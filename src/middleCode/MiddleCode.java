package middleCode;

import base.Func;
import base.Var;
import mips.MipsCodeManger;

import java.util.Vector;

public class MiddleCode {
    public String cls = "";
    Vector<MiddleUnit> content = new Vector<>();
    public static MipsCodeManger mipsCodeManger = new MipsCodeManger();

    public static Func currentFunc = null;
    public int globalMem = 0;

    public void addContent(MiddleUnit middleUnit){
        content.add(middleUnit);
    }

    @Override
    public String toString() {
        String buf = "\n";
        for (MiddleUnit middleUnit: content){
            buf += middleUnit.name + " ";
        }
        return buf;
    }

    public void constructMemoryStageOne(){

    }

    public void constructMemoryStageTwo(){

    }

    public void genMipsCode(){

    }

    public void genVarToRegMips(Var var, String reg){
        if (var.isGlobal){
            mipsCodeManger.addLwLabel(reg, var.name, 0);
        }
        else{
            if (var.isConst)
                mipsCodeManger.addLi(reg, var.value);
            else
                mipsCodeManger.addLw(reg, "fp", var.addr);
        }
    }

    public void genRegToVarMips(Var var, String reg){
        if (var.isGlobal){
            mipsCodeManger.addSwLabel(reg, var.name, 0);
        }
        else{
            if (var.isConst)
                mipsCodeManger.addLi(reg, var.value);
            else
                mipsCodeManger.addSw(reg, "fp", var.addr);
        }
    }


}
