package syntatic.unit;

import base.BaseUnit;
import base.Func;
import base.Var;
import base.VarTable;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import middleCode.unit.ReturnCode;
import syntatic.SynUnit;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Objects;

import static base.Type.*;

public class Block extends SynUnit {
    public boolean isVoid = false;
    public boolean isFunc = false;
    public boolean isLoop = false;
    public boolean isReturnExist = false;

    Func func = null;

    public Block(BaseUnit baseUnit){
        super(baseUnit, "Block");
    }
    Block(BaseUnit baseUnit, boolean isVoid, boolean isFunc){
        super(baseUnit, "Block");
        this.isVoid = isVoid;
        this.isFunc = isFunc;
    }
    Block(BaseUnit baseUnit, boolean isLoop){
        super(baseUnit, "Block");
        this.isLoop = isLoop;
    }
    public void init(){
        super.init();
    }

    public void construct(){
        init();

        checkSymNow(LBRACE);
        while(!Objects.equals(getSymNow(), RBRACE)){
//            System.out.println("block continue " +getSymNow());
            checkSynUnit(BLOCKITEM);
        }

        checkSymNow(RBRACE);
        pushTree(this);
    }
    public void createTable() {
        if (this.isFunc && this.parent.name.equals(FUNCDEF)) {
            func = ((FuncDef) (this.parent)).func;
            for (Var var : func.inVarList) {
                this.varTable.addVar(var);
            }
        }

        checkChild(LBRACE);
        while(isChildMatch(BLOCKITEM)){
            createChildTable(BLOCKITEM);
        }
        checkChild(RBRACE);

        if (isFunc && ! isVoid) {
            boolean res = false;
            BaseUnit unit = children.get(children.size() - 2);
            if (unit.name.equals(BLOCKITEM)) {
                unit = unit.children.get(0);
                if (unit.name.equals(STMT)){
                    if (unit.children.get(0).name.equals(RETURNTK))
                        res = true;
                }
            }
            if (! res)
                errorList.addError(children.lastElement().line, 'g');
        }



    }

    public void genMiddleCode(){
        super.genMiddleCode();


        System.out.println("---------------" + isFunc + " " + isVoid);
        if (isFunc && isVoid) {
            boolean res = false;
            BaseUnit unit = children.get(children.size() - 2);
            if (unit.name.equals(BLOCKITEM)) {
                unit = unit.children.get(0);
                if (unit.name.equals(STMT)){
                    if (unit.children.get(0).name.equals(RETURNTK))
                        res = true;
                }
            }
            System.out.println("------------++++++++++++");
            if (!res){
                middleCodeList.addCode(new ReturnCode(null));
            }
        }
    }

    public void showDetail(){
        varTable.showDetail();
        super.showDetail();
    }
}




