package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import static base.Type.EQL;
import static base.Type.NEQ;

public class EqExp extends SynUnit {

    public EqExp(BaseUnit baseUnit) {
        super(baseUnit, "EqExp");
    }

    public void construct(){
        init();
        checkSynUnit("RelExp");
        pushTree(this);
        while(isSymNowEql(EQL) || isSymNowEql(NEQ)){
            pushTreeNow();
            checkSynUnit("RelExp");
            pushTree(this);
        }
    }
}
