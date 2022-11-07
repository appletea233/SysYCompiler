package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class ConstInitVal extends SynUnit {
    public int dim = 0;

    public ConstInitVal(BaseUnit baseUnit) {
        super(baseUnit, "ConstInitVal");
        this.isInit = true;
    }

    public void construct(){
        init();
        // TODO: !
        if (is_const_exp(sym)){
            checkSynUnit("ConstExp");
        }
        else if (Objects.equals(sym, LBRACE)) {
            checkSymNow(LBRACE);
            getSymNow();
            if (Objects.equals(sym, RBRACE)){
                checkSymNow(RBRACE);
            }
            else{
                // TODO: something except
                checkSynUnit("ConstInitVal");
                while(Objects.equals(getSymNow(), COMMA)){
                    checkSymNow(COMMA);
                    checkSynUnit("ConstInitVal");
                }
                checkSymNow(RBRACE);
            }
        }
        else{
            System.out.println("const initival error...");
        }
        pushTree(this);
    }

    public void getValue(){
        childIdx = 0;
        if (isChildMatch(CONSTEXP)){
            getChildValue(CONSTEXP);
            value = childUnit.value;

//            System.out.println("Const Initial Value " + value);
        }
//        TODO 数组常量
    }
}
