package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import static base.Type.ADDEXP;

public class Exp extends SynUnit {
    public int dim;

    public Exp(BaseUnit baseUnit) {
        super(baseUnit, "Exp");
    }

    public void construct(){
        init();

        checkSynUnit(ADDEXP);
//        this->dim = addExp->dim;

        pushTree(this);
    }

    @Override
    public void createTable() {
        createChildTable(ADDEXP);
        dim = ((AddExp) childUnit).dim;
    }

    public void getValue() {
        reset();

//        if (isConst || isGlobal) {
            getChildValue(ADDEXP);
            value = ((AddExp) childUnit).value;
        System.out.println("Exp ReturnExp "+returnVar);

//        }
    }

    public void genMiddleCode(){
        System.out.println(this.isConst);

        reset();
//        var = parent.var;
//        if (var!=null){
//            if (var.dim == 0){
                genChildMiddleCode(ADDEXP);
                returnVar = childUnit.returnVar;
                System.out.println("Exp ReturnExp "+returnVar);
//            }
//        }
    }

}
