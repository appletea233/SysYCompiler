package lexical.unit;

import base.Var;
import lexical.Symbol;

import static base.Type.INTCON;

public class IntCon extends Symbol {
    public IntCon(String content, int line){
        super(INTCON, content, line);
        this.isConst = true;
    }

    public void getValue(){
//        TODO: 这里做了两边
        value = 0;
        for (int idx = 0; idx<content.length(); idx++){
            value *= 10;
            value += (content.charAt(idx) - '0');
        }
        System.out.println("IntCon Value "+value);
    }

    public void genMiddleCode(){
        returnVar = new Var(value);
    }
}
