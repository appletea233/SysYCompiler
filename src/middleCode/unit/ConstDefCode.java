package middleCode.unit;

import middleCode.MiddleCode;

import java.util.Vector;

public class ConstDefCode extends MiddleCode {
    String name;
    int value = 0;
    Vector<Integer> arrayValue;
    boolean isArray = false;

    public ConstDefCode(String name, int value) {
        this.name = name;
        this.value = value;
    }
    public ConstDefCode(String name, Vector<Integer> arrayValue) {
        this.name = name;
        this.arrayValue = arrayValue;
        isArray = true;
    }

    @Override
    public String toString() {
        if (isArray){
            String values = "";
            for (int value: arrayValue){
                values += value + " ";
            }
            return "array const int " + name + " " + values;
        }
        else
            return "const int " + name + " " + value;
    }
}
