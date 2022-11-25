package syntatic.unit;

import base.BaseUnit;
import base.Func;
import base.Var;
import base.VarTable;
import middleCode.unit.ConditionExpCode;
import middleCode.unit.ExpCode;
import middleCode.unit.FuncCallCode;
import middleCode.unit.PushCode;
import syntatic.SynUnit;

import java.util.Objects;
import java.util.Vector;

import static base.Type.*;

public class UnaryExp extends SynUnit {
    public int dim;
    int line = 0;
    String funcName = "";
    Func func;


    public UnaryExp(BaseUnit baseUnit) {
        super(baseUnit, "UnaryExp");
    }


    public void construct(){
        // UnaryExp → PrimaryExp | Var '(' [FuncRParams] ')'| UnaryOp UnaryExp
        init();

        int res = symbolList.getToken(3);
        Vector<String> token = symbolList.token;

        if (Objects.equals(token.get(0), LPARENT)){
            checkSynUnit(PRIMARYEXP);
        }
        else if (Objects.equals(token.get(0), IDENFR)){
            if (Objects.equals(token.get(1), LPARENT)){
                checkSymNow(IDENFR);
                checkSymNow(LPARENT);
                if (is_exp(token.get(2))){
                    FuncRParams funcRParams = new FuncRParams(this);
                    funcRParams.construct();
                    this.addChildren(funcRParams);
                }
                checkSymNow(RPARENT);
            }
            else {
                checkSynUnit("PrimaryExp");
            }
        }
        else if (Objects.equals(token.get(0), INTCON)){
            checkSynUnit("PrimaryExp");
        }
        else if (Objects.equals(token.get(0), PLUS) || Objects.equals(token.get(0), MINU) || Objects.equals(token.get(0), NOT)){
            // TODO: not special process
            checkSynUnit("UnaryOp");
            checkSynUnit("UnaryExp");
        }
        else{
            System.out.println("token unmatched");
        }
        pushTree(this);
    }

    public void createTable(){
//        System.out.println();
//        for (BaseUnit baseUnit:children){
//            System.out.println(baseUnit.name);
//        }

        if (isChildMatch(PRIMARYEXP)){
            createChildTable(PRIMARYEXP);
            dim = ((PrimaryExp) childUnit).dim;
        }
        else if (isChildMatch(IDENFR)){
            int realCount = 0;
            Vector<Integer> realDim = new Vector<Integer>();
            checkChild(IDENFR);
            funcName = childUnit.content;
            line = childUnit.line;
            if (funcTable.isFuncExist(funcName)){
                func = funcTable.getFunc(funcName);
                func.addUsedLine(line);
                dim = func.dim;

                checkChild(LPARENT);
                if (isChildMatch(FUNCRPARAMS)) {
                    createChildTable(FUNCRPARAMS);
                    realCount = ((FuncRParams) childUnit).paramCount;
                    realDim = ((FuncRParams) childUnit).dims;
                }
                // TODO
                if (realCount != func.paramCount)
                    errorList.addError(line, 'd');
                else{
                    boolean isMatch = true;
                    for (int i=0; i<realCount; i++){
                        if (realDim.get(i) != func.inVarList.get(i).dim) {
                            System.out.println("check dim unmatched " + realDim.get(i)+" "+ func.inVarList.get(i).dim);
                            isMatch = false;
                            break;
                        }
                    }

                    if ( ! isMatch) {
                        System.out.println("UnaryExp.java function params dim unmatched");
                        errorList.addError(line, 'e');
                        System.out.println("UnaryExp.java function params dim unmatched");
                        System.out.println("111111111111111");
                    }
                }
                checkChild(RPARENT);
            }
            else{
                errorList.addError(line, 'c');
            }
        }
        else if (isChildMatch(UNARYOP)){
            createChildTable(UNARYOP);
            createChildTable(UNARYEXP);
            dim = ((UnaryExp) childUnit).dim;
        }
        else{
            System.out.println("createTable: UnaryExp is not matched");
        }
    }

    public void getValue(){
        reset();

        if (isChildMatch(PRIMARYEXP)){
            getChildValue(PRIMARYEXP);
            value = childUnit.value;
        }
        else if (isChildMatch(IDENFR)){
            checkChild(IDENFR);
            checkChild(RPARENT);
            if (isChildMatch(FUNCRPARAMS)){
                getChildValue(FUNCRPARAMS);
            }
            checkChild(LPARENT);
//            System.out.println("Const getValue IDENFY error...");
        }
        else if (isChildMatch(UNARYOP)){
            getChildValue(UNARYOP);
            int opValue = ((UnaryOp) childUnit).opValue;
            if (opValue == 0)
                System.out.println("====================Unary Op is not expected as "+opValue);
            System.out.println(opValue);
            getChildValue(UNARYEXP);
            System.out.println(childUnit.value);

            value = opValue*childUnit.value;
        }
        else{
            System.out.println("getValue: UnaryExp is not matched");
        }
        System.out.println("UnaryExp "+value);
    }

    public void genMiddleCode(){
        reset();
        if (!(isConst || isGlobal)){
            if (isChildMatch(PRIMARYEXP)){
                genChildMiddleCode(PRIMARYEXP);
                returnVar = childUnit.returnVar;
            }
            else if (isChildMatch(IDENFR)){
                checkChild(IDENFR);
                checkChild(LPARENT);
                if (isChildMatch(FUNCRPARAMS)) {
                    genChildMiddleCode(FUNCRPARAMS);
                    Vector<Var> list =  ((FuncRParams) childUnit).inVarList;
                    for (int i = 0; i<list.size(); i++){
                        var = list.get(i);
                        middleCodeList.addCode(new PushCode(var, func, func.inVarList.get(i)));
//                        System.out.println(var);
//                        System.out.println(func.inVarList.get(i));
                    }
                }
                checkChild(RPARENT);


                middleCodeList.addCode(new FuncCallCode(func));
                System.out.println(func);
                if (!Objects.equals(func.returnType, "void")) {
                    returnVar = VarTable.getTmpVar();
                    middleCodeList.addCode(new ExpCode(returnVar, new Var("RET")));
                }
            }
            else if (isChildMatch(UNARYOP)){
                genChildMiddleCode(UNARYOP);
                int opValue = ((UnaryOp) childUnit).opValue;
                genChildMiddleCode(UNARYEXP);

                if (opValue == 0){
                    returnVar = VarTable.getTmpVar();
                    middleCodeList.addCode(new ConditionExpCode("beq", this.returnVar, childUnit.returnVar, new Var(0)));
                }
                else if (opValue == 1){
                    returnVar = childUnit.returnVar;
                }
                else if (opValue == -1) {
                    returnVar = VarTable.getTmpVar();
                    System.out.println("value "+childUnit.returnVar);
                    middleCodeList.addCode(new ExpCode(returnVar, new Var(0), "-", childUnit.returnVar));
                }
            }
            else{
                System.out.println("generateMiddleCode: UnaryExp is not matched");
            }
            System.out.println("UnaryExp returnVar "+ returnVar);
        }
        else{
            this.returnVar = new Var(value);
        }
    }
}
