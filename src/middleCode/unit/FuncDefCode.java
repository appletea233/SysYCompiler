package middleCode.unit;

import base.Func;
import middleCode.MiddleCode;

import java.util.Objects;

public class FuncDefCode extends MiddleCode {
    Func func;

    public FuncDefCode(Func func) {
        this.func = func;
    }

    @Override
    public String toString() {
        return "\n"+func.returnType + " " + func.name + " ()";
    }

    @Override
    public void constructMemoryStageOne() {
        currentFunc = func;
        System.out.println(this);
    }

    @Override
    public void constructMemoryStageTwo() {
        currentFunc = func;
        func.totalMem = func.maxTmpMem + func.localMem + func.paramMem;
        if (Objects.equals(func.name, "func_main")){
            mipsCodeManger.initStamp = func.totalMem;
        }
    }

    public void genMipsCode(){
        currentFunc = func;
        mipsCodeManger.addText("# "+func.getMemInfo());

        mipsCodeManger.addLabel(func.name);
    }
}
