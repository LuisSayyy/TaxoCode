
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luis
 */
public class Grammar {
    public Variables var;
    public String prod;
    public int rulenum;
    
    public Grammar(int rule, Variables v, String p){
        rulenum = rule;
        var = v;
        prod = p;
    }
    
    public String toString(){
        return var + " -> " + prod;
    }
}

enum Variables{
    start, block, stmt_list, stmt, decl, type, vars, assign, cond,
    loop, for_loop, while_loop, read, write, expr, exprt, exprt1, exprt2,
    exprt3, exprt4, exprt5, exprn, exprnterm, exprnfactor, unit
}
    
