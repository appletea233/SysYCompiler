package middleCode.unit;

import middleCode.MiddleCode;

public class LabelCode extends MiddleCode {
    String name;

    public LabelCode(String name) {
        this.name = name;
        this.cls = "LabelCode";
    }

    @Override
    public String toString() {
        return "\n"+ name + ':';
    }
}
