package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import static base.Type.AND;

public class LAndExp extends SynUnit {
    boolean value;

    public LAndExp(BaseUnit baseUnit) {
        super(baseUnit, "LAndExp");
    }

    public void construct(){
        init();
        checkSynUnit("EqExp");
        pushTree(this);
        while(isSymNowEql(AND)){
            pushTreeNow();
            checkSynUnit("EqExp");
            pushTree(this);
        }
    }
}
