package syntatic.unit;

import base.BaseUnit;
import base.Var;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.INTCON;

public class Number extends SynUnit {
    public int dim = 0;
    int a = 1;

    public Number(BaseUnit baseUnit) {
        super(baseUnit, "Number");
    }

    public void construct(){
        init();
        if (Objects.equals(sym, INTCON)){
            pushTreeNow();
        }
        else{
            System.out.println("Number error...");
        }
        pushTree(this);
    }

    public void getValue(){
        childIdx = 0;
        if (isChildMatch(INTCON)){
            getChildValue(INTCON);
            value = childUnit.value;
        }
        returnVar = new Var(value);
//        System.out.println("Number getValue "+value);
//        System.out.println("Number "+ value+" "+returnVar);
        a = 1;
    }

    public void genMiddleCode(){
//        TODO: WHY?
        childIdx = 0;
        if (isChildMatch(INTCON)){
            getChildValue(INTCON);
            value = childUnit.value;
        }
        returnVar = new Var(value);
//        System.out.println("Number "+ this.value+" "+this.returnVar);
//        System.out.println("Number a test "+ this.a);

    }
}
