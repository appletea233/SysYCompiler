package mips;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Vector;

public class MipsCodeManger {
    Vector<String> dataSegment = new Vector<>();
    Vector<String> textSegment= new Vector<>();

    public int initStamp = 0;

    public void addData(String code){
        dataSegment.add(code);
    }

    public void addData(int index, String code){
        dataSegment.add(index, code);
    }

    public void addText(String code){
        textSegment.add(code);
    }

    public void addSpace(String name, int range){
        addData(name+": .space "+range);
    }

    public void addWord(String name, int value){
        addData(name+": .word "+value);
    }

    public void addStrCon(String name, String content){
        addData(0, name+": .asciiz \""+content+"\"");
    }

    public void addExp(String returnReg, String reg1 , String op,String reg2){
        if (Objects.equals(op, "+")){
            addText(String.format("%s $%s, $%s, $%s", "add", returnReg, reg1, reg2));
        }
        else if (Objects.equals(op, "-")){
            addText(String.format("%s $%s, $%s, $%s", "sub", returnReg, reg1, reg2));
        }
        else if (Objects.equals(op, "*")){
            addText(String.format("%s $%s, $%s", "mult", reg1, reg2));
            addText(String.format("%s $%s", "mflo", returnReg));
        }
        else if (Objects.equals(op, "/")){
            addText(String.format("%s $%s, $%s", "div", reg1, reg2));
            addText(String.format("%s $%s", "mflo", returnReg));
        }
        else if (Objects.equals(op, "%")){
            addText(String.format("%s $%s, $%s", "div", reg1, reg2));
            addText(String.format("%s $%s", "mfhi", returnReg));
        }
    }

    public void addAddi(String returnReg, String reg1, int value){
        addText(String.format("%s $%s, $%s, %d", "addi", returnReg, reg1, value));
    }

    public void addLw(String reg,String label, int addr){
        addText(String.format("lw $%s, %d($%s)", reg, addr, label));
    }

    public void addLwLabel(String reg,String label, int addr){
        addText(String.format("lw $%s, %s+%d", reg, label, addr));
    }

    public void addSw(String reg,String label, int addr){
        addText(String.format("sw $%s, %d($%s)", reg, addr, label));
    }

    public void addSwLabel(String reg,String label, int addr){
        addText(String.format("sw $%s, %s+%d", reg, label, addr));
    }

    public void addLabel(String label){
        addText(label+":");
    }

    public void addJal(String label){
        addText("jal "+label);
    }

    public void addJr(){
        addText("jr $ra");
    }

    public void addJ(String label){
        addText("j "+label);
    }

    public void addExit(){
        addLi("v0", 10);
        addSysCall();
    }

    public void addSysCall(){
        addText("syscall");
    }

    public void addLi(String reg, int value) {
        addText(String.format("li $%s, %d", reg, value));
    }

    public void addLa(String reg, String label) {
        addText(String.format("la $%s, %s", reg, label));
    }

    public void parse(String outfile) throws IOException {
        Path path = Paths.get(outfile);
        String content = "";
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));

        content = ".data\n";
        Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        for (String code: dataSegment) {
            content = code + "\n";
            Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }

        content = ".text\n";
        content += "li $fp, 0x10040000\n";
        content += String.format("addi $fp, $fp, %s\n\n", initStamp);
        content += "j func_main\n";
        content += "nop\n\n\n";

        Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

        for (String code: textSegment){
            content = code + "\n";
            Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }
    }
}
