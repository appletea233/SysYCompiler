package middleCode.unit;

import base.Var;
import middleCode.MiddleCode;

import java.util.Vector;

public class ArrayDefCode extends MiddleCode {
    Var var;
    Vector<Var> initVarVector;
    boolean isInit = false;

    public ArrayDefCode(Var var) {
        this.var = var;
    }

    public ArrayDefCode(Var var, Vector<Var> initVarVector) {
        this.var = var;
        this.initVarVector = initVarVector;
        this.isInit = true;
    }

    @Override
    public String toString() {
        String buf = "";
        String dim = "";
        for (int i = 0; i < var.dim; i++) {
            dim += " " + var.arrayDim.get(i);
        }
        if (isInit) {
            for (Var var : initVarVector) {
                if (var.isConst)
                    buf += var.value + " ";
                else
                    buf += var.name + " ";
            }
            return "array int " + var.name + dim + " = " + buf;
        }
       else
           return "array int " + var.name + dim;
    }
}
