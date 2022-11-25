package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

public class Cond extends SynUnit {
    public Cond(BaseUnit baseUnit) {
        super(baseUnit, "Cond");
    }

    public void construct(){
        init();
        checkSynUnit("LOrExp");
        pushTree(this);
    }

    public void genMiddleCode(String label1, String label2) {
        LOrExp lOrExp = (LOrExp) getChildNow();
        lOrExp.genMiddleCode(label1, label2);
    }
}
