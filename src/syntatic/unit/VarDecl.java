package syntatic.unit;

import base.BaseUnit;
import base.Var;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class VarDecl extends SynUnit {
    String type;

    public VarDecl(BaseUnit baseUnit) {
        super(baseUnit, "VarDecl");
    }

    public void construct(){
        checkSynUnit(BTYPE);
        checkSynUnit(VARDEF);


        while(Objects.equals(getSymNow(), COMMA)){
            checkSymNow(COMMA);
            checkSynUnit(VARDEF);
        }
        checkSemicnNow();
        pushTree(this);
    }

    public void createTable(){
        createChildTable(BTYPE);
        type = ((BType) childUnit).type;


        createChildTable(VARDEF);
        while(isChildMatch(COMMA)){
            checkChild(COMMA);
            createChildTable(VARDEF);
        }
        checkChild(SEMICN);
    }


}
