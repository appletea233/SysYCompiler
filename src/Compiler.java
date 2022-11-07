import lexical.Lexical;
import middleCode.MiddleCode;
import middleCode.MiddleCodeList;
import mips.MipsCodeManger;
import syntatic.Syntatic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Compiler {
    static void init() throws IOException {
        String outfile = "log.txt";
        Path path = Paths.get(outfile);
        String content = "";
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }
    public static void main(String[] args) throws IOException {
        String in = "testfile.txt";
        String out = "output.txt";
        String error = "error.txt";
        String middle = "middlecode.txt";
        String mips = "mips.txt";
        init();

        Lexical lexical = new Lexical();
        lexical.construct(in, out, true);

        Syntatic syntatic = new Syntatic(lexical.symbolList);
        syntatic.construct(out, true);
        syntatic.createTable(error, true);
        syntatic.getValue();
        syntatic.generateMiddleCode(middle, true);
//        syntatic.showDetail();

        MiddleCodeList middleCodeList = syntatic.middleCodeList;
        middleCodeList.genMips();
        MipsCodeManger mipsCodeManger = MiddleCode.mipsCodeManger;
        mipsCodeManger.parse(mips);
    }

    public static void run(boolean isLexical, boolean isSyn, boolean isMiddle, boolean isMips,
                           String infile, String outfile, String testFile,
                           String mipsFile, String middleFile, String lexicalFile, String errorFile) throws IOException {
        Lexical lexical = new Lexical();
        if (isLexical) {
            lexical.construct(testFile, lexicalFile, true);
        }
        Syntatic syntatic = new Syntatic(lexical.symbolList);
        if (isSyn) {
            syntatic.construct(lexicalFile, true);
            syntatic.createTable(errorFile, true);
            syntatic.getValue();
            syntatic.generateMiddleCode(middleFile, true);
        }
        MiddleCodeList middleCodeList = syntatic.middleCodeList;
        if (isMiddle) {
            middleCodeList.genMips();
        }
        if (isMips) {
            MipsCodeManger mipsCodeManger = MiddleCode.mipsCodeManger;
            mipsCodeManger.parse(mipsFile);
        }
    }
}
