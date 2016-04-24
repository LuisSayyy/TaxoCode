/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luis
 */
public class ParsingGoTo {
    /*start, block, stmt_list, stmt, decl, type, vars, assign, cond,
    loop, for_loop, while_loop, read, write, expr, exprt, exprt1, exprt2,
    exprt3, exprt4, exprt5, exprn, exprnterm, exprnfactor, unit*/
    public String[] entry = new String[24]; 
    public String[] var = new String[24];
    
    public ParsingGoTo(){
        for(int i=0; i<entry.length; i++) entry[i] = "[EMPTY]";
        listGoTo();
    }
    
    public void listGoTo(){
        var[0] = "block";
        var[1] = "stmt_list";
        var[2] = "stmt";
        var[3] = "decl";
        var[4] = "type";
        var[5] = "vars";
        var[6] = "assign";
        var[7] = "cond";
        var[8] = "loop";
        var[9] = "for_loop";
        var[10] = "while_loop";
        var[11] = "read";
        var[12] = "write";
        var[13] = "expr";
        var[14] = "exprt";
        var[15] = "exprt1";
        var[16] = "exprt2";
        var[17] = "exprt3";
        var[18] = "exprt4";
        var[19] = "exprt5";
        var[20] = "exprn";
        var[21] = "exprnterm";
        var[22] = "exprnfactor";
        var[23] = "unit";


    }
    
}
