package syntatic.unit;

import base.BaseUnit;
import base.Var;
import base.VarTable;
import middleCode.unit.ConditionExpCode;
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

    public void genMiddleCode() {
        reset();
        Var var1;
        Var var2;
        Var returnVar;

        genChildMiddleCode(ADDEXP);
        var1 = childUnit.returnVar;
        while (isChildMatch(LSS) || isChildMatch(LEQ) || isChildMatch(GRE) || isChildMatch(GEQ)) {
            String op = "";
            if (isChildMatch(LSS)) {
                op = "blt";
                checkChild(LSS);
            }
            else if (isChildMatch(LEQ)) {
                op = "ble";
                checkChild(LEQ);
            }
            else if (isChildMatch(GRE)) {
                op = "bgt";
                checkChild(GRE);
            }
            else if (isChildMatch(GEQ)) {
                op = "bge";
                checkChild(GEQ);
            }

            genChildMiddleCode(ADDEXP);
            var2 = childUnit.returnVar;
            returnVar = VarTable.getTmpVar();
            middleCodeList.addCode(new ConditionExpCode(op, returnVar, var1, var2));
            var1 = returnVar;
        }

        this.returnVar = var1;

    }
}
