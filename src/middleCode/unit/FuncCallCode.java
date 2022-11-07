package middleCode.unit;

import base.Func;
import middleCode.MiddleCode;
import mips.MipsCodeManger;

public class FuncCallCode extends MiddleCode {
    Func func;

    public FuncCallCode(Func func) {
        this.func = func;
    }

    @Override
    public String toString() {
        return "call " + func.name;
    }

    public void genMipsCode(){
        mipsCodeManger.addText("# " + func.name+" call");
        mipsCodeManger.addAddi("sp", "sp", -16);
        mipsCodeManger.addSw("ra", "sp", 0);
        mipsCodeManger.addSw("t0", "sp", 4);
        mipsCodeManger.addSw("t1", "sp", 8);
        mipsCodeManger.addSw("t2", "sp", 12);

        mipsCodeManger.addText("");
        mipsCodeManger.addAddi("fp", "fp", func.totalMem);
        mipsCodeManger.addJal(func.name);
        mipsCodeManger.addText("nop");
        mipsCodeManger.addText("nop");
        mipsCodeManger.addAddi("fp", "fp", -func.totalMem);
        mipsCodeManger.addText("");
        mipsCodeManger.addLw("ra", "sp", 0);
        mipsCodeManger.addLw("t0", "sp", 4);
        mipsCodeManger.addLw("t1", "sp", 8);
        mipsCodeManger.addLw("t2", "sp", 12);
        mipsCodeManger.addAddi("sp", "sp", 16);
    }
}
