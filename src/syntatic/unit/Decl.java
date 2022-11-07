package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class Decl extends SynUnit{

    public Decl(BaseUnit baseUnit) {
        super(baseUnit, "Decl");
        if (Objects.equals(baseUnit.name, COMPUNIT)){
            this.isGlobal = true;
        }
    }

    public void construct(){
        init();
        if (Objects.equals(sym, CONSTTK)){
            checkSynUnit(CONSTDECL);
        }
        else if (Objects.equals(sym, INTTK)){
            checkSynUnit(VARDECL);
        }
        else{
            System.out.println("Decl is not const or var...");
        }
    }
}
