package syntatic.unit;

import base.BaseUnit;
import base.Var;
import base.VarTable;
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
            value = var.value;
            System.out.println(LVAL+": "+var);
            // 常量函数赋值
            // TODO 数组常量的存取
            while(isChildMatch(LBRACK)){
                checkChild(LBRACK);
                createChildTable(EXP);
                checkChild(RBRACK);
            }
        }
    }

    public void genMiddleCode(){
        if (dim == 0){
            if (var.dim == 0){
                returnVar = var;
            }
            else{
                returnVar = VarTable.getTmpVar();
            }
        }
        else {

        }
    }
}
