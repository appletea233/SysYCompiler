package error;

public class Error implements Comparable {
    public int line;
    public int type;

    public Error(int line, int type) {
        this.line = line;
        this.type = type;
    }

    @Override
    public int compareTo(Object o) {
        return line = ((Error)o).line;
    }
}
