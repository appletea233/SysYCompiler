package middleCode.unit;

import base.Str;
import middleCode.MiddleCode;

public class StrDefCode extends MiddleCode {
    Str str;
    public StrDefCode(Str str) {
        this.cls = "StrDefCode";
        this.str = str;
    }

    @Override
    public String toString() {
        return "const "+ str.name + " = \"" + str.content + '\"';
    }

    public void genMipsCode(){
        mipsCodeManger.addStrCon(str.name, str.content);
    }
}
