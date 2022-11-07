package base;

import java.util.Objects;
import java.util.Vector;

public class Var {
    // base
    public String name;
    public String type;
    public int dim;
    public int line;
    public Vector<Integer> arrayDim = new Vector<>();

    // const var
    public boolean isConst = false;
    public int value;
    // tmp var
    public boolean isTmp = false;
    // mem
    public boolean isGlobal = false;
    public int length = 0;
    public int addr = 0;    // 地址偏移量
    public Var origin;

    public Vector<Integer> usedLine = new Vector<>();

    @Override
    public String toString() {
        return "Var{" +
                "isConst=" + isConst +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", dim=" + dim +
                ", line=" + line +
                ", value=" + value +
                '}';
    }

    public Var(boolean isConst, String name, String type, int dim, int line) {
        this.isConst = isConst;
        this.name = name;
        this.type = type;
        this.dim = dim;
        this.line = line;
//        System.out.println(this.toString());

        // mem
        if (dim == 0 && Objects.equals(type, "int")){
            this.length = 4;
        }
    }

    public Var(){}

    public Var(int value) {
        this.name = "ConstInt";
        this.value = value;
        this.dim = 0;
        this.line = -1;
        this.isConst = true;
    }

    public Var(String name) {
        this.name = name;
        this.value = 0;
        this.dim = 0;
        this.line = -1;
        this.isConst = false;
    }

    public void addUsedLine(int line){
        this.usedLine.add(line);
    }
}
