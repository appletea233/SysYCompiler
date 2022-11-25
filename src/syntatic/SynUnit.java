package syntatic;

import base.BaseUnit;
import base.Type;
import syntatic.unit.*;
import syntatic.unit.Number;

import java.util.Objects;

public class SynUnit extends BaseUnit {
    public static BaseUnit symUnit;
    public static String sym;
    public BaseUnit childUnit;

    public SynUnit(BaseUnit baseUnit, String name) {
        super(baseUnit, name);
    }

    public void init(){
        this.content = "";
        getSymNow();
        this.line = symUnit.line;
    }

    public String getSymNow() {
        symUnit = symbolList.getSymNow();
        sym = symUnit.name;
        return sym;
    }

    public void addChildren(BaseUnit baseUnit){
        children.add(baseUnit);
    }

    public BaseUnit getLatestChild(){
        return children.lastElement();
    }

    public boolean isSymNowEql(String correct){
        return getSymNow().equals(correct);
    }

    public boolean isSymNowEql(String[] correct){
        for (String str: correct){
            if (isSymNowEql(str))
                return true;
        }
        return false;
    }

    public void pushTree(BaseUnit baseUnit){
        treeList.addUnit(baseUnit);
    }

    public void pushTreeNow(){
        getSymNow();
        pushTree(symUnit);
        symbolList.moveBack();
        addChildren(symUnit);
    }

    public void checkSymNowPre(String correct){
        if (isSymNowEql(correct)) {
            BaseUnit baseUnit = symbolList.getSymNow();
            addChildren(baseUnit);
            pushTree(baseUnit);
            // move symbol list idx
            symbolList.moveBack();
        }
    }

    public void checkSemicnNow() {
        // check error of ; not exists
        if (isSymNowEql("SEMICN"))
            checkSymNowPre("SEMICN");
        else {

            System.out.println("; not exists" + " now " + getSymNow());
            int line = getLatestLine(this);
            pushTree(new BaseUnit("SEMICN", ";", line));
            errorList.addError(getLatestLine(this), 'i');
        }
    }

    public void checkRparentNow() {
        if (isSymNowEql("RPARENT"))
            checkSymNowPre("RPARENT");
        else {
            assert false;
            System.out.println(") not exists");
            int line = getLatestLine(this);
            pushTree(new BaseUnit("RPARENT", ")", line));
            errorList.addError(line, 'j');
        }
    }

    public void checkRbrackNow() {
        if (isSymNowEql("RBRACK"))
            checkSymNowPre("RBRACK");
        else {
            System.out.println("] not exists");

            int line = getLatestLine(this);
            pushTree(new BaseUnit("RBRACK", "]", line));
            errorList.addError(getLatestLine(this), 'k');
        }
    }

    public void checkSymNow(String correct){
        if (correct.equals("SEMICN")){
            checkSemicnNow();
        }
        else if (correct.equals("RPARENT")){
            checkRparentNow();
        }
        else if (correct.equals("RBRACK")){
            checkRbrackNow();
        }
        else{
//            if (correct.equals(LPARENT))
//                System.out.println("( is " + getSymNow() + (Objects.equals(getSymNow(), correct)));
            checkSymNowPre(correct);
        }


    }

    public void checkSynUnit(String name) {
        BaseUnit baseUnit = null;
        if (Objects.equals(name, "Block"))    baseUnit = new Block(this);
        else if (Objects.equals(name, "BlockItem"))    baseUnit = new BlockItem(this);
        else if (Objects.equals(name, "Stmt"))    baseUnit = new Stmt(this);
        else if (Objects.equals(name, "CompUnit"))    baseUnit = new CompUnit(this);
        else if (Objects.equals(name, "Decl"))    baseUnit = new Decl(this);
        else if (Objects.equals(name, "ConstDecl"))    baseUnit = new ConstDecl(this);
        else if (Objects.equals(name, "VarDecl"))    baseUnit = new VarDecl(this);
        else if (Objects.equals(name, "BType"))    baseUnit = new BType(this);
        else if (Objects.equals(name, "AddExp"))    baseUnit = new AddExp(this);
        else if (Objects.equals(name, "ConstExp"))    baseUnit = new ConstExp(this);
        else if (Objects.equals(name, "Exp"))    baseUnit = new Exp(this);
        else if (Objects.equals(name, "MulExp"))    baseUnit = new MulExp(this);
        else if (Objects.equals(name, "UnaryExp"))    baseUnit = new UnaryExp(this);
        else if (Objects.equals(name, "PrimaryExp"))    baseUnit = new PrimaryExp(this);
        else if (Objects.equals(name, "LVal"))    baseUnit = new LVal(this);
        else if (Objects.equals(name, "UnaryOp"))    baseUnit = new UnaryOp(this);
        else if (Objects.equals(name, "Number"))    baseUnit = new Number(this);
        else if (Objects.equals(name, "RelExp"))    baseUnit = new RelExp(this);
        else if (Objects.equals(name, "EqExp"))    baseUnit = new EqExp(this);
        else if (Objects.equals(name, "LAndExp"))    baseUnit = new LAndExp(this);
        else if (Objects.equals(name, "LOrExp"))    baseUnit = new LOrExp(this);
        else if (Objects.equals(name, "Cond"))    baseUnit = new Cond(this);
        else if (Objects.equals(name, "InitVal"))    baseUnit = new InitVal(this);
        else if (Objects.equals(name, "ConstInitVal"))    baseUnit = new ConstInitVal(this);
        else if (Objects.equals(name, "FuncRParams"))    baseUnit = new FuncRParams(this);
        else if (Objects.equals(name, "FuncDef"))    baseUnit = new FuncDef(this);
        else if (Objects.equals(name, "FuncType"))    baseUnit = new FuncType(this);
        else if (Objects.equals(name, "FuncFParams"))    baseUnit = new FuncFParams(this);
        else if (Objects.equals(name, "FuncFParam"))    baseUnit = new FuncFParam(this);
        else if (Objects.equals(name, "MainFuncDef"))    baseUnit = new MainFuncDef(this);
        else if (Objects.equals(name, "VarDef"))    baseUnit = new VarDef(this);
        else if (Objects.equals(name, "ConstDef"))    baseUnit = new ConstDef(this);

        baseUnit.construct();
        addChildren(baseUnit);
    }

    public void checkList(String[] list){
        for (String s: list){
            boolean flag = false;
            for(String syn_unit: Type.synUnits){
                if (Objects.equals(s, syn_unit)) {
                    checkSynUnit(syn_unit);
                    flag = true;
                    break;
                }
            }
            if (!flag){
                checkSymNow(s);
            }
        }
    }

    public int getLatestLine(BaseUnit baseUnit) {
        System.out.println(baseUnit.name);
        while(baseUnit.children.lastElement() != null){
            baseUnit = baseUnit.children.lastElement();
            if (! baseUnit.content.equals("")){
                return baseUnit.line;
            }
        }
        return 0;
    }

    public BaseUnit getChildNow(){
        return children.get(childIdx);
    }

    public boolean isChildMatch(String correct){
        if (childIdx>=children.size())
            return false;
        return getChildNow().name.equals(correct);
    }

    public boolean createChildTable(String correct){
        if (isChildMatch(correct)) {
            childUnit = getChildNow();
            childUnit.createTable();
            childIdx++;
            return true;
        }
        else{
            if (childIdx>=children.size())
                System.out.println("SynUnit createChildTable Children Out Of Idx");
            else
                System.out.println("SynUnit createChildTable Child Unmatched "+correct + " " + getChildNow().line + " " + getChildNow().name);
            return false;
        }
    }

    public void getChildValue(String correct){
        if (isChildMatch(correct)) {
            childUnit = getChildNow();
            childUnit.getValue();
            childIdx++;
        }
        else{
            if (childIdx>=children.size())
                System.out.println("SynUnit getChildValue Children Out Of Idx");
            else
                System.out.println("SynUnit getChildValue Child Unmatched "+correct + " " + getChildNow().line + " " + getChildNow().name);
        }
    }

    protected void genChildMiddleCode(String correct) {
        if (isChildMatch(correct)) {
            childUnit = getChildNow();
            childUnit.genMiddleCode();
            childIdx++;
        }
        else{
            if (childIdx>=children.size())
                System.out.println("SynUnit genChildMiddleCode Children Out Of Idx");
            else
                System.out.println("SynUnit genChildMiddleCode Child Unmatched "+correct + " " + getChildNow().line + " " + getChildNow().name);
        }
    }

    public boolean checkChild(String correct){
        if (isChildMatch(correct)) {
            childUnit = getChildNow();
            childIdx++;
            return true;
        }
        else{
            if (childIdx>=children.size())
                System.out.println("checkChild children out of idx");
            else
                System.out.println("checkChild unmatched "+correct+ " "+getChildNow().line + " "+getChildNow().name);
            return false;
        }
    }


}
