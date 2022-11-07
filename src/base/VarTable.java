package base;

import java.util.Vector;

import static base.Type.INTTK;

public class VarTable {
    public VarTable parent = null;
    public int layer = 0;
    public BaseUnit block = null;
    public Vector<Var> table = new Vector<Var>();
    public static int varNum = 0;
    public static int tmpVarNum = 0;


    VarTable(BaseUnit baseUnit, VarTable parent){
        this.block = baseUnit;
        this.parent = parent;
        if (parent != null){
            this.layer = parent.layer + 1;
        }
    }

    public boolean addVar(boolean isConst, String name, String type, int dim, int line){
        if(! isVarExistNowLayer(name)){
            table.add(new Var(isConst, name, type, dim, line));
            return true;
        }
        return false;
    }
    public boolean addVar(Var var){
        if(! isVarExistNowLayer(var.name)){
            table.add(var);
//            System.out.println("addVar "+var);
            return true;
        }
        return false;
    }

    public boolean isVarExistNowLayer(String name, boolean isMatchLast){
        for (Var var: table){
            if (!isMatchLast && var == table.lastElement())
                break;
            if (var.name.equals(name))
                return true;
        }
        return false;
    }

    public boolean isVarExistNowLayer(String name){
        return isVarExistNowLayer(name, true);
    }

    public boolean isVarExist(String name, boolean isMatchLast){
        if (isVarExistNowLayer(name, isMatchLast))
            return true;
        else{
            if (parent != null)
                return parent.isVarExist(name);
            else
                return false;
        }
    }
    public boolean isVarExist(String name){
        return isVarExist(name, true);
    }
    public Var getVar(String name, boolean isMatchLast){
        for (Var var: table){
            if (!isMatchLast && var == table.lastElement())
                break;
            if (var.name.equals(name))
                return var;
        }
        return parent.getVar(name);
    }
    public Var getVar(String name){
        for (Var var: table){
            if (var.name.equals(name))
                return var;
        }
        return parent.getVar(name);
    }

    public void showDetail(){
        System.out.println();
        System.out.println("Layer "+layer);
        for (Var var:table){
            System.out.println(var);
        }

    }

    public static Var getTmpVar(){
        String name = "@tmp_"+tmpVarNum;
        tmpVarNum += 1;
        Var var = new Var(false, name, INTTK, 0, -1);
        var.isTmp = true;
        return var;
    }
}
