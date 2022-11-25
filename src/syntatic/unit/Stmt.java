package syntatic.unit;

import base.BaseUnit;
import base.Var;
import lexical.unit.StrCon;
import middleCode.LabelManager;
import middleCode.MiddleCodeList;
import middleCode.unit.ExpCode;
import middleCode.unit.LabelCode;
import middleCode.unit.ReturnCode;
import syntatic.SynUnit;

import java.util.Objects;
import java.util.Vector;

import static base.Type.*;

public class Stmt extends SynUnit {
    public boolean isVoid = false;
    public boolean isFunc = false;
    public boolean isLoop = false;

    public String label1 = "";
    public String label2 = "";

    public Stmt(BaseUnit parent) {
        super(parent, "Stmt");
        if (parent.name.equals("BlockItem")){
            BlockItem blockItem = (BlockItem) parent;
            this.isVoid = blockItem.isVoid;
            this.isFunc = blockItem.isFunc;
            this.isLoop = blockItem.isLoop;
        }
    }

    public Stmt(BaseUnit parent, boolean isLoop) {
        super(parent, "Stmt");
        if (parent.name.equals("BlockItem")){
            BlockItem blockItem = (BlockItem) parent;
            this.isVoid = blockItem.isVoid;
            this.isFunc = blockItem.isFunc;
            this.isLoop = blockItem.isLoop;
        }
        this.isLoop = isLoop;
    }

    public void construct(){
        init();
        if (Objects.equals(sym, LBRACE)){
            Block block = new Block(this, this.isLoop);
            block.construct();
            addChildren(block);

        }
        else if (Objects.equals(sym, IFTK)){
            checkSymNow(IFTK);
            checkSymNow(LPARENT);
            checkSynUnit(COND);

            checkSymNow(RPARENT);
            Stmt stmt = new Stmt(this, this.isLoop);
            stmt.construct();
            addChildren(stmt);

            if (Objects.equals(getSymNow(), ELSETK)){
                checkSymNow(ELSETK);
                stmt = new Stmt(this, this.isLoop);
                stmt.construct();
                addChildren(stmt);
            }
        }
        else if (Objects.equals(sym, WHILETK)){
            checkSymNow(WHILETK);
            checkSymNow(LPARENT);
            checkSynUnit(COND);
            checkSymNow(RPARENT);

            Stmt stmt = new Stmt(this, true);
            stmt.construct();
            addChildren(stmt);
        }
        else if (Objects.equals(sym, BREAKTK)){
            checkSymNow(BREAKTK);
            checkSemicnNow();
        }
        else if (Objects.equals(sym, CONTINUETK)){
            checkSymNow(CONTINUETK);
            checkSymNow(SEMICN);
        }
        else if (Objects.equals(sym, RETURNTK)){
            checkSymNow(RETURNTK);
            if (isSymNowEql(SEMICN)){
                checkSymNow(SEMICN);
            }
            else if (isSymNowEql(new String[]{LPARENT, IDENFR, INTCON, PLUS, MINU, NOT})){
                checkSynUnit(EXP);

                checkSemicnNow();
            }
            else{
                checkSemicnNow();
            }

        }
        else if (Objects.equals(sym, PRINTFTK)){
            checkSymNow(PRINTFTK);
            checkSymNow(LPARENT);
            checkSymNow(STRCON);

            while(Objects.equals(getSymNow(), COMMA)){
                checkSymNow(COMMA);
                checkSynUnit(EXP);
            }

            checkSymNow(RPARENT);
            checkSemicnNow();

        }
        else if (isSymNowEql(new String[]{LPARENT, INTCON, PLUS, MINU})){
            checkSynUnit(EXP);

            checkSemicnNow();
        }
        else if (Objects.equals(sym, SEMICN)){
            checkSymNow(SEMICN);
        }
        else if (Objects.equals(sym, IDENFR)){
            symbolList.getToken(2);
            if (Objects.equals(symbolList.token.get(1), LPARENT)){
                checkSynUnit(EXP);
                checkSymNow(SEMICN);
            }
            else{
                int idx_tmp = symbolList.idx;
                treeList.setTreePush(false);

                LVal lval = new LVal(this);
                lval.construct();

                getSymNow();
                if (Objects.equals(sym, ASSIGN)){
                    // TODO: 缕清逻辑
                    symbolList.moveBack();

                    getSymNow();
                    if (is_exp(sym)){
                        symbolList.idx = idx_tmp;
                        treeList.setTreePush(true);

                        lval = new LVal(this);
                        lval.construct();
                        this.addChildren(lval);

                        checkList(new String[]{ASSIGN, "Exp", SEMICN});
                    }
                    else if (isSymNowEql(GETINTTK)){
                        symbolList.idx = idx_tmp;
                        treeList.setTreePush(true);

                        lval = new LVal(this);
                        lval.construct();
                        this.addChildren(lval);
                        int line = lval.line;

                        checkList(new String[]{ASSIGN, GETINTTK, LPARENT, RPARENT, SEMICN});
                    }

                    else{
                        System.out.println("assign but unmatch");
                    }
                }
                else if (isSymNowEql(new String[]{MULT, DIV, MOD, PLUS, MINU, SEMICN})){
                    symbolList.idx = idx_tmp;
                    treeList.setTreePush(true);

                    checkList(new String[]{"Exp", SEMICN});
                }
                else{
                    System.out.println("not exp or lval =");
                }
            }
        }
        pushTree(this);
    }

    public void createTable(){
        if (isChildMatch(RETURNTK)){
            checkChild(RETURNTK);
            line = childUnit.line;
            if (isChildMatch(EXP)){
                if (isFunc && isVoid){
                    errorList.addError(line, 'f');
                }
                createChildTable(EXP);
            }
        }
        else if (isChildMatch(LVAL)){
            createChildTable(LVAL);
            boolean isConst = ((LVal) childUnit).var.isConst;
            int line = childUnit.line;
            if (isChildMatch(ASSIGN)){
                checkChild(ASSIGN);
                if (isConst)
                    errorList.addError(line, 'h');
            }
            if (isChildMatch(EXP)){
                createChildTable(EXP);
            }
            else if (isChildMatch(GETINTTK)){
            }
        }
        else if (isChildMatch(PRINTFTK)){
            int realCount = 0;
            int varCount = 0;
            checkChild(PRINTFTK);
            int line = childUnit.line;
            checkChild(LPARENT);
            createChildTable(STRCON);
            varCount = ((StrCon) childUnit).count;

            while(isChildMatch(COMMA)){
                realCount ++;
                checkChild(COMMA);
                createChildTable(EXP);
            }
            if (varCount != realCount){
                errorList.addError(line, 'l');
            }

            checkChild(RPARENT);
        }
        else if (isChildMatch(BREAKTK)){
            checkChild(BREAKTK);
            int line = childUnit.line;
            if (! isLoop)
                errorList.addError(line, 'm');
        }
        else if (isChildMatch(CONTINUETK)){
            checkChild(CONTINUETK);
            int line = childUnit.line;
            if (! isLoop)
                errorList.addError(line, 'm');
        }
        else if (isChildMatch(EXP)){
            createChildTable(EXP);
        }
        else if (isChildMatch(IFTK)){
            checkChild(IFTK);
            checkChild(LPARENT);
            createChildTable(COND);
            checkChild(RPARENT);
            createChildTable(STMT);
            while(isChildMatch(ELSETK)){
                checkChild(ELSETK);
                createChildTable(STMT);
            }
        }
        else if (isChildMatch(WHILETK)){
            checkChild(WHILETK);
            checkChild(LPARENT);
            createChildTable(COND);
            checkChild(RPARENT);
            createChildTable(STMT);
        }
        else if (isChildMatch(BLOCK)){
            createChildTable(BLOCK);
        }
        else if (isChildMatch(SEMICN)){
            checkChild(SEMICN);
        }
        else{
            System.out.println("Stmt is not defined with "+getChildNow().name);
        }

    }

    public void genMiddleCode(){
        reset();
        if (isChildMatch(RETURNTK)){
            Var returnVar = null;
            checkChild(RETURNTK);
            if (isChildMatch(EXP)){
                genChildMiddleCode(EXP);
                returnVar = childUnit.returnVar;
            }
            if (returnVar != null)
                middleCodeList.addCode(new ReturnCode(returnVar));
            else
                middleCodeList.addCode(new ReturnCode(null));

        }
        else if (isChildMatch(LVAL)){
            Var var1;
            Var var2;
            genChildMiddleCode(LVAL);
            var1 = childUnit.returnVar;

            checkChild(ASSIGN);

            if (isChildMatch(EXP)){
                genChildMiddleCode(EXP);
                var2 = childUnit.returnVar;
                middleCodeList.addCode(new ExpCode(var1, var2));
            }
            else if (isChildMatch(GETINTTK)){
                var2 = new Var("GETINT");
                middleCodeList.addCode(new ExpCode(var1, var2));
            }
        }
        else if (isChildMatch(PRINTFTK)){
            checkChild(PRINTFTK);
            checkChild(LPARENT);
            genChildMiddleCode(STRCON);
            StrCon strCon = (StrCon) childUnit;

            Vector<Var> varList = new Vector<>();
            while(isChildMatch(COMMA)){
                checkChild(COMMA);
                genChildMiddleCode(EXP);
                varList.add(childUnit.returnVar);
            }

            strCon.varList = varList;
            strCon.genStr();

            checkChild(RPARENT);
        }
        else if (isChildMatch(BREAKTK)){
            checkChild(BREAKTK);
        }
        else if (isChildMatch(CONTINUETK)){
            checkChild(CONTINUETK);
        }
        else if (isChildMatch(EXP)){
            genChildMiddleCode(EXP);
        }
        else if (isChildMatch(IFTK)){

            String label1 = LabelManager.getLabel();        // cond == 1
            String label2 = LabelManager.getLabel();        // cond == 0
            checkChild(IFTK);
            checkChild(LPARENT);
            Cond cond = (Cond)getChildNow();
            cond.genMiddleCode(label1, label2);
            childIdx++;
            checkChild(RPARENT);
            middleCodeList.addCode(new LabelCode(label1));
            genChildMiddleCode(STMT);
            System.out.println(returnVar);
            middleCodeList.addCode(new LabelCode(label2));
            while(isChildMatch(ELSETK)){
                checkChild(ELSETK);
                genChildMiddleCode(STMT);
            }
        }
        else if (isChildMatch(WHILETK)){
            String label1 = LabelManager.getLabel();        // cond == 1
            String label2 = LabelManager.getLabel();        // cond == 0
            checkChild(WHILETK);
            checkChild(LPARENT);
            Cond cond = (Cond)getChildNow();
            cond.genMiddleCode(label1, label2);
            childIdx++;
            checkChild(RPARENT);
            middleCodeList.addCode(new LabelCode(label1));
            genChildMiddleCode(STMT);
            middleCodeList.addCode(new LabelCode(label2));
        }
        else if (isChildMatch(BLOCK)){
            genChildMiddleCode(BLOCK);
        }
        else if (isChildMatch(SEMICN)){
            checkChild(SEMICN);
        }
        else{
            System.out.println("Stmt is not defined with "+getChildNow().name);
        }
    }


}
