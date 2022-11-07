package syntatic.unit;

import base.BaseUnit;
import base.Var;
import base.VarTable;
import middleCode.unit.ConstDefCode;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class ConstDef extends SynUnit {
    Var var = null;
    String name;
    String type = INTTK;
    int dim = 0;
    int line;

    public ConstDef(BaseUnit baseUnit) {
        super(baseUnit, "ConstDef");
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
        checkSymNow(ASSIGN);

        // TODO: ConstInitVal
        checkSynUnit(CONSTINITVAL);

        pushTree(this);
    }

    public void createTable(){
        type = ((ConstDecl)parent).type;

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
            var = new Var(true, name, type, dim, line);
        }
        else
            errorList.addError(line, 'b');

        checkChild(ASSIGN);
        createChildTable(CONSTINITVAL);
        varTable.addVar(var);
    }

    public void getValue(){
        childIdx = 0;

        checkChild(IDENFR);

        while(isChildMatch(LBRACK)){
            checkChild(LBRACK);
            getChildValue(CONSTEXP);
            checkChild(RBRACK);
        }

        checkChild(ASSIGN);
        getChildValue(CONSTINITVAL);

        changeName();

        if (var.dim == 0){
            var.value = childUnit.value;
            System.out.println("---------------const:" + var);
        }
    }

    public void changeName(){
        var.name = name+'_'+varTable.layer+'_'+ VarTable.varNum;
        VarTable.varNum += 1;
    }

    public void genMiddleCode(){
        middleCodeList.addCode(new ConstDefCode(var.name, var.value));
    }

}
