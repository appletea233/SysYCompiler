package lexical;

import lexical.unit.IntCon;
import lexical.unit.StrCon;

import java.io.IOException;
import java.util.HashMap;

import static base.Type.*;

public class Lexical {
    public String bufString = "";
    public char buf = ' ';
    public int line = 1;
    public PoneData poneData;
    public SymbolList symbolList;

    public void clearBuf(){
        bufString = "";
    }

    public char getChar(boolean isMove){
        buf = poneData.getChar(isMove);
        return buf;
    }

    public void pushChar(char c){
        bufString+=c;
    }

    public void getAndPush(boolean identMode){
        char buf = getChar(true);
        if (identMode){
            if (isIdentSym(buf)){
                pushChar(buf);
            }
        }
        else{
            pushChar(buf);
        }
    }

    void getToken() {
        do {
            getAndPush(true);
        } while(isIdentSym(buf));
    }

    int getTkOrIdent(){
        boolean flag = false;
        getToken();

        for (HashMap.Entry<String,String> tk: tkMap.entrySet()){
            String tkString = tk.getValue();
            if (bufString.equals(tkString)){
                flag = true;
                symbolList.addSymbol(tk.getKey(), tk.getValue(), line);
            }
        }
        if (! flag){
            symbolList.addSymbol("IDENFR", bufString, line);
        }
        return 1;
    }

    int getOp() {
        boolean flag = false;
        String op_one_word = ""+buf;

        getChar(true);

        String op_two_word = op_one_word + buf;
        for (HashMap.Entry<String,String> op: operatorMap.entrySet()){
            String op_string = op.getValue();
            if (op_two_word.equals(op_string)){
                flag = true;
                symbolList.addSymbol(op.getKey(), op.getValue(), line);
                return 0;
            }
        }
        for (HashMap.Entry<String,String> op: operatorMap.entrySet()){
            String op_string = op.getValue();
            if (op_one_word.equals(op_string)){
                flag = true;
                symbolList.addSymbol(op.getKey(), op.getValue(), line);
                return 1;
            }
        }
        return -1;
    }

    int getDigital() {
        while (isDigital(buf)){
            getAndPush(true);
        }
        symbolList.addSymbol(new IntCon(bufString, line));
        return 1;
    }

    int getQuote() {
        getAndPush(false);
        while(!isQuote(buf)){
            getAndPush(false);
        }
        symbolList.addSymbol(new StrCon(bufString, line));
        return 0;
    }

    void getLine() {
    }

    public int getSym(int is_next) {
        clearBuf();
        // 是否向下读字符
        if (is_next == 0) {
            getChar(true);
        }
        if (isNewLine(buf)){
            line++;
        }

        while(isSpace(buf) || isNewLine(buf) || isTab(buf)){
            getChar(true);
            if (isNewLine(buf)){
                line++;
            }
        }

        getLine();
        pushChar(buf);
        if (isLetter(buf) || buf == '_'){
            return getTkOrIdent();
        }
        else if (isOperator(buf)){
            return getOp();
        }
        else if (isDigital(buf)){
            return getDigital();
        }
        else if (isQuote(buf)){
            // TODO: string 不闭合异常处理
            return getQuote();
        }
        else{
            return -1;
        }
    }

    private void getAllSym() {
        try {
            int res = 0;
            do {
                res = getSym(res);
            } while (res != -1);
        }catch (Exception e){
            System.out.println("out getAllSym");
        }

    }

    public void construct(String infile, String outfile, boolean isParse) throws IOException {
        poneData = new PoneData(infile);
        poneData.construct();

        poneData.poneData += "   ";

        String pone = "error.txt";
        poneData.parse(pone);

        symbolList = new SymbolList();
        getAllSym();

        if (isParse){
            symbolList.parse(outfile);
        }
    }


}
