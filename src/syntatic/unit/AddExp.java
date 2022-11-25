package syntatic.unit;

import base.BaseUnit;
import base.Var;
import base.VarTable;
import middleCode.unit.ExpCode;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class AddExp extends SynUnit{
    public int dim;
    public AddExp(BaseUnit baseUnit) {
        super(baseUnit, "AddExp");
    }

    public void construct(){
        init();

        // AddExp → MulExp | AddExp ('+' | '−') MulExp
        checkSynUnit("MulExp");
        pushTree(this);
        getSymNow();
        while(Objects.equals(sym, PLUS) || Objects.equals(sym, MINU)){
            pushTreeNow();
            checkSynUnit("MulExp");
            pushTree(this);
            getSymNow();
        }
    }

    public void createTable(){
        createChildTable(MULEXP);
        dim = ((MulExp) childUnit).dim;
        while(isChildMatch(PLUS) || isChildMatch(MINU)){
            if (isChildMatch(MINU))
                checkChild(MINU);
            else if (isChildMatch(PLUS))
                checkChild(PLUS);

            createChildTable(MULEXP);
        }
    }

    @Override
    public void getValue() {
        reset();
//        if (isConst || isGlobal){
            getChildValue(MULEXP);
            value = childUnit.value;
            while(isChildMatch(PLUS) || isChildMatch(MINU)){
                if (isChildMatch(MINU)) {
                    checkChild(MINU);
                    getChildValue(MULEXP);
                    value -= childUnit.value;
                }
                else if (isChildMatch(PLUS)) {
                    checkChild(PLUS);
                    getChildValue(MULEXP);
                    value += childUnit.value;
                }

            }
//        }
//        System.out.println("AddExp "+value);
    }

    public void genMiddleCode() {
        reset();
        if (!(isConst || isGlobal)) {
            update();

            Var var1;
            Var var2;
            Var returnVar = null;
            reset();

            genChildMiddleCode(MULEXP);
            var1 = childUnit.returnVar;
            while (isChildMatch(PLUS) || isChildMatch(MINU)) {
                returnVar = VarTable.getTmpVar();
                if (isChildMatch(MINU)) {
                    checkChild(MINU);
                    genChildMiddleCode(MULEXP);
//                if (childUnit == children.lastElement())
//                    returnVar = var;
                    var2 = childUnit.returnVar;
                    middleCodeList.addCode(new ExpCode(returnVar, var1, "-", var2));

                    var1 = returnVar;
                } else if (isChildMatch(PLUS)) {
                    checkChild(PLUS);
                    genChildMiddleCode(MULEXP);
                    var2 = childUnit.returnVar;
                    middleCodeList.addCode(new ExpCode(returnVar, var1, "+", var2));

                    var1 = returnVar;
                }
            }
            this.returnVar = var1;
        } else {
            this.returnVar = new Var(value);
        }
//        System.out.println("AddExp Return "+returnVar);
    }

    public void update(){
        // TODO 常值优化
    }
}
