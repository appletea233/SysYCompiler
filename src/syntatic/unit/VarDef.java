package syntatic.unit;

import base.BaseUnit;
import base.Var;
import base.VarTable;
import middleCode.unit.ArrayAssignCode;
import middleCode.unit.ArrayDefCode;
import middleCode.unit.VarDefCode;
import syntatic.SynUnit;

import java.util.Objects;
import java.util.Vector;

import static base.Type.*;

public class VarDef extends SynUnit {
    String name;
    String type = INTTK;
    int dim = 0;
    int line;
    Vector<Var> initVarVector = new Vector<>();

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
            childUnit = getChildNow();
            childUnit.var = var;

            System.out.println(childUnit.name);
            System.out.println(var);
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
            var.arrayDim.add(childUnit.value);
            checkChild(RBRACK);
        }

        if (isChildMatch(ASSIGN)) {
            checkChild(ASSIGN);
            getChildValue(INITVAL);
            if (isGlobal){
                if (var.dim == 0)
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
        checkChild(IDENFR);
        while(isChildMatch(LBRACK)){
            checkChild(LBRACK);
            genChildMiddleCode(CONSTEXP);
            checkChild(RBRACK);
        }
        if (isChildMatch(ASSIGN)) {
            checkChild(ASSIGN);
            genChildMiddleCode(INITVAL);
            this.returnVar = childUnit.returnVar;
            this.initVarVector = ((InitVal)childUnit).returnVarList;
            System.out.println("VAR DEF INITVAL" + initVarVector);

            // global or not global
            if (var.dim > 0) {
                middleCodeList.addCode(new ArrayDefCode(var));
                for (int index = 0; index < initVarVector.size(); index++) {
                    middleCodeList.addCode(new ArrayAssignCode(var, new Var(index), initVarVector.get(index)));
                }
            }
            else if (var.dim == 0) {
                middleCodeList.addCode(new VarDefCode(var, new Var(returnVar.value)));
            }
        }
        else{
            if (var.dim > 0) {
                middleCodeList.addCode(new ArrayDefCode(var));
            }
            else if (var.dim == 0) {
                middleCodeList.addCode(new VarDefCode(var));
            }
        }
    }


}
