package middleCode.unit;

import base.Func;
import base.Var;
import middleCode.MiddleCode;

public class PushCode extends MiddleCode {
    Var var;
    Func func;
    Var origin;
    int addr = 0;

    public PushCode(Var var, Func func, Var origin) {
        this.var = var;
        this.func = func;
        this.origin = origin;
    }

    @Override
    public String toString() {
        if (var.isConst)
            return  "push " + var.value;
        else
            return "push " + var.name;
    }

    public void constructMemoryStageOne(){

    }

    public void genMipsCode(){
        mipsCodeManger.addText("# pushCode");

        if (var.isConst){
            mipsCodeManger.addLi("t0", var.value);
            mipsCodeManger.addSw("t0", "fp", origin.addr + func.totalMem);
        }
        else {
            genVarToRegMips(var, "t0");
            mipsCodeManger.addSw("t0", "fp", origin.addr + func.totalMem);
        }
    }
}
