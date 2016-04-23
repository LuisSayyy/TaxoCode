/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luis
 */
public class ParsingTokens {
    public String[] entry = new String[44];
    public String tok[] = new String[44];
    
    public ParsingTokens() {
        for(int i = 0; i < entry.length; i++) entry[i] = "[EMPTY]";
        listTokens();
    }
    
    public void listTokens(){
        tok[0] = "[KINGDOM]";
        tok[1] = "[ENDKINGDOM]";
        tok[2] = "[FISH]";
        tok[3] = "[INSECT]";
        tok[4] = "[REPTILE]";
        tok[5] = "[KILLER]";
        tok[6] = "[PREDATOR]";
        tok[7] = "[PREY]";
        tok[8] = "[IF]";
        tok[9] = "[ELSE]";
        tok[10] = "[REPRODUCE]";
        tok[11] = "[PROCREATE]";
        tok[12] = "[INHALE]";
        tok[13] = "[EXHALE]";
        tok[14] = "[IS]";
        tok[15] = "[EQUALS]";
        tok[16] = "[NEQUALS]";
        tok[17] = "[GTHAN]";
        tok[18] = "[LTHAN]";
        tok[19] = "[GETHAN]";
        tok[20] = "[LETHAN]";
        tok[21] = "[AND]";
        tok[22] = "[OR]";
        tok[23] = "[NAND]";
        tok[24] = "[NOR]";
        tok[25] = "[NOT]";
        tok[26] = "[COMMA]";
        tok[27] = "[EOS]";
        tok[28] = "[CONCAT]";
        tok[29] = "[MULT]";
        tok[30] = "[PLUS]";
        tok[31] = "[MINUS]";
        tok[32] = "[LPAREN]";
        tok[33] = "[RPAREN]";
        tok[34] = "[LCURLY]";
        tok[35] = "[RCURLY]";
        tok[36] = "[STRLIT]";
        tok[37] = "[ID]";
        tok[38] = "[NUMCONST]";
        tok[39] = "[CHARLIT]";
        tok[40] = "[WITH]";
        tok[41] = "[UNTIL]";
        tok[42] = "[CONSTANT]";
        tok[43] = "$";

    }
}
