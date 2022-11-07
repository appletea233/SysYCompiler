package syntatic.unit;

import base.BaseUnit;
import base.Var;
import base.VarTable;
import middleCode.unit.VarDefCode;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class VarDef extends SynUnit {
    String name;
    String type = INTTK;
    int dim = 0;
    int line;

    public VarDef(BaseUnit baseUnit) {
        super(baseUnit, "VarDef");
    }

    public void construct(){
        init();
        if (isSymNowEql(IDENFR)){
            checkSymNow(IDENFR);
        }
        while(Objects.equals(getSymNow(), LBRACK)){
            checkSymNow(LBRACK);
            checkSynUnit("ConstExp");
            checkSymNow(RBRACK);
        }
        if (Objects.equals(getSymNow(), ASSIGN)){
            checkList(new String[]{ASSIGN, "InitVal"});
        }


        pushTree(this);
    }

    public void createTable(){
        type = ((VarDecl)parent).type;

        checkChild(IDENFR);
        name = childUnit.content;
        line = childUnit.line;

        while(isChildMatch(LBRACK)){
            dim++;
            checkChild(LBRACK);
            createChildTable(CONSTEXP);
            checkChild(RBRACK);
        }
        if (! varTable.isVarExistNowLayer(name)) {
            var = new Var(false, name, type, dim, line);
        }
        else
            errorList.addError(line, 'b');
        if (isChildMatch(ASSIGN)) {
            checkChild(ASSIGN);
            createChildTable(INITVAL);
        }
        varTable.addVar(var);
    }

    public void getValue(){
        reset();

        checkChild(IDENFR);
        while(isChildMatch(LBRACK)){
            checkChild(LBRACK);
            getChildValue(CONSTEXP);
            checkChild(RBRACK);
        }

        if (isChildMatch(ASSIGN)) {
            checkChild(ASSIGN);
            getChildValue(INITVAL);
            if (isGlobal){
                var.value = childUnit.value;
            }
        }

        changeName();

    }

    public void changeName(){
        var.name = name+'_'+varTable.layer+'_'+ VarTable.varNum;
        VarTable.varNum += 1;
    }

    public void genMiddleCode(){
        if (isGlobal){
            System.out.println("VAR DEF isGlobal");
        }

        reset();
        if (var.dim == 0){
            checkChild(IDENFR);
            if (isChildMatch(ASSIGN)) {
                checkChild(ASSIGN);
                genChildMiddleCode(INITVAL);
                returnVar = childUnit.returnVar;
//                System.out.println("VarDef ExpReturn " + returnVar);

//                if (MiddleCodeList.currentCode!=null && MiddleCodeList.currentCode.cls.equals("ExpCode")){
//                    ExpCode expCode = (ExpCode) (MiddleCodeList.currentCode);
//                    if (expCode.varReturn.isTmp) {
//                        expCode.varReturn = var;
//                    }
//                }
//                else{
//                    middleCodeList.addCode(new VarDefCode(var, returnVar));
//                }
                if (isGlobal) {
                    middleCodeList.addCode(new VarDefCode(var, new Var(returnVar.value)));
                }
                else
                    middleCodeList.addCode(new VarDefCode(var, returnVar));
            }
            else{
                middleCodeList.addCode(new VarDefCode(var));
            }
        }
    }


}
