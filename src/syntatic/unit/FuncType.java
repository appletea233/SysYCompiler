package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.INTTK;
import static base.Type.VOIDTK;

public class FuncType extends SynUnit {
    String type;
    public FuncType(BaseUnit baseUnit) {
        super(baseUnit, "FuncType");
    }

    public void construct(){
        init();
        if (Objects.equals(sym, INTTK) || Objects.equals(sym, VOIDTK)){
            if (sym.equals(INTTK))
                type = "int";
            else
                type = "void";
            pushTreeNow();
        }
        else{
            System.out.println("functype error...");
        }
        pushTree(this);
    }
}
