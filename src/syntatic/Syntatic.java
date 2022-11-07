package syntatic;

import base.BaseUnit;
import error.ErrorList;
import lexical.SymbolList;
import middleCode.MiddleCode;
import middleCode.MiddleCodeList;
import syntatic.unit.CompUnit;

import java.io.IOException;

public class Syntatic {
    SymbolList symbolList;
    TreeList treeList;
    ErrorList errorList;
    public MiddleCodeList middleCodeList;
    public CompUnit unit;

    static MiddleCode currentMiddleCode = null;

    public Syntatic(SymbolList symbolList){
        this.symbolList = symbolList;
        treeList = new TreeList();
        errorList = new ErrorList();
        middleCodeList = new MiddleCodeList();
    }

    public void construct(String outfile, boolean isParse) throws IOException {
        BaseUnit.treeList = treeList;
        BaseUnit.symbolList = symbolList;
        BaseUnit.errorList = errorList;
        BaseUnit.middleCodeList = middleCodeList;

        unit = new CompUnit(null);
        unit.construct();

        if (isParse){
            treeList.parse(outfile);
        }
    }

    public void createTable(String file, boolean isParse) throws IOException {
        unit.createTable();
        if (isParse){
            errorList.parse(file);
        }
    }

    public void generateMiddleCode(String file, boolean isParse) throws IOException {
        unit.genMiddleCode();
        if (isParse){
            middleCodeList.parse(file);
        }
    }



    public void getValue() {
        unit.getValue();
    }

    public void showDetail() throws IOException {
        unit.showDetail();
    }
}
