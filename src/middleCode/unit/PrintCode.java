package middleCode.unit;

import base.Str;
import base.Var;
import middleCode.MiddleCode;
import mips.MipsCodeManger;

public class PrintCode extends MiddleCode {
    Str str = null;
    Var var = null;

    public PrintCode(Str str) {
        this.str = str;
    }

    public PrintCode(Var var) {
        this.var = var;
    }

    @Override
    public String toString() {
        String buf = "print ";
        if (str!=null){
            buf += str.name;
        }
        else{
            buf += var.name;
        }
        return buf;
    }


    public void genMipsCode(){
        if (str!=null){
            mipsCodeManger.addLa("a0", str.name);
            mipsCodeManger.addLi("v0", 4);
        }
        else{
            if (var.isConst){
                mipsCodeManger.addLi("a0", var.value);
            }
            else{
                genVarToRegMips(var, "a0");
            }
            mipsCodeManger.addLi("v0", 1);
        }
        mipsCodeManger.addSysCall();
    }
}
