package syntatic.unit;

import base.BaseUnit;
import base.Var;
import base.VarTable;
import middleCode.LabelManager;
import middleCode.MiddleCode;
import middleCode.unit.BranchCode;
import middleCode.unit.ConditionExpCode;
import middleCode.unit.GotoCode;
import middleCode.unit.LabelCode;
import syntatic.SynUnit;

import static base.Type.*;

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

    public void genMiddleCode(String label1, String label2) {
        reset();
        Var var1;
        Var var2;
        Var returnVar;

        genChildMiddleCode(RELEXP);
        var1 = childUnit.returnVar;
        while (isChildMatch(EQL) || isChildMatch(NEQ)) {
            String op = "";
            if (isChildMatch(EQL)) {
                op = "beq";
                checkChild(EQL);
            }
            else if (isChildMatch(NEQ)) {
                op = "bne";
                checkChild(NEQ);
            }
            genChildMiddleCode(RELEXP);
            var2 = childUnit.returnVar;
            returnVar = VarTable.getTmpVar();
            middleCodeList.addCode(new ConditionExpCode(op, returnVar, var1, var2));
            var1 = returnVar;
        }
        this.returnVar = var1;
        middleCodeList.addCode(new BranchCode(this.returnVar, label1));
        middleCodeList.addCode(new GotoCode(label2));

    }
}
