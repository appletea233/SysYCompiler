package middleCode.unit;

import base.Var;
import middleCode.MiddleCode;

import java.util.Vector;

public class ArrayDefCode extends MiddleCode {
    Var array;
    public Vector<Integer> dimList = new Vector<>();
    public Vector<Integer> valueList = new Vector<>();
    boolean isInit = false;

    public ArrayDefCode(Var var) {
        this.array = var;
        this.dimList = var.arrayDim;
    }
    public ArrayDefCode(Var var, Vector<Integer> valueList) {
        this.array = var;
        this.dimList = var.arrayDim;
        this.valueList = valueList;
        this.isInit = true;
    }

    @Override
    public String toString() {
        String buf = "";
        for (int dim: dimList) {
            buf += "[" + dim + "]";
        }
       return "arr int "+ array.name + buf;
    }

    public void constructMemoryStageOne(){
        if (currentFunc == null){
            mipsCodeManger.addSpace(array.name, array.getArraySize());
            array.isGlobal = true;
        }
        else{
            array.addr = currentFunc.localMem;
            currentFunc.localMem += array.getArraySize();
        }
    }

    public void constructMemoryStageTwo(){
        if (currentFunc != null) {
            array.addr -= currentFunc.totalMem;
        }
    }

    public void genMipsCode(){
    }
}
