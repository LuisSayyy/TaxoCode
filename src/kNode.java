
/**
 *
 * @author Luis
 */
public class kNode {
    
    public String info;
    public Token token;
    public kNode child, next, prev, parent;
    public int level;
    public boolean isRoot = false;
    
    public kNode(){
        info = null;
        token = new Token(null,"");
        next = child = null;
    }
    
    public kNode(String information){   //for variable nodes
        info = information;
        token = new Token(null,"");
        next = prev = child = null;
    }
    
    public kNode(String information, Token tok){    //for terminal nodes
        info = information;
        token = tok;
        next = prev = child = null;
    }
    
    public kNode(String information, kNode firstChild){
        info = information;
        child = firstChild;
        token = new Token(null,"");
        next = prev = null;
    } 
}
