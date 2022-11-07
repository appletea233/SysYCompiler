package base;


import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Type {
    public static final String MAINTK = "MAINTK";
    public static final String IDENFR = "IDENFR";
    public static final String INTCON = "INTCON";
    public static final String STRCON = "STRCON";
    public static final String CONSTTK = "CONSTTK";
    public static final String INTTK = "INTTK";
    public static final String BREAKTK = "BREAKTK";
    public static final String CONTINUETK = "CONTINUETK";
    public static final String IFTK = "IFTK";
    public static final String ELSETK = "ELSETK";
    public static final String NOT = "NOT";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String WHILETK = "WHILETK";
    public static final String GETINTTK = "GETINTTK";
    public static final String PRINTFTK = "PRINTFTK";
    public static final String RETURNTK = "RETURNTK";
    public static final String PLUS = "PLUS";
    public static final String MINU = "MINU";
    public static final String VOIDTK = "VOIDTK";
    public static final String MULT = "MULT";
    public static final String DIV = "DIV";
    public static final String MOD = "MOD";
    public static final String LSS = "LSS";
    public static final String LEQ = "LEQ";
    public static final String GRE = "GRE";
    public static final String GEQ = "GEQ";
    public static final String EQL = "EQL";
    public static final String NEQ = "NEQ";
    public static final String ASSIGN = "ASSIGN";
    public static final String SEMICN = "SEMICN";
    public static final String COMMA = "COMMA";
    public static final String LPARENT = "LPARENT";  // (
    public static final String RPARENT = "RPARENT";   // )
    public static final String LBRACK = "LBRACK" ;    // [
    public static final String RBRACK = "RBRACK";     // ]
    public static final String LBRACE = "LBRACE";     // {
    public static final String RBRACE = "RBRACE";     // }

    public static final String ADDEXP = "AddExp";
    public static final String BLOCK = "Block";
    public static final String BLOCKITEM = "BlockItem";
    public static final String BTYPE = "BType";
    public static final String COMPUNIT = "CompUnit";
    public static final String COND = "Cond";
    public static final String CONSTDECL = "ConstDecl";
    public static final String CONSTDEF = "ConstDef";
    public static final String CONSTEXP = "ConstExp";
    public static final String CONSTINITVAL = "ConstInitVal";
    public static final String DECL = "Decl";
    public static final String EQEXP = "EqExp";
    public static final String EXP = "Exp";
    public static final String FUNCDEF = "FuncDef";
    public static final String FUNCFPARAM = "FuncFParam";
    public static final String FUNCFPARAMS = "FuncFParams";
    public static final String FUNCRPARAMS = "FuncRParams";
    public static final String FUNCTYPE = "FuncType";
    public static final String INITVAL = "InitVal";
    public static final String LANDEXP = "LAndExp";
    public static final String LOREXP = "LOrExp";
    public static final String LVAL = "LVal";
    public static final String MAINFUNCDEF = "MainFuncDef";
    public static final String MULEXP = "MulExp";
    public static final String NUMBER = "Number";
    public static final String PRIMARYEXP = "PrimaryExp";
    public static final String RELEXP = "RelExp";
    public static final String STMT = "Stmt";
    public static final String UNARYEXP = "UnaryExp";
    public static final String UNARYOP = "UnaryOp";
    public static final String VARDECL = "VarDecl";
    public static final String VARDEF = "VarDef";


    static public Map<String, String> operatorMap = Stream.of(new Object[][] {
            {"NEQ", "!="},
            {"NOT", "!"},
            {"AND", "&&"},
            {"OR", "||"},
            {"LEQ", "<="},
            {"LSS", "<"},
            {"GEQ", ">="},
            {"GRE", ">"},
            {"EQL", "=="},
            {"ASSIGN", "="},
            {"DIV", "/"},
            {"PLUS", "+"},
            {"MINU", "-"},
            {"MULT", "*"},
            {"MOD", "%"},
            {"COMMA", ","},
            {"SEMICN", ";"},
            {"LPARENT", "("},
            {"RPARENT", ")"},
            {"LBRACK", "["},
            {"RBRACK", "]"},
            {"LBRACE", "{"},
            {"RBRACE", "}"},
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));

    static public Map<String, String> tkMap = Stream.of(new Object[][] {
            {"IFTK", "if"},
            {"INTTK", "int"},
            {"CONSTTK", "const"},
            {"CONTINUETK", "continue"},
            {"MAINTK", "main"},
            {"ELSETK", "else"},
            {"BREAKTK", "break"},
            {"WHILETK", "while"},
            {"GETINTTK", "getint"},
            {"PRINTFTK", "printf"},
            {"RETURNTK", "return"},
            {"VOIDTK", "void"},
            {"MINU", "-"},
            {"MULT", "*"},
            {"MOD", "%"},
            {"SEMICN", ";"},
            {"LPARENT", "("},
            {"RPARENT", ")"},
            {"LBRACK", "["},
            {"RBRACK", "]"},
            {"LBRACE", "{"},
            {"RBRACE", "}"},
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));
    static public Vector<String> synUnits = new Vector<String>();
    static {
        synUnits.add("Block");
        synUnits.add("BlockItem");
        synUnits.add("CompUnit");
        synUnits.add("Decl");
        synUnits.add("ConstDecl");
        synUnits.add("Stmt");
        synUnits.add("VarDecl");
        synUnits.add("BType");
        synUnits.add("AddExp");
        synUnits.add("ConstExp");
        synUnits.add("Exp");
        synUnits.add("MulExp");
        synUnits.add("UnaryExp");
        synUnits.add("PrimaryExp");
        synUnits.add("LVal");
        synUnits.add("UnaryOp");
        synUnits.add("Number");
        synUnits.add("RelExp");
        synUnits.add("EqExp");
        synUnits.add("LAndExp");
        synUnits.add("LOrExp");
        synUnits.add("Cond");
        synUnits.add("InitVal");
        synUnits.add("ConstInitVal");
        synUnits.add("FuncRParams");
        synUnits.add("FuncDef");
        synUnits.add("FuncType");
        synUnits.add("FuncFParams");
        synUnits.add("FuncFParam");
        synUnits.add("FuncRParams");
        synUnits.add("MainFuncDef");
        synUnits.add("VarDef");
        synUnits.add("ConstDef");
    };

    static public  boolean isSpace(char buf){
        return buf == ' ';
    }
    static public  boolean isNewLine(char buf){
        return buf == '\n' || buf == '\r';
    }
    static public  boolean isTab(char buf){
        return buf == '\t';
    }
    static public  boolean isLetter(char buf){
        return (buf >= 'a' && buf <= 'z') || (buf >= 'A' && buf <= 'Z');
    }
    static public  boolean isDigital(char buf){
        return buf>='0' && buf <='9';
    }
    static public  boolean isNot(char buf){
        return buf == '!';
    }
    static public  boolean isAnd(char buf){
        return buf == '&';
    }
    static public  boolean isOr(char buf){
        return buf == '|';
    }
    static public  boolean isPlus(char buf){
        return buf == '+';
    }
    static public  boolean isMinu(char buf){
        return buf == '-';
    }
    static public  boolean isMult(char buf){
        return buf == '*';
    }
    static public  boolean isDiv(char buf){
        return buf == '/';
    }
    static public  boolean isMod(char buf){
        return buf == '%';
    }
    static public  boolean isLss(char buf){
        return buf == '<';
    }
    static public  boolean isEq(char buf){
        return buf == '=';
    }
    static public  boolean isGre(char buf){
        return buf == '>';
    }
    static public  boolean isSem(char buf){
        return buf == ';';
    }
    static public  boolean isCom(char buf){
        return buf == ',';
    }
    static public  boolean isLpa(char buf){
        return buf == '(';
    }
    static public  boolean isRpa(char buf){
        return buf == ')';
    }
    static public  boolean isLbrack(char buf){
        return buf == '[';
    }
    static public  boolean isRbrack(char buf){
        return buf == ']';
    }
    static public  boolean isLbrace(char buf){
        return buf == '{';
    }
    static public  boolean isRbrace(char buf){
        return buf == '}';
    }
    static public  boolean isQuote(char buf){
        return buf == '"';
    }

    static public  boolean isIdentSym(char buf){
        return isDigital(buf) || isLetter(buf) || buf=='_';
    }

    static public  boolean isOperator(char buf){
        return isNot(buf)||isAnd(buf)||isOr(buf)||isPlus(buf)||isMinu(buf)||isMult(buf)||isDiv(buf)||isMod(buf)||isLss(buf)||isEq(buf)||isGre(buf)
           ||isSem(buf)||isCom(buf)||isLpa(buf)||isRpa(buf)||isLbrack(buf)||isRbrack(buf)||isLbrace(buf)||isRbrace(buf);
    }

    static public int is_decl(Vector<String > token){
        // 1. const or int
        // 2. ident
        // 3. ,
        if (Objects.equals(token.get(0), CONSTTK)){
            return 1;
        }
        else if (Objects.equals(token.get(0), INTTK)){
            if (Objects.equals(token.get(1), MAINTK)){
                return 0;
            }
            else if (Objects.equals(token.get(1), IDENFR)){
                if (Objects.equals(token.get(2), LPARENT)){
                    return 0;
                }
                else{
                    return 1;
                }
            }
            else{
                return -1;
            }
        }
        else if (Objects.equals(token.get(0), VOIDTK)){
            return 0;
        }
        else{
            return -1;
        }
    }

    static public int is_func_def(Vector<String> token){
        // 1. int or void
        // 2. ident
        // 3. (
        if (Objects.equals(token.get(0), CONSTTK)){
            return -1;
        }
        else if (Objects.equals(token.get(0), INTTK)){
            if (Objects.equals(token.get(1), MAINTK)){
                return 0;
            }
            else if (Objects.equals(token.get(1), IDENFR)){
                if (Objects.equals(token.get(2), LPARENT)){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            else{
                return -1;
            }
        }
        else if (Objects.equals(token.get(0), VOIDTK)){
            return 1;
        }
        else{
            return -1;
        }
    }



    public static boolean is_const_exp(String sym){
        // TODO: check const and normal
        return Objects.equals(sym, LPARENT) || Objects.equals(sym, IDENFR) || Objects.equals(sym, INTCON) || Objects.equals(sym, PLUS) || Objects.equals(sym, MINU);
    }

    public static boolean is_exp(String sym){
        // TODO: todo
        return Objects.equals(sym, LPARENT) || Objects.equals(sym, IDENFR) || Objects.equals(sym, INTCON) || Objects.equals(sym, PLUS) || Objects.equals(sym, MINU) || Objects.equals(sym, NOT);
    }

}
