package syntatic.unit;

import base.BaseUnit;
import middleCode.LabelManager;
import middleCode.unit.LabelCode;
import syntatic.SynUnit;

import static base.Type.AND;
import static base.Type.OR;

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
    public void genMiddleCode(String label1, String label2) {
        EqExp eqExp = (EqExp) getChildNow();
        eqExp.genMiddleCode();
        int eqExpNum = (this.children.size() + 1) / 2;
        if (eqExpNum == 1) {
            eqExp.genMiddleCode(label1, label2);
        } else {
            String label3 = LabelManager.getLabel();
            eqExp.genMiddleCode(label3, label2);
            childIdx++;

            middleCodeList.addCode(new LabelCode(label3));
            for (int i = 1; i < eqExpNum - 1; i++) {
                checkChild(AND);
                label3 = LabelManager.getLabel();
                eqExp = (EqExp) getChildNow();
                eqExp.genMiddleCode(label3, label2);
                childIdx++;

                middleCodeList.addCode(new LabelCode(label3));
            }
            checkChild(AND);
            eqExp = (EqExp) getChildNow();
            eqExp.genMiddleCode(label1, label2);
            childIdx++;
        }
    }

}
