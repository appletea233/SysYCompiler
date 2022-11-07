package middleCode.unit;

import base.Var;
import middleCode.MiddleCode;

public class FuncParaCode extends MiddleCode {
    Var var;

    public FuncParaCode(Var var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "para " + var.type + " " + var.name;
    }

    @Override
    public void constructMemoryStageOne() {
        if(var.dim == 0) {
            var.addr = currentFunc.paramMem;
            currentFunc.paramMem += 4;
        }
    }

    @Override
    public void constructMemoryStageTwo() {
        if(var.dim == 0) {
            var.addr += currentFunc.localMem + currentFunc.maxTmpMem;
            var.addr -= currentFunc.totalMem;
        }
        System.out.println("FuncPara "+var.addr);
    }
}
