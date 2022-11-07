package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class ConstDecl extends SynUnit {
    String type = INTTK;
    public ConstDecl(BaseUnit baseUnit) {
        super(baseUnit, "ConstDecl");
    }

    public void construct(){
        init();

        checkSymNow(CONSTTK);

        checkSynUnit(BTYPE);
        checkSynUnit(CONSTDEF);

        while(Objects.equals(getSymNow(), COMMA)){
            checkSymNow(COMMA);
            checkSynUnit(CONSTDEF);
        }
        checkSemicnNow();

        pushTree(this);
    }

    public void createTable(){
        checkChild(CONSTTK);

        createChildTable(BTYPE);
        type = ((BType) childUnit).type;

        createChildTable(CONSTDEF);
        while(isChildMatch(COMMA)){
            checkChild(COMMA);
            createChildTable(CONSTDEF);
        }
        checkChild(SEMICN);

    }
}
