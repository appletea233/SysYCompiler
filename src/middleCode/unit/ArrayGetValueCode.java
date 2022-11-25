package middleCode.unit;

import base.Var;
import middleCode.MiddleCode;

public class ArrayGetValueCode extends MiddleCode {
    Var array;
    Var var;
    Var index;

    public ArrayGetValueCode(Var var, Var array, Var index) {
        this.var = var;
        this.array = array;
        this.index = index;
    }

    public String toString() {
        String buf = "";
        buf += var.name;
        buf += " = ";
        buf += array.name + "[" ;
        if (index.isConst)
            buf += index.value;
        else
            buf += index.name;
        buf += "]";
        return buf;
    }

    public void genMipsCode(){
        genVarToRegMips(array, "t1");
        genVarToRegMips(index, "t2");
        mipsCodeManger.addSll("t2", "t2", 2);
        mipsCodeManger.addExp("t1", "t1", "+", "t2");
        mipsCodeManger.addLw( "t1", "t1", 0);
        genRegToVarMips(var, "t1");
    }
}
