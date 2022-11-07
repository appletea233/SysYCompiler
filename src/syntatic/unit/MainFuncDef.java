package syntatic.unit;

import base.BaseUnit;
import base.Func;
import middleCode.unit.FuncDefCode;
import syntatic.SynUnit;

import static base.Type.*;

public class MainFuncDef extends SynUnit {
    public MainFuncDef(BaseUnit baseUnit) {
        super(baseUnit, "MainFuncDef");
    }

    public void construct(){
        init();
        checkList(new String[]{INTTK, MAINTK, LPARENT, RPARENT});

        Block block = new Block(this, false, true);
        block.construct();
        addChildren(block);

        pushTree(this);
    }

    public void genMiddleCode(){
        checkChild(INTTK);
        checkChild(MAINTK);
        checkChild(LPARENT);
        checkChild(RPARENT);
        middleCodeList.addCode(new FuncDefCode(new Func("func_main", "int", childUnit.line)));
        genChildMiddleCode(BLOCK);
    }
}
