package syntatic.unit;

import base.BaseUnit;
import base.Func;
import base.Var;
import middleCode.unit.FuncParaCode;
import syntatic.SynUnit;

import java.util.Objects;

import static base.Type.*;

public class FuncFParam extends SynUnit {
    public Func func;
    public Var var;
    public int dim = 0;
    public String name;
    public String type;
    public int line;

    public FuncFParam(BaseUnit baseUnit) {
        super(baseUnit, "FuncFParam");
    }

    public void construct(){
        init();
        checkSynUnit("BType");
        if (isSymNowEql(IDENFR)) {
            checkSymNow(IDENFR);
        }

        if (Objects.equals(getSymNow(), LBRACK)){
            checkList(new String[]{LBRACK, RBRACK});
            while(Objects.equals(getSymNow(), LBRACK)){
                checkList(new String[]{LBRACK, "ConstExp", RBRACK});
            }
        }

        pushTree(this);
    }

    public void createTable(){
        func = ((FuncFParams)parent).func;

        createChildTable(BTYPE);
        type = ((BType) childUnit).type;

        checkChild(IDENFR);
        name = childUnit.content;
        line = childUnit.line;

        if (isChildMatch(LBRACK)) {
            dim ++;
            checkChild(LBRACK);
            checkChild(RBRACK);
            while (isChildMatch(LBRACK)) {
                dim++;
                checkChild(LBRACK);
                createChildTable(CONSTEXP);
                checkChild(RBRACK);
            }
        }

        // if the function is declaring, add var to it
        // otherwise, it's not declaring or error on it
        if (func.isDeclaring){
            if (func.isInVarList(name)){
                errorList.addError(line, 'b');
            }
            else{
                var = new Var(false, name, type, dim, line);
                func.addInVarList(var);
            }
        }
        else{
            System.out.println("func has been declared");
        }

    }

    public void genMiddleCode(){
        reset();

        genChildMiddleCode(BTYPE);
        checkChild(IDENFR);
        if (dim == 0)
            middleCodeList.addCode(new FuncParaCode(var));
        else{
            middleCodeList.addCode(new FuncParaCode(var));
        }

        if (isChildMatch(LBRACK)) {
            checkChild(LBRACK);
            checkChild(RBRACK);
            while (isChildMatch(LBRACK)) {
                checkChild(LBRACK);
                genChildMiddleCode(CONSTEXP);
                checkChild(RBRACK);
            }
        }

    }
}
