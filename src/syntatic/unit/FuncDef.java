package syntatic.unit;

import base.BaseUnit;
import base.Func;
import middleCode.unit.FuncDefCode;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class FuncDef extends SynUnit {
    Func func = null;
    String name;
    int line = 0;
    String returnType;

    public FuncDef(BaseUnit baseUnit) {
        super(baseUnit, "FuncDef");
    }

    public void construct(){
        String return_type;
        boolean isVoid = false;

        init();
        checkSynUnit("FuncType");
        return_type = ((FuncType)getLatestChild()).type;
        if (isSymNowEql(IDENFR)){
            pushTreeNow();
        }

        checkSymNow(LPARENT);

        getSymNow();
        if (sym.equals(INTTK)){
            checkList(new String[]{"FuncFParams", RPARENT});
        }
        else{
            checkRparentNow();
        }

        if (Objects.equals(return_type, "void"))
            isVoid = true;
        Block block = new Block(this, isVoid, true);
        block.construct();
        addChildren(block);

        pushTree(this);
    }

    public void createTable(){
        reset();
        createChildTable(FUNCTYPE);
        returnType = ((FuncType)childUnit).type;

        checkChild(IDENFR);
        name = childUnit.content;
        line = childUnit.line;

        // check the error of same name
        if (funcTable.isFuncExistNowLayer(name))
            errorList.addError(line, 'b');
        else {
            func = new Func(name, returnType, line);
            funcTable.addFunc(func);
        }

        checkChild(LPARENT);

        if (!isChildMatch(RPARENT)){
            createChildTable(FUNCFPARAMS);
        }
        checkChild(RPARENT);
        createChildTable(BLOCK);
    }

    public void genMiddleCode(){
        func.name = "func_" + func.name;

        reset();
        genChildMiddleCode(FUNCTYPE);

        checkChild(IDENFR);
        middleCodeList.addCode(new FuncDefCode(func));
        checkChild(LPARENT);

        if (!isChildMatch(RPARENT)){
            genChildMiddleCode(FUNCFPARAMS);
        }
        checkChild(RPARENT);
        genChildMiddleCode(BLOCK);
    }
}
