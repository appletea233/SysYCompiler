package syntatic.unit;

import base.BaseUnit;
import base.Var;
import lexical.unit.IntCon;
import syntatic.SynUnit;

import java.util.Objects;
import java.util.Vector;

import static base.Type.COMMA;
import static base.Type.EXP;

public class FuncRParams extends SynUnit {
    public int paramCount = 0;
    public Vector<Integer> dims = new Vector<Integer>();
    public Vector<Var> inVarList = new Vector<>();

    public FuncRParams(BaseUnit baseUnit) {
        super(baseUnit, "FuncRParams");
    }

    public void construct(){
        int count = 0;

        init();

        checkSynUnit("Exp");
        count += 1;
        while(Objects.equals(sym, COMMA)){
            count += 1;
            pushTreeNow();
            checkSynUnit("Exp");
        }
        this.paramCount = count;
        pushTree(this);
    }

    public void createTable(){
        createChildTable(EXP);
        dims.add(((Exp) childUnit).dim);
        while(isChildMatch(COMMA)){
            checkChild(COMMA);
            createChildTable(EXP);
            dims.add(((Exp) childUnit).dim);
        }
    }

    public void getValue(){
        System.out.println("FuncParams come on!!!");
    }

    public void genMiddleCode(){
        reset();
        genChildMiddleCode(EXP);
        inVarList.add(childUnit.returnVar);
        while(isChildMatch(COMMA)){
            checkChild(COMMA);
            genChildMiddleCode(EXP);
            inVarList.add(childUnit.returnVar);
        }
        System.out.println("invar"+inVarList);
    }
}
