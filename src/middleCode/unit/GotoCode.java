package middleCode.unit;

import middleCode.MiddleCode;

public class GotoCode extends MiddleCode {
    String label;

    public GotoCode(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "goto " + label;
    }

    public void genMipsCode(){
        mipsCodeManger.addJ(label);
    }
}
