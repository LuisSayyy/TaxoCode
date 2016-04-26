
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

/**
 *
 * @author Luis
 */
public class LexAnalyzer {
    private final String capLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String smallLetters = "abcdefghijklmnopqrstuvwxyz";
    private final String letters = capLetters + smallLetters;
    private final String digits = "0123456789";
    private final String space = " ";
    private final String specialSymbols = "<>|/~$%#^&*+-_()[]{}.,;:!?\'";
    private final String singlesym = "=,;~*+-(){}";
    private final String arith = "+-*()";
    private final String squote = "\'";
    private final String dquote = "\"";
    private static RandomAccessFile reader;
    private char prev = ' ';
    private final String input;

    public static HashMap<String, Token> rwTable = new HashMap();
    public int i = 0;
    public int totalCharacters = 0;
    public int row = 1;
    public int col = -1;
    public String strBuffer = "";
    public String error = "";
    
     public static void addReservedWords(){
        rwTable.put("KINGDOM", new Token(TokenNames.KINGDOM, "KINGDOM"));
        rwTable.put("ENDKINGDOM", new Token(TokenNames.ENDKINGDOM, "ENDKINGDOM"));
        rwTable.put("FISH", new Token(TokenNames.FISH, "FISH"));
        rwTable.put("INSECT", new Token(TokenNames.INSECT, "INSECT"));
        rwTable.put("REPTILE", new Token(TokenNames.REPTILE, "REPTILE"));
        rwTable.put("KILLER", new Token(TokenNames.KILLER, "KILLER"));
        rwTable.put("PREDATOR", new Token(TokenNames.PREDATOR, "PREDATOR"));
        rwTable.put("PREY", new Token(TokenNames.PREY, "PREY"));
        rwTable.put("IF", new Token(TokenNames.IF, "IF"));
        rwTable.put("ELSE", new Token(TokenNames.ELSE, "ELSE"));
        rwTable.put("REPRODUCE", new Token(TokenNames.REPRODUCE, "REPRODUCE"));
        rwTable.put("PROCREATE", new Token(TokenNames.PROCREATE, "PROCREATE"));
        rwTable.put("INHALE", new Token(TokenNames.INHALE, "INHALE"));
        rwTable.put("EXHALE", new Token(TokenNames.EXHALE, "EXHALE"));
        rwTable.put("IS", new Token(TokenNames.IS, "IS"));
        rwTable.put("=", new Token(TokenNames.EQUALS, "="));
        rwTable.put("!=", new Token(TokenNames.NEQUALS, "!="));
        rwTable.put(">", new Token(TokenNames.GTHAN, ">"));
        rwTable.put("<", new Token(TokenNames.LTHAN, "<"));
        rwTable.put("<=", new Token(TokenNames.LETHAN, "<="));
        rwTable.put(">=", new Token(TokenNames.GETHAN, ">="));
        rwTable.put("AND", new Token(TokenNames.AND, "AND"));
        rwTable.put("OR", new Token(TokenNames.OR, "OR"));
        rwTable.put("NOT", new Token(TokenNames.NOT, "NOT"));
        rwTable.put("NAND", new Token(TokenNames.NAND, "NAND"));
        rwTable.put("NOR", new Token(TokenNames.NOR, "NOR"));
        rwTable.put(",", new Token(TokenNames.COMMA, ","));
        rwTable.put(";", new Token(TokenNames.EOS, ";"));
        rwTable.put("~", new Token(TokenNames.CONCAT, "~"));
        rwTable.put("*", new Token(TokenNames.MULT, "*"));
        rwTable.put("+", new Token(TokenNames.PLUS, "+"));
        rwTable.put("-", new Token(TokenNames.MINUS, "-"));
        rwTable.put("(", new Token(TokenNames.LPAREN, "("));
        rwTable.put(")", new Token(TokenNames.RPAREN, ")"));
        rwTable.put("{", new Token(TokenNames.LCURLY, "{"));
        rwTable.put("}", new Token(TokenNames.RCURLY, "}"));
        rwTable.put("THE", new Token(TokenNames.THE, "THE"));
        rwTable.put("WITH", new Token(TokenNames.WITH, "WITH"));
        rwTable.put("UNTIL", new Token(TokenNames.UNTIL, "UNTIL"));
        rwTable.put("CONSTANT", new Token(TokenNames.CONSTANT, "CONSTANT"));
    }
    
    public LexAnalyzer(String file) throws IOException, FileNotFoundException{
        reader = new RandomAccessFile(file, "r");
        input = file;
        addReservedWords();
    }
    
    public void incIndex(){
        ++i;
        ++col;
    }
    
    public void getTotalChar() throws IOException{
            while(reader.read() != -1){
                ++totalCharacters;
            }
    }
    
    public char getChar() throws IOException, FileNotFoundException{
            reader = new RandomAccessFile(input, "r");
            if(reader.read() != -1){
                reader.seek(i);
                return (char) (reader.read());
            }
            return (char) -1;
    }
    
    public void storeChar(){
        try{
            char c;
            c = getChar();
            strBuffer += c;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void consumeSpace() throws IOException{
        char c;
        c = getChar();
        while(space.indexOf(c) >= 0 || c == '\t' || c == '\n' || c == '\r' || c == (char) -1){
            if(c == '\n'){
                ++row;
                col = -1;
            }
            incIndex();
            c = getChar();
        }
    }
    
    public String getTokenType() throws IOException{
            char c;
            consumeSpace();
            strBuffer = "";
            
            //Single Symbols
            boolean ssymCheck = true;
            String sym = "Start";
            while(ssymCheck){
                switch(sym){
                    case "Start":
                        c = getChar();
                        if(singlesym.indexOf(c) >= 0){
                            storeChar();
                            incIndex();
                            sym = "Symbol";
                        }
                        else{
                            sym = "Fail";
                        }
                        break;
                    case "Symbol":
                        return "Single Symbol";
                    case "Fail":
                        ssymCheck = false;
                        break;
                }
            }
            
            //Double Symbols
            boolean dsymCheck = true;
            String stateSym = "Start";
            strBuffer = "";
            while(dsymCheck){
                switch(stateSym){
                    case "Start":
                        c = getChar();
                        if(c == '>' || c == '<'){
                            stateSym = "GL";
                            storeChar();
                            incIndex();
                        }
                        else if(c == '!'){
                            stateSym = "Not";
                            storeChar();
                            incIndex();
                        }
                        else{
                            stateSym = "Fail";
                        }
                        break;
                    case "GL":
                        c = getChar();
                        if(c == '='){
                            stateSym = "DSymbol";
                            storeChar();
                            incIndex();
                        }
                        else if(space.indexOf(c) >= 0 || c == '\n' || c == '\t' || c == '\r' || squote.indexOf(c) >= 0 || digits.indexOf(c) >= 0 || capLetters.indexOf(c) >= 0){
                            stateSym = "DSymbol";
                        }
                        else{
                            stateSym = "Fail";
                        }
                        break;
                    case "Not":
                        c = getChar();
                        if(c == '='){
                            stateSym = "DSymbol";
                            storeChar();
                            incIndex();
                        }
                        else{
                            stateSym = "Fail";
                        }
                        break;
                    case "DSymbol":
                        return "Double Symbol";
                    case "Fail":
                        dsymCheck = false;
                        break;
                }
            }
    
            //Reserved Words
            String stateKW = "Start";
            boolean kwCheck = true;
            strBuffer = "";
            while(kwCheck){
                switch(stateKW){
                    case "Start":
                        c = getChar();
                        if(capLetters.indexOf(c) >= 0){
                            storeChar();
                            incIndex();
                        }
                        else if(space.indexOf(c) >= 0 || c == '\n' || c == '\t' || c == '\r' || c == '{' || c == '(' ||c == (char) -1 || c == ';'){
                            stateKW = "Keyword";
                        }
                        else{
                            stateKW = "Fail";
                        }
                        break;
                    case "Keyword":
                        return "Keyword";
                    case "Fail":
                        kwCheck = false;
                        break;
                }
            }
            
            //Identifier
            String stateID = "Start";
            boolean idCheck = true;
            strBuffer = "";
            while(idCheck){
                switch(stateID){
                    case "Start":
                        c = getChar();
                        if(smallLetters.indexOf(c) >= 0){
                            stateID = "Maybe";
                            storeChar();
                            incIndex();
                        }
                        else{
                           stateID = "Fail";
                        }
                        break;
                    case "Maybe":
                        c = getChar();
                        if(smallLetters.indexOf(c) >= 0 || digits.indexOf(c) >= 0 || c == '_'){
                            storeChar();
                            incIndex();
                            prev = c;
                        }
                        else if((space.indexOf(c) >= 0 || c == '\n' || c == '\t' || c == '\r' || c == ';' || c == '!' ||  c == ',' || arith.indexOf(c) >= 0) && prev != '_'){
                            stateID = "ID";
                        }
                        else{
                            stateID = "Fail";
                        }
                        break;
                    case "ID":
                        return "ID";
                    case "Fail":
                        idCheck = false;
                        break;
                }
            }
            
            //Number Constants
            String stateInt = "Start";
            boolean intCheck = true;
            strBuffer = "";
            while(intCheck){
                switch(stateInt){
                    case "Start":
                        c = getChar();
                        if(digits.indexOf(c) >= 0){
                            storeChar();
                            incIndex();
                        }
                        else if(space.indexOf(c) >= 0 || c == '\n' || c == '\t' || c == '\r' || c == ';' || arith.indexOf(c) >= 0){
                            stateInt = "numConst";
                        }
                        else{
                            stateInt = "Fail";
                        }
                        break;
                    case "numConst":
                        return "numConst";
                    case "Fail":
                        intCheck = false;
                        break;
                }
            }
            
            //String Literals
            String stateStr = "Start";
            boolean strCheck = true;
            strBuffer = "";
            while(strCheck){
                switch(stateStr){
                    case "Start":
                        c = getChar();
                        if(dquote.indexOf(c) >= 0){
                            stateStr = "Maybe";
                            storeChar();
                            incIndex();
                        }
                        else{
                            stateStr = "Fail";
                        }
                        break;
                    case "Maybe":
                        c = getChar();
                        if(letters.indexOf(c) >= 0 || digits.indexOf(c) >= 0 || specialSymbols.indexOf(c) >= 0 || space.indexOf(c) >= 0){
                            storeChar();
                            incIndex();
                        }
                        else if(dquote.indexOf(c) >= 0){
                            stateStr = "strLit";
                            storeChar();
                            incIndex();
                        }
                        else{
                            stateStr = "Fail";
                        }
                        break;
                    case "strLit":
                        return "strLit";
                    case "Fail":
                        strCheck = false;
                        break;
                }
            }
            
            //Character Literals
            String stateChar = "Start";
            boolean charCheck = true;
            strBuffer = "";
            while(charCheck){
                switch(stateChar){
                    case "Start":
                        c = getChar();
                        if(squote.indexOf(c) >= 0){
                            stateChar = "Maybe";
                            storeChar();
                            incIndex();
                        }
                        else{
                            stateChar = "Fail";
                        }
                        break;
                    case "Maybe":
                        c = getChar();
                        if(letters.indexOf(c) >= 0){
                            stateChar = "End";
                            storeChar();
                            incIndex();
                        }
                        else{
                            stateChar = "Fail";
                        }
                        break;
                    case "End":
                        c = getChar();
                        if(squote.indexOf(c) >= 0){
                            stateChar = "charLit";
                            storeChar();
                            incIndex();
                        }
                        else{
                              stateChar = "Fail";
                        }
                        break;
                    case "charLit":
                        return "charLit";
                    case "Fail":
                        charCheck = false;
                        break;
                }
            }
            
            //Single Comments
            String stateComm = "Start";
            boolean commCheck = true;
            strBuffer = "";
            while(commCheck){
                switch(stateComm){
                    case "Start":
                        c = getChar();
                        if(c == '/'){
                            storeChar();
                            incIndex();
                            stateComm = "Forward";
                        }
                        else{
                            stateComm = "Fail";
                        }
                        break;
                    case "Forward":
                        c = getChar();
                        if(c == '/'){
                            storeChar();
                            incIndex();
                            stateComm = "Comment";
                        }
                        else{
                            stateComm = "Fail";
                        }
                        break;
                    case "Comment":
                        c = getChar();
                        if(letters.indexOf(c) >= 0 || digits.indexOf(c) >= 0 || specialSymbols.indexOf(c) >= 0 || space.indexOf(c) >= 0){
                            storeChar();
                            incIndex();
                        }
                        else if(c == '\n' || c == '\r'){
                            stateComm = "Single Comment";
                        }
                        else{
                            stateComm = "Fail";
                        }
                        break;
                    case "Single Comment":
                        return "Single Comment";
                    case "Fail":
                        commCheck = false;
                        break;
                }
            }
            
            //Multiline Comment
            String stateMulti = "Start";
            boolean multiCheck = true;
            while(multiCheck){
                switch(stateMulti){
                    case "Start":
                        c = getChar();
                        if(c == '$'){
                            storeChar();
                            incIndex();
                            stateMulti = "Start2";
                        }
                        else{
                            stateMulti = "Fail";
                        }
                        break;
                    case "Start2":
                        c = getChar();
                        if(c == '<'){
                            storeChar();
                            incIndex();
                            stateMulti = "Maybe";
                        }
                        else{
                            stateMulti = "Fail";
                        }
                        break;
                    case "Maybe":
                        c = getChar();
                        if(letters.indexOf(c) >= 0 || digits.indexOf(c) >= 0 || (specialSymbols.indexOf(c) >= 0 && c != '>') || space.indexOf(c) >=0 || c == '\n' || c == '\t' || c == '\r'){
                            storeChar();
                            incIndex();
                        }
                        else if(c == '>'){
                            stateMulti = "End";
                        }
                        else{
                            stateMulti = "Fail";
                        }
                        break;
                    case "End":
                        c = getChar();
                        if(c == '>'){
                            storeChar();
                            incIndex();
                            stateMulti = "End2";
                        }
                        else{
                            stateMulti = "Fail";
                        }
                        break;
                    case "End2":
                        c = getChar();
                        if(c == '$'){
                            storeChar();
                            incIndex();
                            stateMulti = "Multiline Comment";
                        }
                        else if(letters.indexOf(c) >= 0 || digits.indexOf(c) >= 0 || (specialSymbols.indexOf(c) >= 0 && c != '$') || space.indexOf(c) >=0 || c == '\n' || c == '\t' || c == '\r'){
                            stateMulti = "Maybe";
                        }
                        else{
                            stateMulti = "Fail";
                        }
                        break;
                    case "Multiline Comment":
                        return "Multiline Comment";
                    case "Fail":
                        multiCheck = false;
                        break;
                }
            }
        incIndex();
        return "FAIL";
    }
    
}
