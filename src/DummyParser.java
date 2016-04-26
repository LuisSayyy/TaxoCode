
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Luis
 */
public class DummyParser {
    public static HashMap<String, Token> idTable = new HashMap();
    
    public static void main(String[] args) throws IOException, FileNotFoundException {
        Token tok;
        LexAnalyzer lex = new LexAnalyzer("E:\\Paolo\\UST\\4th year\\2nd sem\\CS 105\\Sample Programs\\fib.txt");
        lex.getTotalChar();
        System.out.println("-----Lexical Analyzer-----");
        do{
            String token = lex.getTokenType();
            switch(token){
                case "Keyword":
                   if(lex.rwTable.get(lex.strBuffer) != null){
                        if(lex.rwTable.get(lex.strBuffer).lexeme == "THE"){
                            continue;
                        }
                        else{
                            tok = lex.rwTable.get(lex.strBuffer);
                            System.out.format(tok.toString() + "\n");
                        }
                    }
                    else System.out.println("ERROR -- Invalid Keyword: " + lex.strBuffer + " (Line: " + lex.row + " Col: " + lex.col + ")");
                    break;
                case "Single Symbol":
                case "Double Symbol":
                    tok = lex.rwTable.get(lex.strBuffer);
                    System.out.format(tok.toString() + "\n");
                    break;
                case "ID":
                    if(idTable.get(lex.strBuffer) == null){
                        idTable.put(lex.strBuffer, new Token(TokenNames.ID, lex.strBuffer));
                    }
                    tok = idTable.get(lex.strBuffer);
                    System.out.format(tok.toString() + "\n");
                    break;
                case "numConst":
                    tok = new Token(TokenNames.NUMCONST, lex.strBuffer);
                    System.out.format(tok.toString() + "\n");
                    break;
                case "charLit":
                    tok = new Token(TokenNames.CHARLIT, lex.strBuffer);
                    System.out.format(tok.toString() + "\n");
                    break;
                case "strLit":
                    tok = new Token(TokenNames.STRLIT, lex.strBuffer);
                    System.out.format(tok.toString() + "\n");
                    break;
                case "Single Comment":
                    tok = new Token(TokenNames.SINGCOM, lex.strBuffer);
                    System.out.format(tok.toString() + "\n");
                    break;
                case "Multiline Comment":
                    tok = new Token(TokenNames.MULTICOM, lex.strBuffer);
                    System.out.format(tok.toString() + "\n");
                    break;
                default:
                    System.out.println("ERROR -- Invalid Token (Line: " + lex.row + " Col: " + lex.col + ")");
                    break;
            }
        } while(lex.i != lex.totalCharacters);
        System.out.println("----Lexical Analysis Complete----");
    }
}
