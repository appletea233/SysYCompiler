package syntatic;

import base.BaseUnit;
import lexical.SymbolList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TreeList extends SymbolList {
    SymbolList symbolList;
    public boolean isTreePush = true;

    public void setTreePush(boolean isTreePush){
        this.isTreePush = isTreePush;
    }

    void addUnit(BaseUnit baseUnit) {
        String content;
        if (isTreePush){
            this.list.add(baseUnit);
        }
        Path path = Paths.get("log.txt");
        if (! baseUnit.content.equals(""))
            content = baseUnit.name + " " + baseUnit.content + " " + baseUnit.line + '\n';
        else
            content = "<" + baseUnit.name + "> " + baseUnit.line + '\n';
        try {
            Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }catch (Exception e){

        }

    }
}
