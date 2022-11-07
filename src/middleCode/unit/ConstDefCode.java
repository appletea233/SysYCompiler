package middleCode.unit;

import middleCode.MiddleCode;

public class ConstDefCode extends MiddleCode {
    String name;
    int value = 0;

    public ConstDefCode(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "const int " + name + " " + value;
    }
}
