package middleCode.unit;

import base.Var;
import middleCode.MiddleCode;

public class BranchCode extends MiddleCode {
    String label;
    Var var;

    public BranchCode(Var var, String label) {
        this.label = label;
        this.var = var;
    }

    @Override
    public String toString() {
        String var_str = var.isConst ? String.valueOf(var.value) : var.name;
        return "if " + var_str + " goto " + label;
    }

    public void genMipsCode(){
        mipsCodeManger.addText("# " + this);
        genVarToRegMips(var, "t1");
        mipsCodeManger.addCondBranch("bne", "t1", 0, label);
    }

}
