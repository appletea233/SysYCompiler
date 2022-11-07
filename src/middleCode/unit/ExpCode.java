package middleCode.unit;

import base.Var;
import middleCode.MiddleCode;
import mips.MipsCodeManger;

import java.util.Objects;

public class ExpCode extends MiddleCode {
    public Var var1 = null, var2 = null, varReturn = null;
    String op = "";
    public ExpCode(){
        this.cls = "ExpCode";
    }

    public ExpCode( Var varReturn, Var var1, String op, Var var2) {
        this.cls = "ExpCode";
        this.var1 = var1;
        this.var2 = var2;
        this.varReturn = varReturn;
        this.op = op;
    }

    public ExpCode(Var varReturn, Var var1) {
        this.cls = "ExpCode";
        this.var1 = var1;
        this.varReturn = varReturn;
    }

    @Override
    public String toString() {
        String buf = "";
        buf += varReturn.name + " = ";
        if (var1.isConst)
            buf += var1.value;
        else
            buf += var1.name;
        if (var2 != null) {
            buf += " " + op + " ";
            if (var2.isConst)
                buf += var2.value;
            else
                buf += var2.name;
        }
        return buf;
    }

    @Override
    public void constructMemoryStageOne() {
//        if (!var1.name.equals("RET") && !var1.name.equals("GETINT")) {
            if (varReturn.dim == 0) {
                if (var1.isTmp && !var1.name.equals("RET") && !var1.name.equals("GETINT")) {
//                    currentFunc.tmpMem -= 4;
                }
                if (var2 != null && var2.isTmp) {
//                    currentFunc.tmpMem -= 4;
                }
                if (varReturn.isTmp) {
                    varReturn.addr = currentFunc.tmpMem;
                    currentFunc.tmpMem += 4;
                    if (currentFunc.tmpMem > currentFunc.maxTmpMem) {
                        currentFunc.maxTmpMem = currentFunc.tmpMem;
                    }
                }
            }
    }

    @Override
    public void constructMemoryStageTwo() {
        if (varReturn.dim == 0) {
            if (varReturn.isTmp) {
                varReturn.addr += currentFunc.localMem;
                varReturn.addr -= currentFunc.totalMem;
            }
        }
    }

    public void genMipsCode(){
        mipsCodeManger.addText("# "+this);
        if (Objects.equals(var1.name, "RET")){
            genRegToVarMips(varReturn, "v0");
        }
        else if (Objects.equals(var1.name, "GETINT")){
            mipsCodeManger.addLi("v0", 5);
            mipsCodeManger.addSysCall();
            genRegToVarMips(varReturn, "v0");
        }
        else {
            genVarToRegMips(var1, "t1");
            if (var2 != null) {
                genVarToRegMips(var2, "t2");
            } else {
                op = "+";
                genVarToRegMips(new Var(0), "t2");
            }
            mipsCodeManger.addExp("t0", "t1", op, "t2");

            genRegToVarMips(varReturn, "t0");
            mipsCodeManger.addText("");
        }
    }
}
