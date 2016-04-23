
/**
 *
 * @author Luis
 */

public class kTree {
    
    public kNode root;
    
    public kTree(){
        root = null;
    }
    
    public kTree(kNode r){
        root = r;
        root.isRoot = true;
    }
    
    public void addParent(kNode child, kNode parent){ //for Bottom Up Approach
        if(parent == null){
            String error = "addParent error: parent parameter is null";
            System.out.println(error);
        }
        else if(child == null){
            String error = "addParent error: child parameter is null";
            System.out.println(error);
        }
        else{
            if(parent.child == null){
                parent.child = child;
                child.parent = parent;
            }
            else addSibling(parent.child, child);
        }
    }
    
    private void fixLevels(){
        fixLevels(root, 0);
    }
    
    private void fixLevels(kNode p, int level){
        if(p == null) return;
        p.level = level;
        fixLevels(p.child, level + 1);
        fixLevels(p.next, level + 1);
    }
    
    public void makeRoot(kNode node){
        root = node;
    }
    
    public void addChild(kNode parent, kNode child){ //for Top Down Approach
        if(root == null){
            //no nodes in the tree
            root = parent;
            root.isRoot = true;
            root.level = 0;
            root.child = child;
            root.child.level = 1;
            root.child.parent = root;
        }
        else if(root == parent){
            if(root.child == null){
                root.child = child;
                root.child.level = 1;
                root.child.parent = root;
            }
            else addSibling(root.child, child);
        }
        else{
            kNode p = getNode(root, parent);
            if(p != null){
                if(p.child == null){
                    p.child = child;
                    p.child.level = p.level + 1;
                    p.child.parent = p;
                }
                else addSibling(p.child, child);
            }
            else{
                String error = "addChild Error: Parent unreachable in the tree.";
                System.out.println(error);
            }
        }
    }
    
    public kNode getNode(kNode source, kNode findThis){
        if(source == null) return null;
        if(source == findThis) return findThis;
        if(source.child != null){
            kNode p = getNode(source.child, findThis);
            if(p != null) return p;
        }
        if(source.next != null){
            kNode p  = getNode(source.next, findThis);
            if(p != null) return p;
        }
        return null;
    }
    
    public void addSibling(kNode child, kNode sibling){
        for(kNode p = child; p != null; p = p.next){
            if(p.next == null){
                p.next = sibling;
                sibling.prev = p;
                sibling.parent = child.parent;
                sibling.level = sibling.parent.level + 1;
                break;
            }
        }
    }
    
    @Override
    public String toString(){
        return toString(root, 0);
    }
    
    private String toString(kNode p, int indention){
        String s = "";
        //for(int i = 0; i < indention; i++) s += "\t";
        if(p == null) return s + "null";
        s += "[info = " + p.info;
        //for(int i = 0; i < indention; i++) s += "\t";
        s += " child = " + toString(p.child, indention + 1);
        //for(int i = 0; i < indention; i++) s += "\t";
        s += " next = " + toString(p.next, indention + 1) + "]";
        return s;
    }
    
}
