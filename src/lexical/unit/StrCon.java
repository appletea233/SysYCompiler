package lexical.unit;

import base.Str;
import base.Var;
import lexical.Symbol;
import middleCode.MiddleCodeList;
import middleCode.unit.PrintCode;
import middleCode.unit.StrDefCode;

import java.util.Vector;

public class StrCon extends Symbol {
    public int count = 0;
    int idx = 0;
    static int strNum = 0;
    public Vector<Var> strList = new Vector<>();
    public Vector<Var> varList;
    public int varIdx = 0;



    public StrCon(String content, int line) {
        super("STRCON", content, line);
    }

    public void createTable() {
//        System.out.println(content);
        int idx = 0;
        boolean end = false;
        for (; idx < content.length(); ) {

            if (idx == 0) {
                idx++;
            }
            if (idx == content.length() - 1) {
                idx++;
                end = true;
                break;
            }
            char c = content.charAt(idx);

            if (c!='%') {
                if (c == '\\') {
                    if (content.charAt(idx + 1) == 'n') {
                        idx += 2;
                    } else {
                        break;
                    }
                } else {
                    idx += 1;
//                    System.out.print(c);
                }
            } else {
//                System.out.println(c);
                if (c == '%') {
                    count++;
                    if (content.charAt(idx + 1) == 'd')
                        idx += 2;
                    else
                        break;
                } else
                    break;
            }


        }
        if (!end) {
            errorList.addError(line, 'a');
        }
    }

    public void genStr(){
        String buf = "";
        int idx = 0;
        for (; idx < content.length(); ) {
            if (idx == 0) {
                idx++;
            }
            if (idx == content.length() - 1) {
                if (! buf.equals("")) {
                    Str str = new Str("str_" + strNum, buf);
                    strNum++;
                    middleCodeList.addCodeHead(new StrDefCode(str));
                    middleCodeList.addCode(new PrintCode(str));
                }
                break;
            }
            char c = content.charAt(idx);
//            c == 32 || c == 33 || (c >= 40 && c <= 126)
            if (c!='%') {
                if (c == '\\') {
                    if (content.charAt(idx + 1) == 'n') {
                        buf+="\\n";
                        idx += 2;
                    } else {
                        break;
                    }
                } else {
                    buf+=c;
                    idx += 1;
                }
            } else {
                if (c == '%') {
                    if (content.charAt(idx + 1) == 'd') {
                        if (! buf.equals("")) {
                            Str str = new Str("str_" + strNum, buf);
                            buf = "";
                            strNum++;
                            middleCodeList.addCodeHead(new StrDefCode(str));
                            middleCodeList.addCode(new PrintCode(str));

                        }
                        middleCodeList.addCode(new PrintCode(varList.get(varIdx)));
                        varIdx++;
                        idx += 2;
                    }
                    else
                        break;
                } else
                    break;
            }

        }
    }
}