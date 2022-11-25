package middleCode.unit;

import base.Var;
import middleCode.LabelManager;
import middleCode.MiddleCode;

import java.util.Objects;

public class ConditionExpCode extends MiddleCode {
    String op;
    Var var1;
    Var var2;
    Var returnVar;

    public ConditionExpCode(String op, Var returnVar, Var var1, Var var2) {
        this.op = op;
        this.returnVar = returnVar;
        this.var1 = var1;
        this.var2 = var2;
    }

    @Override
    public String toString() {
        String var1_str = var1.isConst ? String.valueOf(var1.value) : var1.name;
        String var2_str = var2.isConst ? String.valueOf(var2.value) : var2.name;
        return returnVar.name + " = " + var1_str + " " + op + " " + var2_str;
    }

    public void constructMemoryStageOne() {
//        if (!var1.name.equals("RET") && !var1.name.equals("GETINT")) {
        if (returnVar.dim == 0) {
            if (var1.isTmp) {
//                    currentFunc.tmpMem -= 4;
            }
            if (var2 != null && var2.isTmp) {
//                    currentFunc.tmpMem -= 4;
            }
            if (returnVar.isTmp) {
                returnVar.addr = currentFunc.tmpMem;
                currentFunc.tmpMem += 4;
                if (currentFunc.tmpMem > currentFunc.maxTmpMem) {
                    currentFunc.maxTmpMem = currentFunc.tmpMem;
                }
            }
        }
    }

    @Override
    public void constructMemoryStageTwo() {
        if (returnVar.isTmp) {
            returnVar.addr += currentFunc.localMem;
            returnVar.addr -= currentFunc.totalMem;
        }
    }

    public void genMipsCode(){
        mipsCodeManger.addText("# "+this);
        mipsCodeManger.addLi( "t0", 0);
        genVarToRegMips(var1, "t1");
        if (var2 != null) {
            genVarToRegMips(var2, "t2");
        } else {
            genVarToRegMips(new Var(0), "t2");
        }
        String label = LabelManager.getLabel();
        if (Objects.equals(op, "bne")){
            mipsCodeManger.addCondBranch("beq", "t1", "t2", label);
        } else if (Objects.equals(op, "beq")){
            mipsCodeManger.addCondBranch("bne", "t1", "t2", label);
        } else if (Objects.equals(op, "bge")){
            mipsCodeManger.addCondBranch("blt", "t1", "t2", label);
        } else if (Objects.equals(op, "ble")){
            mipsCodeManger.addCondBranch("bgt", "t1", "t2", label);
        } else if (Objects.equals(op, "bgt")){
            mipsCodeManger.addCondBranch("ble", "t1", "t2", label);
        } else if (Objects.equals(op, "blt")){
            mipsCodeManger.addCondBranch("bge", "t1", "t2", label);
        }
        mipsCodeManger.addLi( "t0", 1);
        mipsCodeManger.addLabel(label);

        genRegToVarMips(returnVar, "t0");
        mipsCodeManger.addText("");
    }

}
