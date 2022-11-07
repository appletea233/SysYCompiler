package base;

import error.ErrorList;
import lexical.SymbolList;
import middleCode.MiddleCodeList;
import syntatic.TreeList;

import java.util.Objects;
import java.util.Vector;

public class BaseUnit {
    public String name;
    public String content = "";
    public int line;
    public int value = 0;
    public Var var;
    public Var returnVar;
    public boolean isConst = false;
    public boolean isInit = false;      // 定义声明字段中的表达式
    public boolean isGlobal = false;      // 定义声明字段中的表达式

    public BaseUnit parent = null;
    public int childIdx = 0;
    public Vector<BaseUnit> children = new Vector<BaseUnit>();


    protected VarTable varTable;
    protected FuncTable funcTable;

    public static SymbolList symbolList = new SymbolList();
    public static TreeList treeList = new TreeList();
    public static ErrorList errorList = new ErrorList();
    public static MiddleCodeList middleCodeList = new MiddleCodeList();

    public BaseUnit(BaseUnit baseUnit, String name) {
        this.parent = baseUnit;
        this.name = name;

        if (parent != null) {
            if (Objects.equals(name, "Block")) {
                this.varTable = new VarTable(this, parent.varTable);
                this.funcTable = new FuncTable(this, parent.funcTable);
            }
            else{
                this.varTable = parent.varTable;
                this.funcTable = parent.funcTable;
            }
        }
        else{
            // For CompUnit
            this.varTable = new VarTable(this, null);
            this.funcTable = new FuncTable(this, null);
        }

        // set Const Var
        if (parent!=null){
            if (parent.isConst){
                this.isConst = true;
            }
            if (parent.isInit){
                this.isInit = true;
            }
            if (parent.isGlobal){
                this.isGlobal = true;
            }
        }
    }

    public void construct(){

    }

    public BaseUnit(String name, String content, int line) {
        this.name = name;
        this.content = content;
        this.line = line;
    }

    public BaseUnit() {}


    public void createTable() {
        for (BaseUnit child: children){
            child.createTable();
        }
    }

    public void getValue(){
        for (BaseUnit unit: children){
            unit.getValue();
        }
    }

    public void genMiddleCode(){
        for (BaseUnit unit: children){
            unit.genMiddleCode();
        }
    }

    public void reset(){
        childIdx = 0;
    }

    public void showDetail(){
        for (BaseUnit unit: children){
            unit.showDetail();
        }
    }
}
