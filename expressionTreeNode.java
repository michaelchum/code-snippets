// STUDENT_NAME: Michael Ho
// STUDENT_ID: 260532097

import java.lang.*;

class expressionTreeNodeSolution {
    private String value;
    private expressionTreeNodeSolution leftChild, rightChild, parent;
    
    expressionTreeNodeSolution() {
        value = null; 
        leftChild = rightChild = parent = null;
    }
    
    // Constructor
    /* Arguments: String s: Value to be stored in the node
                  expressionTreeNodeSolution l, r, p: the left child, right child, and parent of the node to created      
       Returns: the newly created expressionTreeNodeSolution               
    */
    expressionTreeNodeSolution(String s, expressionTreeNodeSolution l, expressionTreeNodeSolution r, expressionTreeNodeSolution p) {
        value = s; 
        leftChild = l; 
        rightChild = r;
        parent = p;
    }
    
    /* Basic access methods */
    String getValue() { return value; }

    expressionTreeNodeSolution getLeftChild() { return leftChild; }

    expressionTreeNodeSolution getRightChild() { return rightChild; }

    expressionTreeNodeSolution getParent() { return parent; }


    /* Basic setting methods */ 
    void setValue(String o) { value = o; }
    
    // sets the left child of this node to n
    void setLeftChild(expressionTreeNodeSolution n) { 
        leftChild = n; 
        n.parent = this; 
    }
    
    // sets the right child of this node to n
    void setRightChild(expressionTreeNodeSolution n) { 
        rightChild = n; 
        n.parent=this; 
    }
    

    // Returns the root of the tree describing the expression s
    // Watch out: it makes no validity checks whatsoever!
    expressionTreeNodeSolution(String s) {
        // check if s contains parentheses. If it doesn't, then it's a leaf
        if (s.indexOf("(")==-1) setValue(s);
        else {  // it's not a leaf

            /* break the string into three parts: the operator, the left operand,
               and the right operand. ***/
            setValue( s.substring( 0 , s.indexOf( "(" ) ) );
            // delimit the left operand 2008
            int left = s.indexOf("(")+1;
            int i = left;
            int parCount = 0;
            // find the comma separating the two operands
            while (parCount>=0 && !(s.charAt(i)==',' && parCount==0)) {
                if ( s.charAt(i) == '(' ) parCount++;
                if ( s.charAt(i) == ')' ) parCount--;
                i++;
            }
            int mid=i;
            if (parCount<0) mid--;

        // recursively build the left subtree
            setLeftChild(new expressionTreeNodeSolution(s.substring(left,mid)));
    
            if (parCount==0) {
                // it is a binary operator
                // find the end of the second operand.F13
                while ( ! (s.charAt(i) == ')' && parCount == 0 ) )  {
                    if ( s.charAt(i) == '(' ) parCount++;
                    if ( s.charAt(i) == ')' ) parCount--;
                    i++;
                }
                int right=i;
                setRightChild( new expressionTreeNodeSolution( s.substring( mid + 1, right)));
            }
        }
    }


    // Returns a copy of the subtree rooted at this node... 2013
    expressionTreeNodeSolution deepCopy() {
        expressionTreeNodeSolution n = new expressionTreeNodeSolution();
        n.setValue( getValue() );
        if ( getLeftChild()!=null ) n.setLeftChild( getLeftChild().deepCopy() );
        if ( getRightChild()!=null ) n.setRightChild( getRightChild().deepCopy() );
        return n;
    }
    
    // Returns a String describing the subtree rooted at a certain node.
    public String toString() {
        String ret = value;
        if ( getLeftChild() == null ) return ret;
        else ret = ret + "(" + getLeftChild().toString();
        if ( getRightChild() == null ) return ret + ")";
        else ret = ret + "," + getRightChild().toString();
        ret = ret + ")";
        return ret;
    } 

    // Returns the value of the expression rooted at a given node
    // when x has a certain value
    double evaluate(double x) {
        String node = getValue();

        // If the node is an operator, use recursion with the appropriate sign
        switch (node) {
            case "x": // node is x operand, return the value x input by user
                return x;
            case "add": // node is addition operator, recursively add children
                return getRightChild().evaluate(x) + getLeftChild().evaluate(x);
            case "mult": // node is multiplication operator, recursively multiply children
                return getRightChild().evaluate(x) * getLeftChild().evaluate(x);
            case "minus": // node is subtraction operator, recursively subtract children
                return getLeftChild().evaluate(x) - getRightChild().evaluate(x);
            case "sin": // node is sin operator, recursively peform sin on child
                return Math.sin(getLeftChild().evaluate(x));
            case "cos": // node is cos operator, recursively peform cos on child
                return Math.cos(getLeftChild().evaluate(x));
            case "exp": // node is exp operator, recursively peform exp on child
                return Math.exp(getLeftChild().evaluate(x));
        }

        // If the node is not an operator, is it therefore an operand number, return as a double
        return Double.parseDouble(node);

    }                                                 

    /* returns the root of a new expression tree representing the derivative of the
       original expression */
    expressionTreeNodeSolution differentiate() {
        String node = getValue();

        expressionTreeNodeSolution rightChild, leftChild;

        switch (node) {
            case "x": // BASE CASE: node is x, return its derivative, 1
                return new expressionTreeNodeSolution("1");
            case "add": // node is addition operator
                return new expressionTreeNodeSolution(getValue(), getLeftChild().differentiate(), getRightChild().differentiate(), null);
            case "mult": // node is multiplication operator
                rightChild = new expressionTreeNodeSolution(getValue(), getLeftChild().differentiate(), getRightChild().deepCopy(), null);
                leftChild = new expressionTreeNodeSolution(getValue(), getLeftChild().deepCopy(), getRightChild().differentiate(), null);
                return new expressionTreeNodeSolution("add", leftChild, rightChild, null);
            case "minus": // node is subtraction operator
                return new expressionTreeNodeSolution(getValue(), getLeftChild().differentiate(), getRightChild().differentiate(), null);
            case "sin": // node is sin operator
                leftChild = new expressionTreeNodeSolution("cos", getLeftChild().deepCopy(), null, null);
                rightChild = getLeftChild().differentiate();
                return new expressionTreeNodeSolution("mult", leftChild, rightChild, null);
            case "cos": // node is cos operator
                leftChild = new expressionTreeNodeSolution("sin", getLeftChild(), null, null);
                leftChild = new expressionTreeNodeSolution("minus", new expressionTreeNodeSolution("0"), leftChild, null);
                rightChild = getLeftChild().differentiate();
                return new expressionTreeNodeSolution("mult", leftChild, rightChild, null);
            case "exp": // node is exp operator
                leftChild = new expressionTreeNodeSolution("exp", getLeftChild().deepCopy(), null, null);
                rightChild = getLeftChild().differentiate();
                return new expressionTreeNodeSolution("mult", leftChild, rightChild, null);
            default: // BASE CASE: else node is a constant, return 0
                return new expressionTreeNodeSolution("0");
        }
    }
        
    public static void main(String args[]) {
        //expressionTreeNodeSolution e = new expressionTreeNodeSolution("mult(add(2,x),cos(x))");
        expressionTreeNodeSolution e = new expressionTreeNodeSolution("mult(x,add(add(2,x),cos(minus(x,4))))");
        System.out.println(e);
        System.out.println(e.evaluate(1));
        System.out.println(e.differentiate());
    }

}
