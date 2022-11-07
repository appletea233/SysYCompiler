package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class UnaryOp extends SynUnit {
    int opValue = 0;
    public UnaryOp(BaseUnit baseUnit) {
        super(baseUnit, "UnaryOp");
    }

    public void construct(){
        init();

        if (Objects.equals(sym, PLUS) || Objects.equals(sym, MINU) || Objects.equals(sym, NOT)){
            pushTreeNow();
            if (sym.equals(PLUS)){
                opValue = 1;
            }
            else if (sym.equals(MINU)){
                opValue = -1;
            }
            else if (sym.equals(NOT)){
                opValue = 0;
            }

        }
        else{
            System.out.println("unary op is not +/-/!");
        }
        pushTree(this);
    }

    public void getValue(){
        reset();
        System.out.println("======================UnaryOp getValue");

        System.out.println(getChildNow());


        if (isChildMatch(PLUS)){
            opValue = 1;
        }
        else if (isChildMatch(MINU)){
            opValue = -1;
        }
        else if (isChildMatch(NOT)){
            opValue = 0;
        }
    }

    @Override
    public void genMiddleCode() {

    }
}
