
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Interpreter {
    public static kTree parseTree;
    public static String datatype = "";
    public static Scanner s = new Scanner(System.in);
    public static int in1 = 0;
    public static String in2 = "";
    public static char in3 = ' ';
    public static boolean in4;
    public static boolean cond = true;
    public static String avalue = "";
    public static int val;
    
    public static void main(kTree pT) {
        parseTree = pT;
        traverse();
    }
    
    public static void traverse(){
        kNode p = parseTree.root.child.child.next.next;
        stmt_list(p); 
    }
    
    public static void stmt_list(kNode k){
        k = k.child;
        stmt(k);
        k = k.next;
        if(k != null){
            stmt_list(k);
        }
    }
    
    public static void stmt(kNode s){
        switch(s.child.info){
            case "decl":
                decl(s.child);
                break;
            case "assign":
                assign(s.child);
                break;
            case "cond":
                cond(s.child);
                break;
            case "loop":
                loop(s.child);
                break;
            case "read":
                read(s.child);
                break;
            case "write":
                write(s.child);
                break;
        }
    }
    
    public static void decl(kNode d){
        kNode dec = d.child;
        type(dec);
        dec = dec.next;
        vars(dec);
    }
    
    public static void assign(kNode a){
        kNode id = a.child;
        kNode assignment = id.next.next;
//        System.out.println(assignment.token.lexeme);
        expr(assignment);
        id.token.value = avalue;
        //System.out.println("The value of " + id.token.lexeme + " is " + id.token.value);
    }
    
    public static void cond(kNode c){
        kNode ifelse = c.child;
        kNode ifstmt = ifelse.next.next;
        exprt(ifstmt);
        ifstmt.token.value = Boolean.toString(cond);
   //     System.out.println(ifstmt.token.value);
   //     System.out.println(ifstmt.next.next.next.next.next.info);
        if(ifstmt.token.value == "true"){
            stmt_list(ifstmt.next.next.next);   
        }
   //    System.out.println(ifstmt.next.next.next.next.next.next.next.info);
        if(ifstmt.next.next.next.next.next.info != null && ifstmt.token.value == "false"){
            kNode elsestmt = ifstmt.next.next.next.next.next;
            stmt_list(elsestmt.next.next);
        }
    }
    
    public static void loop(kNode l){
        switch(l.child.info){
            case "for_loop":
                for_loop(l.child);
                break;
            case "while_loop":
                while_loop(l.child);
                break;
        }
    }
    
    public static void read(kNode r){
        kNode input = r.child.next.next;
        //System.out.println(input.token.lexeme);
        System.out.print("Input value for " + input.token.lexeme + ": ");
        switch(input.token.dtype){
            case "FISH":
                in1 = s.nextInt();
                input.token.value = Integer.toString(in1);
                break;
            case "INSECT":
                in3 = s.next().charAt(0);
                input.token.value = Character.toString(in3);
                break;
            case "REPTILE":
                in2 = s.nextLine();
                input.token.value = in2;
                break;
            case "KILLER":
                in4 = s.nextBoolean();
                input.token.value = Boolean.toString(in4);
                break;
        }
        //System.out.println("The value of " + input.token.lexeme + " is " + input.token.value);
    }
    
    public static void write(kNode w){
        kNode id = w.child;
        kNode output = w.child.next.next;
        expr(output);
        id.token.value = avalue;
        System.out.println(id.token.value);
    }
    
    public static void type(kNode t){
        switch(t.child.info){
            case "[FISH]":
                datatype = "FISH";
                break;
            case "[REPTILE]":
                datatype = "REPTILE";
                break;
            case "[INSECT]":
                datatype = "INSECT";
                break;
            case "[KILLER]":
                datatype = "KILLER";
                break;
        }
    }
    
    public static void vars(kNode v){
        kNode variable = v.child;
        variable.token.dtype = datatype;
        //System.out.println(variable.token.lexeme + " is a " + variable.token.dtype + " type.");
        if(variable.next != null){
            vars(variable.next.next);
        }
    }
    
    public static void for_loop(kNode f){
        
    }
    
    public static void while_loop(kNode w){
        
    }
    
    public static void expr(kNode e){
        kNode expr = e.child;
        //System.out.println(expr.info);
        if(expr.info == "expr"){
            expr(expr);
            expr.token.value = avalue;
            exprt(expr.next.next);
            expr.next.next.token.value = avalue;
            avalue = expr.token.value + expr.next.next.token.value;
        }
        else{
            exprt(expr);
        }
    }
    
    public static void exprt(kNode et){
        kNode exprt = et.child;
        if(exprt.info == "exprt"){
            exprt(exprt);
            exprt1(exprt.next.next);
        }
        else{
            exprt1(exprt);
        }
    }
    
    public static void exprt1(kNode e1){
        kNode exprt1 = e1.child;
        if(exprt1.info == "exprt1"){
            exprt1(exprt1);
            exprt2(exprt1.next.next);
        }
        else{
            exprt2(exprt1);
        }
    }
    
    public static void exprt2(kNode e2){
        kNode exprt2 = e2.child;
        if(exprt2.info == "exprt2"){
            exprt2(exprt2);
            exprt3(exprt2.next.next);
        }
        else{
            exprt3(exprt2);
        }
    }
    
    public static void exprt3(kNode e3){
        kNode exprt3 = e3.child;
        if(exprt3.info == "exprt3"){
            exprt3(exprt3);
            exprt4(exprt3.next.next);
        }
        else{
            exprt4(exprt3);
        }
    }
    
    public static void exprt4(kNode e4){
        kNode exprt4 = e4.child;
        if(exprt4.info == "exprt4"){
            exprt4(exprt4);
            exprt5(exprt4.next.next);
        }
        else if(exprt4.info == "[NOT]"){
            exprt4(exprt4.next);
        }
        else{
            exprt5(exprt4);
        }
    }
    
    public static void exprt5(kNode e5){
        kNode exprt5 = e5.child;
        if(exprt5.info == "exprt5"){
            exprt5(exprt5);
            exprt5.token.value = avalue;
            exprn(exprt5.next.next);
            exprt5.next.next.token.value = avalue;
            switch(exprt5.next.info){
                case "[EQUALS]":
                    if(exprt5.token.value == exprt5.next.next.token.value){
                        cond = true;
                    }
                    else{
                        cond = false;
                    }
                    break;
                case "[NEQUALS]":
                    if(exprt5.token.value != exprt5.next.next.token.value){
                        cond = true;
                    }
                    else{
                        cond = false;
                    }
                    break;
                case "[GTHAN]":
                    if(Integer.parseInt(exprt5.token.value) > Integer.parseInt(exprt5.next.next.token.value)){
                        cond = true;
                    }
                    else{
                        cond = false;
                    }
                    break;
                case "[LTHAN]":
                    if(Integer.parseInt(exprt5.token.value) < Integer.parseInt(exprt5.next.next.token.value)){
                        cond = true;
                    }
                    else{
                        cond = false;
                    }
                    break;
                case "[GETHAN]":
                    if(Integer.parseInt(exprt5.token.value) >= Integer.parseInt(exprt5.next.next.token.value)){
                        cond = true;
                    }
                    else{
                        cond = false;
                    }
                    break;
                case "[LETHAN]":
                    if(Integer.parseInt(exprt5.token.value) <= Integer.parseInt(exprt5.next.next.token.value)){
                        cond = true;
                    }
                    else{
                        cond = false;
                    }
                    break;
            }
        }
        else{
            exprn(exprt5);
        }
    }
    
    public static void exprn(kNode en){
        kNode exprn = en.child;
        if(exprn.info == "exprn"){
            exprn(exprn);
            //System.out.println(avalue);
            exprn.token.value = avalue;
            exprnterm(exprn.next.next);
            exprn.next.next.token.value = avalue;
            //System.out.println("OP: " + exprn.next.info);
            switch(exprn.next.info){
                case "[PLUS]":
                    val = Integer.parseInt(exprn.token.value) + Integer.parseInt(exprn.next.next.token.value);
                    avalue = Integer.toString(val);
                    break;
                case "[MINUS]":
                    //System.out.println(exprn.token.value + "  " + exprn.next.next.token.value);
                    val = Integer.parseInt(exprn.token.value) - Integer.parseInt(exprn.next.next.token.value);
                    avalue = Integer.toString(val);
                    break;
            }
        }
        else{
            exprnterm(exprn);
        }
    }
    
    public static void exprnterm(kNode ent){
        kNode exprnterm = ent.child;
        if(exprnterm.info == "exprnterm"){
            exprnterm(exprnterm);
            exprnterm.token.value = avalue;
            exprnfactor(exprnterm.next.next);
            exprnterm.next.next.token.value = avalue;
            val = Integer.parseInt(exprnterm.token.value) * Integer.parseInt(exprnterm.next.next.token.value);
            avalue = Integer.toString(val);
        }
        else{
            exprnfactor(exprnterm);
        }
    }
    
    public static void exprnfactor(kNode enf){
        kNode exprnfactor = enf.child;
  //      System.out.println(exprnfactor.info);
        if(exprnfactor.info == "[LPAREN]"){
            expr(exprnfactor.next);
        }
        else{
            unit(exprnfactor);
        }
    }
    
    public static void unit(kNode u){
        kNode cons = u.child;
        Token tok = new Token(null, "");
   //     System.out.println("Value:");
   //     System.out.println(cons.token.lexeme);;
        switch(cons.info){
            case "[ID]":
                tok = Parser.idTable.get(cons.token.lexeme);
//              System.out.println("Value: " + tok.value);
                if(tok.value != "" || tok.value != null){
                    avalue = tok.value;
                }
                else{
                    cons.token.value = cons.token.lexeme;
                    avalue = cons.token.value;
                }
                break;
            case "[STRLIT]":
                tok.value = cons.token.lexeme;
                if(tok.value != "" || tok.value != null){
                    avalue = tok.value.replace("\"", "");
                }
                else{
                    cons.token.value = cons.token.lexeme;
                    avalue = cons.token.value.replace("\"", "");
                }
                break;
            case "[NUMCONST]":
            case "[CHARLIT]":
            case "[PREDATOR]":
            case "[PREY]":
                tok.value = cons.token.lexeme;
                if(tok.value != "" || tok.value != null){
                    avalue = tok.value;
                }
                else{
                    cons.token.value = cons.token.lexeme;
                    avalue = cons.token.value;
                }
                break;
        }
    }
}
