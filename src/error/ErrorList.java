package error;

import base.BaseUnit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class ErrorList {
    Vector<Error> list = new Vector<Error>();
    public boolean addError(int line, char id){
        if (list.isEmpty()) {
            list.add(new Error(line, id));
            return true;
        }
        else{
            if (list.lastElement().line == line){
                return false;
            }
            else{
                list.add(new Error(line, id));
                return true;
            }
        }
//        list.add(new Error(line, id));
//        return true;
    }

    public void parse(String outfile) throws IOException {
        Comparator<Error> errorComparator = new Comparator<Error>() {
            @Override
            public int compare(Error o1, Error o2) {
                return o1.line - o2.line;
            }
        };
        list.sort(errorComparator);


        Path path = Paths.get(outfile);
        String content = "";
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));

        for (Error error: list) {
            content = error.line + " " + (char)error.type + '\n';
            Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }
    }
}
