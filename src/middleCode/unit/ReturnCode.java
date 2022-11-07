package middleCode.unit;

import base.Var;
import middleCode.MiddleCode;

public class ReturnCode extends MiddleCode {
    Var var = null;

    public ReturnCode(Var var) {
        this.var = var;
    }

    @Override
    public String toString() {
        if (var != null) {
            if (var.isConst)
                return "ret " + var.value;
            else
                return "ret " + var.name;
        }
        else
            return "ret";
    }

    public void genMipsCode(){
        if (currentFunc.name!="func_main") {
            if (var != null) {
                if (var.isConst)
                    mipsCodeManger.addLi("v0", var.value);
                else
                    mipsCodeManger.addLw("v0", "fp", var.addr);
            }
            mipsCodeManger.addJr();
            mipsCodeManger.addText("");
        }
        else{
            mipsCodeManger.addExit();
        }
    }


}
