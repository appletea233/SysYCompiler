package middleCode;

import middleCode.unit.StrDefCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Vector;

public class MiddleCodeList {
    public static MiddleCode currentCode = null;
    Vector<MiddleCode> list = new Vector<>();

    public void addCode(MiddleCode code){
        list.add(code);
        currentCode = code;
//        System.out.println(code);
    }

    public void parse(String outfile) throws IOException {
        Path path = Paths.get(outfile);
        String content = "";
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));

        for (MiddleCode code: list){
            content = code.toString() + "\n";
            Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }
    }

    public void addCodeHead(MiddleCode middleCode) {
        list.add(0, middleCode);
    }

    public void genMips(){
        for (MiddleCode middleCode: list){
            middleCode.constructMemoryStageOne();
        }
        for (MiddleCode middleCode: list){
            middleCode.constructMemoryStageTwo();
        }
        for (MiddleCode middleCode: list){
            middleCode.genMipsCode();
        }
    }
}
