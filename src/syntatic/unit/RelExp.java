package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import static base.Type.*;

public class RelExp extends SynUnit {
    int value = -1;

    public RelExp(BaseUnit baseUnit) {
        super(baseUnit, "RelExp");
    }

    public void construct(){
        init();
        checkSynUnit("AddExp");
        pushTree(this);
        while(isSymNowEql(new String[]{LSS, LEQ, GRE, GEQ})){
            pushTreeNow();
            checkSynUnit("AddExp");
            pushTree(this);
        }
    }
}
