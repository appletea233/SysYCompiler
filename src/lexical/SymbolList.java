package lexical;

import base.BaseUnit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Vector;

public class SymbolList {
    public Vector<BaseUnit> list = new Vector<BaseUnit>();
    public Vector<String> token = new Vector<String>();
    public int idx = 0;

    public void addSymbol(String name, String content, int line){
        list.add(new BaseUnit(name, content, line));
        // System.out.println(name);
    }

    public void addSymbol(BaseUnit baseUnit){
        list.add(baseUnit);
    }

    public BaseUnit getSymNow(){
        return list.get(idx);
    }

    public int getToken(int num){
        token.clear();
        if (num+idx > list.size()){
            for (int i=idx; i < list.size();i++){
                token.add(list.get(i).name);
            }
            return list.size() - idx - 1;
        }
        else{
            for (int i=0; i < num;i++){
                token.add(list.get(i + idx).name);
            }
            return num;
        }
    }


    public void parse(String outfile) throws IOException {
        Path path = Paths.get(outfile);
        String content = "";
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));

        for (BaseUnit symbol: list) {
            if (! symbol.content.equals(""))
                content = symbol.name + " " + symbol.content + '\n';
            else
                content = "<" + symbol.name + ">" + '\n';
            Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }
    }

    public void moveBack(){
        idx ++;
    }
}
