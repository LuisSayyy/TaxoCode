
/**
 *
 * @author Luis
 */
public class kNode {
    
    public String info;
    public kNode child, next, prev, parent;
    public int level;
    public boolean isRoot = false;
    
    public kNode(){
        info = null;
        next = child = null;
    }
    
    public kNode(String information){
        info = information;
        next = prev = child = null;
    }
    
    public kNode(String information, kNode firstChild){
        info = information;
        child = firstChild;
        next = prev = null;
    } 
}
