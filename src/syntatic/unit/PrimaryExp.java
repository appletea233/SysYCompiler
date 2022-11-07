package syntatic.unit;

import base.BaseUnit;
import base.Var;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class PrimaryExp extends SynUnit {
    public int dim;

    public PrimaryExp(BaseUnit baseUnit) {
        super(baseUnit, "PrimaryExp");
    }

    public void construct(){
        init();
        if (Objects.equals(sym, LPARENT)){
            checkSymNow(LPARENT);
            checkSynUnit("Exp");
//            this->dim = ((Exp*)(children.back()))->dim;
            checkSymNow(RPARENT);
        }
        else if (Objects.equals(sym, IDENFR)){
            checkSynUnit("LVal");
//            this->dim = ((LVal*)(children.back()))->dim;
        }
        else if (Objects.equals(sym, INTCON)){
            checkSynUnit("Number");
//            this->dim = ((Number*)(children.back()))->dim;
        }
        else{
            System.out.println("primary_exp error");
        }
        pushTree(this);
    }

    public void createTable(){
        if (isChildMatch(LPARENT)){
            checkChild(LPARENT);
            createChildTable(EXP);
            dim = ((Exp) childUnit).dim;
            checkChild(RPARENT);
        }
        else if(isChildMatch(LVAL)){
            createChildTable(LVAL);
            dim = ((LVal) childUnit).dim;
        }
        else if (isChildMatch((NUMBER))){
            createChildTable(NUMBER);
            dim = ((Number) childUnit).dim;
        }
        else{
            System.out.println("primary exp unmatched");
        }
    }

    public void getValue(){
        childIdx = 0;

//        if (isConst || isGlobal) {
            if (isChildMatch(LPARENT)) {
                checkChild(LPARENT);
                getChildValue(EXP);
                value = childUnit.value;
                checkChild(RPARENT);
            } else if (isChildMatch(LVAL)) {
                getChildValue(LVAL);
                value = childUnit.value;
                System.out.println("PrimaryExp getValue get LVal Value " + value);
            } else if (isChildMatch((NUMBER))) {
                getChildValue(NUMBER);
                value = childUnit.value;
            } else {
                System.out.println("primary exp unmatched");
            }
//        }
    }

    public void genMiddleCode(){
        reset();
        if (! isConst && !isGlobal) {
            if (isChildMatch(LPARENT)) {
                checkChild(LPARENT);
                genChildMiddleCode(EXP);
                returnVar = childUnit.returnVar;
                checkChild(RPARENT);
            } else if (isChildMatch(LVAL)) {
                genChildMiddleCode(LVAL);
                returnVar = childUnit.returnVar;
            } else if (isChildMatch((NUMBER))) {
                genChildMiddleCode(NUMBER);
                returnVar = childUnit.returnVar;
            } else {
                System.out.println("primary exp unmatched");
            }
        }
        else{
            this.returnVar = new Var(value);
        }
        System.out.println("PrimaryExp returnVar "+ returnVar);

    }
}
