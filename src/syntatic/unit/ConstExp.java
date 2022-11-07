package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import static base.Type.ADDEXP;

public class ConstExp extends SynUnit {
    public ConstExp(BaseUnit baseUnit) {
        super(baseUnit, "ConstExp");
        this.isConst = true;
    }

    public void construct(){
        init();
        checkSynUnit(ADDEXP);
        pushTree(this);
    }

    public void getValue(){
        childIdx = 0;
        getChildValue(ADDEXP);
        value = childUnit.value;
//        System.out.println("ConstExp "+value);
    }
}
