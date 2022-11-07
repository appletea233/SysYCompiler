package base;

import java.util.Objects;
import java.util.Vector;

import static base.Type.VOIDTK;

public class Func extends Var{
    public String returnType;
    public int paramCount = 0;
    public Boolean isDeclaring = true;
    public Vector<Var> inVarList = new Vector<Var>();


    // mem
    public int paramMem = 0;
    public int tmpMem = 0;
    public int localMem = 0;
    public int maxTmpMem = 0;
    public int totalMem = 0;

    public Func(String name, String returnType, int line){
        this.name = name;
        this.returnType = returnType;
        this.line = line;
        if (Objects.equals(returnType, "void"))
            dim = -1;
        else
            dim = 0;
    }

    public void addInVarList(String name, String type, int dim, int line){
        inVarList.add(new Var(false, type, name, dim, line));
    }
    public void addInVarList(Var var){
        inVarList.add(var);
    }

    public void closeDeclare(){
        isDeclaring = false;
        paramCount = inVarList.size();
    }

    public boolean isInVarList(String name){
        for (Var var: inVarList){
            if (Objects.equals(var.name, name)){
                return true;
            }
        }
        return false;
    }

    public String getMemInfo(){
        return String.format("totalMem: %d localMem: %d tmpMem: %d paraMem: %d", totalMem, localMem, maxTmpMem, paramMem);
    }

    @Override
    public String toString() {
        String content = "";
        for(Var var:inVarList){
            content += var + "";
        }
        return "Func{" +
                "name" + name +
                "returnType='" + returnType + '\'' +
                ", paramCount=" + paramCount +
                ", isDeclaring=" + isDeclaring +
                ", inVarList=" + content+
                '}';
    }
}
