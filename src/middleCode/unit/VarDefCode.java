package middleCode.unit;

import base.Var;
import middleCode.MiddleCode;
import mips.MipsCodeManger;

public class VarDefCode extends MiddleCode {
    Var var;
    Var initVar;
    boolean isInit = false;


    public VarDefCode(Var var) {
        this.var = var;
    }

    public VarDefCode(Var var, Var initVar) {
        this.var = var;
        this.initVar = initVar;
        this.isInit = true;
    }

    @Override
    public String toString() {
        String buf = "var int " + var.name;
        if (isInit){
            buf += " = ";
            if (initVar.isConst)
                buf += initVar.value;
            else
                buf += initVar.name;
        }
        return buf;
    }

    public void constructMemoryStageOne(){
        if (currentFunc == null){
            if (var.dim == 0) {
                mipsCodeManger.addWord(var.name, var.value);
                var.isGlobal = true;
                System.out.println(var);
            }
        }
        else{
            if (var.dim == 0){
                if (! var.isTmp){
                    var.addr = currentFunc.localMem;
                    currentFunc.localMem += 4;
                    System.out.println(var);
                }
            }
        }
    }

    public void constructMemoryStageTwo(){
        if (currentFunc != null) {
            if (var.dim == 0){
                if (! var.isTmp){
                    var.addr -= currentFunc.totalMem;
                }
            }
        }
    }

    public void genMipsCode(){
        if (isInit){
            mipsCodeManger.addText("# "+this);
            genVarToRegMips(initVar, "t0");
            genRegToVarMips(var, "t0");
        }
    }

}
