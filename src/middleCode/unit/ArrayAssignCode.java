package middleCode.unit;

import base.Var;
import middleCode.MiddleCode;

public class ArrayAssignCode extends MiddleCode {
    Var array;
    Var index;
    Var valueVar;
    public ArrayAssignCode(Var array, Var index, Var valueVar) {
        this.array = array;
        this.index = index;
        this.valueVar = valueVar;
    }

    public String toString() {
        String buf = "";
        buf += array.name + "[" + (index.isConst?(""+index.value):index.name) + "]";
        buf += " = " + (valueVar.isConst?(""+valueVar.value):valueVar.name);
        return buf;
    }

    public void genMipsCode(){
        genVarToRegMips(array, "t1");
        genVarToRegMips(index, "t2");
        mipsCodeManger.addSll("t2", "t2", 2);
        mipsCodeManger.addExp("t1", "t1", "+", "t2");
        genVarToRegMips(valueVar, "t2");
        mipsCodeManger.addSw("t2", "t1", 0);
    }

}
