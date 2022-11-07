package syntatic.unit;

import base.BaseUnit;
import base.VarTable;
import syntatic.SynUnit;

import static base.Type.*;

public class CompUnit extends SynUnit {

    public CompUnit(BaseUnit baseUnit) {
        super(baseUnit, "CompUnit");
    }

    public void construct(){
        init();

        symbolList.getToken(3);
        while(is_decl(symbolList.token) == 1){
            checkSynUnit("Decl");
            symbolList.getToken(3);
        }

        while(is_func_def(symbolList.token) == 1){
            checkSynUnit("FuncDef");
            symbolList.getToken(3);
        }

        checkSynUnit("MainFuncDef");
        pushTree(this);
    }

    public void createTable(){
        reset();
        while (isChildMatch(DECL)){
            createChildTable(DECL);
            childUnit.isGlobal = true;
        }
        while(isChildMatch(FUNCDEF)){
            createChildTable(FUNCDEF);
        }
        createChildTable(MAINFUNCDEF);
    }

    public void showDetail(){
        varTable.showDetail();
        super.showDetail();
    }

}
