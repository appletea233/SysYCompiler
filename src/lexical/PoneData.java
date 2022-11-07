package lexical;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PoneData {
    public String poneData = "";
    public String rawData;
    char buf;
    int idxPone = 0;
    int idxRaw = 0;
    int lineNum;
    File file;

    public PoneData(String infile){
        Path path = Paths.get(infile);
        byte[] data = null;
        file = new File(infile);
        try{
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rawData = new String(data);
    }

    public boolean getRawChar(){
        boolean res;
        if (idxRaw >= rawData.length())
            res = false;
        else
            res =  true;
        if (res) {
            buf = rawData.charAt(idxRaw);
            idxRaw++;
        }
        return res;
    }

    public void addPoneData(char c){
        poneData += c;
    }

    public void clearNotes(boolean isQuote){
        if (! isQuote) {
            getRawChar();
            if (buf == '/') {
                while (buf != '\n') {
                    getRawChar();
                }
                addPoneData(buf);

            }
            else if (buf == '*') {
                // TODO: 此处逻辑需要缕清
                getRawChar();
                while (true) {
                    while (buf != '*') {
                        // process \n to make thure lin num correct
                        if (buf == '\n')
                            addPoneData(buf);
                        getRawChar();
                    }
                    getRawChar();
                    if (buf == '\n')
                        addPoneData(buf);
                    if (buf == '/') {
                        return;
                    }
                }
            }
            else {
                addPoneData('/');
                addPoneData(buf);
            }
        }
        else{
            addPoneData(buf);
        }
    }

    public void construct(){
        boolean res = true;
        boolean isQuote = false;
        while(true){
            res = getRawChar();
            if (!res){
                return;
            }
            if (buf == '\"'){
                isQuote = ! isQuote;
                addPoneData(buf);
            }
            else if (buf == '/'){
                clearNotes(isQuote);
            }
            else if (buf == '\r'){

            }
            else{
                addPoneData(buf);
            }
        }
    }

    public char getChar(boolean isMove){
        char c =  poneData.charAt(idxPone);
        if (isMove)
            idxPone++;
        return c;
    }

    void parse(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Files.write(path, poneData.getBytes(StandardCharsets.UTF_8));
    }
}
