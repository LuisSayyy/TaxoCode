
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
        kNode assignment = a.child;
        kNode value = assignment.next.next;
        //System.out.println(assignment.token.lexeme);
        //System.out.println(value.info);
        expr(value);
    }
    
    public static void cond(kNode c){
        
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
                in2 = s.next();
                input.token.value = in2;
                break;
            case "KILLER":
                in4 = s.nextBoolean();
                input.token.value = Boolean.toString(in4);
                break;
        }
        System.out.println("The value of " + input.token.lexeme + " is " + input.token.value);
    }
    
    public static void write(kNode w){
        kNode output = w.child;
        
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
        
    }
}
