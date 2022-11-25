package syntatic.unit;

import base.BaseUnit;
import base.Var;
import base.VarTable;
import middleCode.unit.ArrayGetValueCode;
import middleCode.unit.ExpCode;
import syntatic.SynUnit;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Objects;
import java.util.Vector;

import static base.Type.*;

public class LVal extends SynUnit {
    int dim = 0;
    int line = 0;
    Vector<Var> varVector = new Vector<>();
    String name;
    Vector<Integer> indexList = new Vector<>();

    public LVal(BaseUnit baseUnit) {
        super(baseUnit, "LVal");
    }

    public void construct(){
        init();
        if (Objects.equals(sym, IDENFR)){
            checkSymNow(IDENFR);
            getSymNow();
            while(Objects.equals(sym, LBRACK)){
                checkList(new String[]{LBRACK, "Exp", RBRACK});
                getSymNow();
            }
        }
        else {
            System.out.println("Lval error...");
        }
        pushTree(this);
    }

    public void createTable(){
        checkChild(IDENFR);
        name = childUnit.content;
        line = childUnit.line;
//        TODO: 是否直接将变量拿过来更好
        if (varTable.isVarExist(name)){
            var = varTable.getVar(name);
            dim = var.dim;
        }
        else{
            var = new Var(false, name, INTTK, 0, line);
            System.out.println("Lval ident undefined "+line + " " + name);
            errorList.addError(line, 'c');
        }

        while(isChildMatch(LBRACK)){
            dim--;
            checkChild(LBRACK);
            createChildTable(EXP);
            checkChild(RBRACK);
        }
    }

    public void getValue(){
        reset();
        if (isConst || isGlobal) {
            checkChild(IDENFR);
            this.value = var.value;
            while(isChildMatch(LBRACK)){
                checkChild(LBRACK);
                getChildValue(EXP);
                indexList.add(childUnit.value);
                checkChild(RBRACK);
            }
            if (var.dim != 0) {
                int index = 0;
                for (int i = 0; i < indexList.size() - 1; i++) {
                    index += indexList.get(i) * var.arrayDim.get(i + 1);
                }
                index += indexList.get(indexList.size() - 1);
                this.value = var.arrayValue.get(index);
            }
            System.out.println(LVAL+" Value: "+value);
        }
    }

    public void genMiddleCode(){
        reset();
        checkChild(IDENFR);
        if (dim == 0){
            if (var.dim == 0){
                returnVar = var;
            }
            else{
                Vector<Var> indexVarVector = new Vector<>();
                while(isChildMatch(LBRACK)){
                    checkChild(LBRACK);
                    genChildMiddleCode(EXP);
                    indexVarVector.add(childUnit.returnVar);
                    checkChild(RBRACK);
                }
                if (indexVarVector.size() == 1){
                    returnVar = VarTable.getTmpVar();
                    middleCodeList.addCode(new ArrayGetValueCode(returnVar,var, indexVarVector.get(0)));
                }
                else if (indexVarVector.size() == 2){
                    Var indexVar1 = VarTable.getTmpVar();
                    middleCodeList.addCode(new ExpCode(indexVar1, indexVarVector.get(0), "*", new Var(var.arrayDim.get(1))));
                    Var indexVar2 = VarTable.getTmpVar();
                    middleCodeList.addCode(new ExpCode(indexVar2, indexVar1, "+", indexVarVector.get(1)));

                    returnVar = VarTable.getTmpVar();
                    System.out.println(returnVar);
                    middleCodeList.addCode(new ArrayGetValueCode(returnVar, var, indexVar2));
                }

            }
        }
        else {
            // only when func call
            if (dim == var.dim){
                returnVar = var;
            }
            else{
                Vector<Var> indexVarVector = new Vector<>();
                while(isChildMatch(LBRACK)){
                    checkChild(LBRACK);
                    genChildMiddleCode(EXP);
                    indexVarVector.add(childUnit.returnVar);
                    checkChild(RBRACK);
                }
                if (indexVarVector.size() == 1){
                    returnVar = VarTable.getTmpVar();
                    middleCodeList.addCode(new ExpCode(returnVar, var, "+", indexVarVector.get(0)));
                }
                else{
                    System.out.println("LVal error...");
                }
            }
        }
    }
}
