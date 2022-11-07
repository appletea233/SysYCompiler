package syntatic.unit;

import base.BaseUnit;
import base.Var;
import base.VarTable;
import middleCode.unit.ExpCode;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class MulExp extends SynUnit {
    public int dim;

    public MulExp(BaseUnit baseUnit) {
        super(baseUnit, "MulExp");
    }

    public void construct(){
        init();

        // MulExp → UnaryExp | MulExp ('*' | '/' | '%') UnaryExp
        checkSynUnit("UnaryExp");
        pushTree(this);
        getSymNow();
        while(Objects.equals(sym, MULT) || Objects.equals(sym, DIV) || Objects.equals(sym, MOD)){
            pushTreeNow();
            checkSynUnit("UnaryExp");
            pushTree(this);
            getSymNow();
        }
    }

    public void createTable(){
        createChildTable(UNARYEXP);
        dim = ((UnaryExp) childUnit).dim;

        while(isChildMatch(MULT) || isChildMatch(DIV) || isChildMatch(MOD)){
            if (isChildMatch(MULT))
                checkChild(MULT);
            else if (isChildMatch(DIV))
                checkChild(DIV);
            else if (isChildMatch(MOD))
                checkChild(MOD);

            createChildTable(UNARYEXP);
        }
    }

    public void getValue(){
        childIdx = 0;

//        if (isConst || isGlobal) {
        System.out.println("===================");
            getChildValue(UNARYEXP);
            value = ((UnaryExp) childUnit).value;

            while (isChildMatch(MULT) || isChildMatch(DIV) || isChildMatch(MOD)) {
                if (isChildMatch(MULT)) {
                    checkChild(MULT);
                    getChildValue(UNARYEXP);
                    value *= ((UnaryExp) childUnit).value;
                } else if (isChildMatch(DIV)) {
                    checkChild(DIV);
                    getChildValue(UNARYEXP);
                    if (((UnaryExp) childUnit).value != 0)
                        value = value / ((UnaryExp) childUnit).value;
                } else if (isChildMatch(MOD)) {
                    checkChild(MOD);
                    getChildValue(UNARYEXP);
                    if (((UnaryExp) childUnit).value != 0)
                        value = value % ((UnaryExp) childUnit).value;
                }

            }

//        }
//        System.out.println("MulExp "+value);

    }

    public void genMiddleCode(){
        if (! (isConst || isGlobal)) {
            update();

            Var var1;
            Var var2;
            Var returnVar = null;
            reset();

            genChildMiddleCode(UNARYEXP);
            var1 = childUnit.returnVar;
            while (isChildMatch(MULT) || isChildMatch(DIV) || isChildMatch(MOD)) {
                returnVar = VarTable.getTmpVar();
                if (isChildMatch(MULT)) {
                    checkChild(MULT);
                    genChildMiddleCode(UNARYEXP);
//                if (childUnit == children.lastElement())
//                    returnVar = var;
                    var2 = childUnit.returnVar;
                    middleCodeList.addCode(new ExpCode(returnVar, var1, "*", var2));

                    var1 = returnVar;
                } else if (isChildMatch(DIV)) {
                    checkChild(DIV);
                    genChildMiddleCode(UNARYEXP);
                    var2 = childUnit.returnVar;
                    middleCodeList.addCode(new ExpCode(returnVar, var1, "/", var2));

                    var1 = returnVar;
                } else if (isChildMatch(MOD)) {
                    checkChild(MOD);
                    genChildMiddleCode(UNARYEXP);
                    var2 = childUnit.returnVar;
                    middleCodeList.addCode(new ExpCode(returnVar, var1, "%", var2));

                    var1 = returnVar;
                }
            }
            this.returnVar = var1;
            System.out.println("return" + returnVar);
        }
        else{
            this.returnVar = new Var(value);
        }
    }

    public void update(){
        // TODO 常值优化
    }
}
