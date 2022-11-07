package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.INTTK;

public class BType extends SynUnit {
    String type = INTTK;

    public BType(BaseUnit baseUnit) {
        super(baseUnit, "BType");
    }

    public void construct(){
        init();

        if (Objects.equals(sym, INTTK)){
            type = "int";
            checkSymNow(INTTK);
        }
        else{
            System.out.println("BType is not int...");
        }
    }
}
