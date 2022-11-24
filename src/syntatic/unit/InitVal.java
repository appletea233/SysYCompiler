package syntatic.unit;

import base.BaseUnit;
import base.Var;
import syntatic.SynUnit;

import java.util.Objects;
import java.util.Vector;

import static base.Type.*;

public class InitVal extends SynUnit {
    public Vector<Var> returnVarList = new Vector<>();
    int dim = 0;

    public InitVal(BaseUnit baseUnit) {
        super(baseUnit, "InitVal");
        this.isInit = true;
    }

    public void construct(){
        init();
        if (is_exp(sym)){
            checkSynUnit("Exp");
        }
        else if (Objects.equals(sym, LBRACE)){
            checkSymNow(LBRACE);
            if (Objects.equals(getSymNow(), "}"))
                pushTreeNow();
            else{
                checkSynUnit("InitVal");
                while(Objects.equals(getSymNow(), COMMA)){
                    checkList(new String[]{"COMMA", "InitVal"});
                }
                checkSymNow(RBRACE);
            }
        }
        else{
            System.out.println("Initval error...");
        }
        pushTree(this);
    }

    public void createTable(){
        reset();
        if (isChildMatch(EXP)){
            createChildTable("Exp");
        }
        else if (isChildMatch(LBRACE)){
            checkChild(LBRACE);
            if (isChildMatch(RBRACE))
                checkChild(RBRACE);
            else{
                InitVal initVal = (InitVal) getChildNow();
                initVal.var = var;
                initVal.returnVarList = this.returnVarList;
                createChildTable(INITVAL);
                while(isChildMatch(COMMA)){
                    checkChild(COMMA);
                    initVal = (InitVal) getChildNow();
                    initVal.var = var;
                    initVal.returnVarList = this.returnVarList;
                    createChildTable(INITVAL);
                }
                checkChild(RBRACE);
            }
        }
    }

    public void getValue(){
        reset();
        if (isChildMatch(EXP)){
            getChildValue("Exp");
            value = childUnit.value;
            System.out.println(var);
            this.var.arrayValue.add(value);
        }
        else if (isChildMatch(LBRACE)){
            checkChild(LBRACE);
            if (isChildMatch(RBRACE))
                checkChild(RBRACE);
            else{
                getChildValue(INITVAL);
                while(isChildMatch(COMMA)){
                    checkChild(COMMA);
                    getChildValue(INITVAL);
                }
                checkChild(RBRACE);
            }
        }
    }

    public void genMiddleCode(){
        reset();
        var = parent.var;
        System.out.println("genmiddle var: " + var);
        if (var.dim == 0){
            genChildMiddleCode(EXP);
            returnVar = childUnit.returnVar;
            this.returnVarList.add(returnVar);
        }
        else{
            if (isChildMatch(EXP)){
                genChildMiddleCode(EXP);
                returnVar = childUnit.returnVar;
                this.returnVarList.add(returnVar);
            }
            else if (isChildMatch(LBRACE)){
                checkChild(LBRACE);
                if (isChildMatch(RBRACE))
                    checkChild(RBRACE);
                else{
                    genChildMiddleCode(INITVAL);
                    while(isChildMatch(COMMA)){
                        checkChild(COMMA);
                        genChildMiddleCode(INITVAL);
                    }
                    checkChild(RBRACE);
                }
            }
        }
    }
}
