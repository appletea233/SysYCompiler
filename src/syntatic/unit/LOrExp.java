package syntatic.unit;

import base.BaseUnit;
import middleCode.LabelManager;
import middleCode.unit.LabelCode;
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

    public void genMiddleCode(String label1, String label2) {
        LAndExp landExp = (LAndExp) getChildNow();
        int landExpNum = (this.children.size() + 1) / 2;
        if (landExpNum == 1) {
            landExp.genMiddleCode(label1, label2);
        } else {
            String label3 = LabelManager.getLabel();
            landExp.genMiddleCode(label1, label3);
            childIdx++;
            middleCodeList.addCode(new LabelCode(label3));
            for (int i = 1; i < landExpNum-1; i++) {
                checkChild(OR);
                label3 = LabelManager.getLabel();
                landExp = (LAndExp) getChildNow();
                landExp.genMiddleCode(label1, label3);
                childIdx++;
                middleCodeList.addCode(new LabelCode(label3));
            }
            checkChild(OR);
            landExp = (LAndExp) getChildNow();
            landExp.genMiddleCode(label1, label2);
            childIdx++;
        }
    }
}
