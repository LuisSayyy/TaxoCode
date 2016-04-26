
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Luis
 */
public class Parser {
    public static HashMap<String, Token> idTable = new HashMap();
    public static ArrayList<Token> tokens = new ArrayList<Token>(); 
    public static Token curr_token;
    public static Stack tokenStack = new Stack();
    public static Stack gotoStack = new Stack();
    public static int i = 0;
    public static boolean flag = true;
    public static boolean err_flag = false;
    public static HashMap<String, Grammar> productions = new HashMap();
    public static kNode node = new kNode();
    public static kNode parent = new kNode();
    public static kTree tree = new kTree();
    //public static int ro, co;
    
    public static Stack tokStack = new Stack();
    public static Stack gotStack = new Stack();
    
    public static void addProductions(){
        productions.put("0", new Grammar(0, Variables.start, "block"));
        productions.put("1", new Grammar(1, Variables.block, "[KINGDOM] [LCURLY] stmt_list [RCURLY] [ENDKINGDOM]"));
        productions.put("2", new Grammar(2, Variables.stmt_list, "stmt"));
        productions.put("3", new Grammar(3, Variables.stmt_list, "stmt stmt_list"));
        productions.put("4", new Grammar(4, Variables.stmt, "decl"));
        productions.put("5", new Grammar(5, Variables.stmt, "assign"));
        productions.put("6", new Grammar(6, Variables.stmt, "cond"));
        productions.put("7", new Grammar(7, Variables.stmt, "loop"));
        productions.put("8", new Grammar(8, Variables.stmt, "read"));
        productions.put("9", new Grammar(9, Variables.stmt, "write"));
        productions.put("10", new Grammar(10, Variables.decl, "type vars [EOS]"));
        productions.put("11", new Grammar(11, Variables.type, "[FISH]"));
        productions.put("12", new Grammar(12, Variables.type, "[REPTILE]"));
        productions.put("13", new Grammar(13, Variables.type, "[INSECT]"));
        productions.put("14", new Grammar(14, Variables.type, "[KILLER]"));
        productions.put("15", new Grammar(15, Variables.vars, "[ID] [COMMA] vars"));
        productions.put("16", new Grammar(16, Variables.vars, "[ID]"));
        productions.put("17", new Grammar(17, Variables.assign, "[ID] [IS] expr [EOS]"));
        productions.put("18", new Grammar(18, Variables.assign, "[CONSTANT] [ID] [IS] expr [EOS]"));
        productions.put("19", new Grammar(19, Variables.cond, "[IF] [LPAREN] exprt [RPAREN] [LCURLY] stmt_list [RCURLY] [ELSE] [LCURLY] stmt_list [RCURLY]"));
        productions.put("20", new Grammar(20, Variables.cond, "[IF] [LPAREN] exprt [RPAREN] [LCURLY] stmt_list [RCURLY]"));
        productions.put("21", new Grammar(21, Variables.loop, "for_loop"));
        productions.put("22", new Grammar(22, Variables.loop, "while_loop"));
        productions.put("23", new Grammar(23, Variables.for_loop, "[REPRODUCE] [LPAREN] assign [UNTIL] exprt [WITH] [NUMCONST] [RPAREN] [LCURLY] stmt_list [RCURLY]"));
        productions.put("24", new Grammar(24, Variables.while_loop, "[PROCREATE] [LPAREN] exprt [RPAREN] [LCURLY] stmt_list [RCURLY]"));
        productions.put("25", new Grammar(25, Variables.read, "[INHALE] [LPAREN] [ID] [RPAREN] [EOS]"));
        productions.put("26", new Grammar(26, Variables.write, "[EXHALE] [LPAREN] expr [RPAREN] [EOS]"));
        productions.put("27", new Grammar(27, Variables.expr, "expr [CONCAT] exprt"));
        productions.put("28", new Grammar(28, Variables.expr, "exprt"));
        productions.put("29", new Grammar(29, Variables.exprt, "exprt [NOR] exprt1"));
        productions.put("30", new Grammar(30, Variables.exprt, "exprt1"));
        productions.put("31", new Grammar(31, Variables.exprt1, "exprt1 [NAND] exprt2"));
        productions.put("32", new Grammar(32, Variables.exprt1, "exprt2"));
        productions.put("33", new Grammar(33, Variables.exprt2, "exprt2 [OR] exprt3"));
        productions.put("34", new Grammar(34, Variables.exprt2, "exprt3"));
        productions.put("35", new Grammar(35, Variables.exprt3, "exprt3 [AND] exprt4"));
        productions.put("36", new Grammar(36, Variables.exprt3, "exprt4"));
        productions.put("37", new Grammar(37, Variables.exprt4, "[NOT] exprt4"));
        productions.put("38", new Grammar(38, Variables.exprt4, "exprt5"));
        productions.put("39", new Grammar(39, Variables.exprt5, "exprt5 [EQUALS] exprn"));
        productions.put("40", new Grammar(40, Variables.exprt5, "exprt5 [NEQUALS] exprn"));
        productions.put("41", new Grammar(41, Variables.exprt5, "exprt5 [GTHAN] exprn"));
        productions.put("42", new Grammar(42, Variables.exprt5, "exprt5 [LTHAN] exprn"));
        productions.put("43", new Grammar(43, Variables.exprt5, "exprt5 [lETHAN] exprn"));
        productions.put("44", new Grammar(44, Variables.exprt5, "exprt5 [GETHAN] exprn"));
        productions.put("45", new Grammar(45, Variables.exprt5, "exprn"));
        productions.put("46", new Grammar(46, Variables.exprn, "exprn [PLUS] exprnterm"));
        productions.put("47", new Grammar(47, Variables.exprn, "exprn [MINUS] exprnterm"));
        productions.put("48", new Grammar(48, Variables.exprn, "exprnterm"));
        productions.put("49", new Grammar(49, Variables.exprnterm, "exprnterm [MULT] exprnfactor"));
        productions.put("50", new Grammar(50, Variables.exprnterm, "exprnfactor"));
        productions.put("51", new Grammar(51, Variables.exprnfactor, "[LPAREN] expr [RPAREN]"));
        productions.put("52", new Grammar(52, Variables.exprnfactor, "unit"));
        productions.put("53", new Grammar(53, Variables.unit, "[ID]"));
        productions.put("54", new Grammar(54, Variables.unit, "[NUMCONST]"));
        productions.put("55", new Grammar(55, Variables.unit, "[STRLIT]"));
        productions.put("56", new Grammar(56, Variables.unit, "[CHARLIT]"));
        productions.put("57", new Grammar(57, Variables.unit, "[PREDATOR]"));
        productions.put("58", new Grammar(58, Variables.unit, "[PREY]"));
    }
    
    public static void main(String[] args) throws IOException, FileNotFoundException {
        Token tok = new Token(null, "");
        LexAnalyzer lex = new LexAnalyzer("E:\\Paolo\\UST\\4th year\\2nd sem\\CS 105\\Sample Programs\\semantic.txt");
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
                case "Multiline Comment":
                    break;
                default:
                    System.out.println("ERROR -- Invalid Token (Line: " + lex.row + " Col: " + lex.col + ")");
                    break;
            }
            tokens.add(tok);
        } while(lex.i != lex.totalCharacters);
        System.out.println("----Lexical Analysis Complete----\n");
        
        
        System.out.println("---------Syntax Analyzer---------");
        tokens.add(new Token(TokenNames.$, "$"));
        addProductions();
        ParsingTable.loadTable();
        tokenStack.push("$");
        gotoStack.push("0");
        while(i != tokens.size() && flag && !err_flag){
            int curr_state = Integer.parseInt(gotoStack.peek().toString());
            ParsingTokens row = ParsingTable.parsingTokTable.get(curr_state);
            curr_token = getNextToken();
            node = new kNode(curr_token.getTokenKind(), curr_token);
            
            System.out.println("Top of Stack: " + curr_state);
            System.out.println("Lookahead: " + curr_token.getTokenKind());
            
                switch(curr_token.getTokenKind()){
                    case "[KINGDOM]": action(row.entry[0]); break;
                    case "[ENDKINGDOM]": action(row.entry[1]); break;
                    case "[FISH]": action(row.entry[2]); break;
                    case "[INSECT]": action(row.entry[3]); break;
                    case "[REPTILE]": action(row.entry[4]); break;
                    case "[KILLER]": action(row.entry[5]); break;
                    case "[PREDATOR]": action(row.entry[6]); break;
                    case "[PREY]": action(row.entry[7]); break;
                    case "[IF]": action(row.entry[8]); break;
                    case "[ELSE]": action(row.entry[9]); break;
                    case "[REPRODUCE]": action(row.entry[10]); break;
                    case "[PROCREATE]": action(row.entry[11]); break;
                    case "[INHALE]": action(row.entry[12]); break;
                    case "[EXHALE]": action(row.entry[13]); break;
                    case "[IS]": action(row.entry[14]); break;
                    case "[EQUALS]": action(row.entry[15]); break;
                    case "[NEQUALS]":action(row.entry[16]); break;
                    case "[GTHAN]":action(row.entry[17]); break;
                    case "[LTHAN]":action(row.entry[18]); break;
                    case "[GETHAN]":action(row.entry[19]); break;
                    case "[LETHAN]":action(row.entry[20]); break;
                    case "[AND]":action(row.entry[21]); break;
                    case "[OR]": action(row.entry[22]); break;
                    case "[NAND]":action(row.entry[23]); break;
                    case "[NOR]":action(row.entry[24]); break;
                    case "[NOT]":action(row.entry[25]); break;
                    case "[COMMA]":action(row.entry[26]); break;
                    case "[EOS]":action(row.entry[27]); break;
                    case "[CONCAT]":action(row.entry[28]); break;
                    case "[MULT]": action(row.entry[29]); break;
                    case "[PLUS]":action(row.entry[30]); break;
                    case "[MINUS]":action(row.entry[31]); break;
                    case "[LPAREN]": action(row.entry[32]); break;
                    case "[RPAREN]":action(row.entry[33]); break;
                    case "[LCURLY]": action(row.entry[34]); break;
                    case "[RCURLY]": action(row.entry[35]); break;
                    case "[STRLIT]": action(row.entry[36]); break;
                    case "[ID]": action(row.entry[37]); break;
                    case "[NUMCONST]": action(row.entry[38]); break;
                    case "[CHARLIT]": action(row.entry[39]); break;
                    case "[WITH]": action(row.entry[40]); break;
                    case "[UNTIL]": action(row.entry[41]); break;
                    case "[CONSTANT]": action(row.entry[42]); break;
                    case "[$]": action(row.entry[43]); break;
            }
        }
        tree.makeRoot(parent);
        if(!err_flag){
            System.out.println("Generated Parse Tree: \n" + tree.toString());
            System.out.println("-----Syntax Analysis Complete----\n");
        }
        
        System.out.println("--------Semantics Analyzer-------");
            Interpreter.main(tree);
        System.out.println("-----Semantics Analysis Complete----");
    }
    
    public static void action(String cell){ 
        System.out.println(cell); 
        ParsingGoTo row = new ParsingGoTo();
        int state;
        kNode temp;
        Object holder;
        Stack revStack = new Stack();
        String arr[] = cell.split("&");
        String first = String.valueOf(arr[0]);
        String second = "";
        if(arr.length < 2){
            System.out.println("\n ----PARSING ERROR: Token Table Entry does not contain a Shift or Reduce.----");
            err_flag = true;
        }
        else{
            second = String.valueOf(arr[1]);
        }

        
        switch(first){
            case "S": case "s":
                    System.out.println("Shift " + second);
                    tokenStack.push(node);
                    gotoStack.push(second);
                ++i;
                break;
            case "R": case "r":
                Grammar p = productions.get(second);
                String pro[] = p.prod.split(" ");
                switch(second){
                    case "0":
                        System.out.println("Reduce by " + p.toString() + ", Accept!");
                        parent = new kNode(p.var.toString());
                        revStack.push(tokenStack.pop());
                        holder = revStack.pop();
                        temp = (kNode) holder;
                        System.out.println("Connecting " + temp.info + " to " + parent.info + "... DONE!");
                        tree.addParent(temp, parent);
                        gotoStack.pop();    
                        tree.makeRoot(parent);
                        flag = false;
                        break;
                    case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9": case "10":
                    case "11": case "12": case "13": case "14": case "15": case "16": case "17": case "18": case "19": case "20":
                    case "21": case "22": case "23": case "24": case "25": case "26": case "27": case "28": case "29": case "30":
                    case "31": case "32": case "33": case "34": case "35": case "36": case "37": case "38": case "39": case "40":
                    case "41": case "42": case "43": case "44": case "45": case "46": case "47": case "48": case "49": case "50":
                    case "51": case "52": case "53": case "54": case "55": case "56": case "57": case "58":
                        System.out.println("Reduce by Rule#" + p.rulenum + ": "+ p.toString());
                        parent = new kNode(p.var.toString());

                        for(int i=0; i<pro.length; i++){
                            revStack.push(tokenStack.pop());                    
                        }
                        for(int i=0; i<pro.length; i++){                            
                            holder = revStack.pop();
                            temp = (kNode) holder;
                            //System.out.println(temp.info);
                            System.out.println("Connecting " + temp.info + " to " + parent.info + "... DONE!");
                            tree.addParent(temp, parent);
                            //tree.addSibling(temp, temp);
                            //System.out.println(temp.parent.info);
                            gotoStack.pop();                        
                        }
                        state = Integer.parseInt((String) gotoStack.peek());
                        System.out.println("Stack Peek: " + state);
                        row = ParsingTable.parsingGoToTable.get(state);
                        tokenStack.push(parent);
                        for(int i=0; i<24; i++){    
                            if(row.var[i] == p.var.toString()){
                                System.out.println("Push State#" + row.entry[i]);
                                gotoStack.push(row.entry[i]);
                            }
                        }
                        break;
                    default:
                        System.out.println("Parsing Reduce Error.");
                        break;
                }
                tree.makeRoot(parent);
               // System.out.println(tree.toString());
                break;
        }
        System.out.println("");
    }
    
    public static Token getNextToken(){
        return tokens.get(i);
    }
    
}
