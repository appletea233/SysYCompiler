package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import static base.Type.OR;

public class LOrExp extends SynUnit {
    boolean value;

    public LOrExp(BaseUnit baseUnit) {
        super(baseUnit, "LOrExp");
    }

    public void construct(){
        init();
        checkSynUnit("LAndExp");
        pushTree(this);
        while(isSymNowEql(OR)){
            pushTreeNow();
            checkSynUnit("LAndExp");
            pushTree(this);
        }
    }
}
