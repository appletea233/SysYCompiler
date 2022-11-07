package syntatic.unit;

import base.BaseUnit;
import base.Func;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.COMMA;
import static base.Type.FUNCFPARAM;

public class FuncFParams extends SynUnit {
    public Func func;

    public FuncFParams(BaseUnit baseUnit) {
        super(baseUnit, "FuncFParams");
    }

    public void construct(){
        init();
        checkSynUnit("FuncFParam");
        while(Objects.equals(getSymNow(), COMMA)){
            checkList(new String[]{COMMA, "FuncFParam"});
        }
        pushTree(this);
    }

    public void createTable(){
        func = ((FuncDef)parent).func;
        createChildTable(FUNCFPARAM);
        while(isChildMatch(COMMA)){
            checkChild(COMMA);
            createChildTable(FUNCFPARAM);
        }
        func.closeDeclare();
    }

}
