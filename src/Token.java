
/**
 *
 * @author Luis
 */
public class Token {
    public TokenNames kind;
    public String dtype;
    public String value;
    public String lexeme;
    
    public Token(TokenNames k, String l){
        kind = k;
        lexeme = l;
        value = "";
        dtype = "";
    }
    
    public String toString(){
        return "[" + kind + " = " + lexeme + "]";
    }
    
    public String getTokenKind(){
        return "[" + kind + "]";
    }
    
}
    
enum TokenNames {
    
    KINGDOM, ENDKINGDOM, FISH, INSECT, REPTILE, KILLER, PREDATOR, PREY,
    IF, ELSE, REPRODUCE, PROCREATE, INHALE, EXHALE, IS, MULTICOM, SINGCOM,
    EQUALS, NEQUALS, GTHAN, LTHAN, GETHAN, LETHAN, AND, OR, NOT,
    NAND, NOR, COMMA, EOS, CONCAT, MULT, PLUS, MINUS,
    LPAREN, RPAREN, LCURLY, RCURLY, THE, WITH, UNTIL, CONSTANT, ID,
    STRLIT, NUMCONST, CHARLIT, $
    
}
