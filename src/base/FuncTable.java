package base;

import syntatic.unit.FuncRParams;

import java.util.Vector;

public class FuncTable {
    public FuncTable parent = null;
    public BaseUnit block = null;
    public int layer = 0;
    public Vector<Func> table = new Vector<>();
    public static int funcNum = 0;

    FuncTable(BaseUnit baseUnit, FuncTable parent){
        this.block = baseUnit;
        this.parent = parent;
        if (parent != null){
            this.layer = parent.layer + 1;
        }
    }

    public boolean addFunc(String name, String type, int line){
        if(!isFuncExistNowLayer(name)){
            table.add(new Func(name, type, line));
            return true;
        }
        return false;
    }

    public boolean isFuncExistNowLayer(String name){
        for (Func func: table){
            if (func.name.equals(name))
                return true;
        }
        return false;
    }

    public boolean isFuncExist(String name){
        if (isFuncExistNowLayer(name))
            return true;
        else{
            if (parent != null)
                return parent.isFuncExist(name);
            else
                return false;
        }
    }

    public Func getFunc(String name){
        for (Func var: table){
            if (var.name.equals(name))
                return var;
        }
        return parent.getFunc(name);
    }

    public boolean addFunc(Func func) {
        if(!isFuncExistNowLayer(func.name)){
            table.add(func);
            return true;
        }
        return false;
    }
}
