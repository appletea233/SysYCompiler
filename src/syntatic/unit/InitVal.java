package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class InitVal extends SynUnit {
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
                createChildTable(INITVAL);
                while(isChildMatch(COMMA)){
                    checkChild(COMMA);
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
            System.out.println("init "+value);
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
        System.out.println(this.isConst);

        reset();
        var = parent.var;

        if (var.dim == 0){
            genChildMiddleCode(EXP);
            returnVar = childUnit.returnVar;
            System.out.println("InitVal ExpReturn " + returnVar);
        }
    }
}
