package Assignment;


import java.util.*;

public class ExpTree{

    private int kind;
    private Object value;
    private ExpTree lChild, rChild;
    public static final int numNode = 0, idNode = 1, opNode = 2, andNode = 3, letNode = 4, defNode = 5;


    public ExpTree(int knd, Object val, ExpTree l, ExpTree r) {
        kind = knd;
        value = val;
        lChild = l;
        rChild = r;
    }

    //Returns the binary tree in postOrder
    public void postOrder() {

        //Traverse left
        if (this.lChild != null) {
            this.lChild.postOrder();
        }

        //Traverse right
        if (this.rChild != null) {
            // then recur on right subtree
            this.rChild.postOrder();
        }

        //Outputs the node's contents. At the end of the function as then each child has been exhausted.
        System.out.print(this.value + " ");

    }



    public int evaluate() {
        return evaluator(this);
    }

    //Returns the evaluation of the expression as an int
    private int evaluator(ExpTree node) {

        //If leaf node or 'and, let' node.
        if ((node.lChild == null) && (node.rChild == null)) {
            if (node.kind == numNode) {
                return (int) node.value;
            } else if (node.kind == idNode) {
                try {
                    return Ass2.assignments.get(node.value);
                } catch(Exception e) {
                    return 0;
                }
            }if (node.kind == defNode){
                Ass2.assignments.put(String.valueOf(node.lChild.value), ((Integer) node.rChild.value));
                node.kind = numNode;
                return evaluator(node);
            }
        }
        //if an and or let node, goes down the left of the tree, then returns the right
        else if (node.kind == andNode || node.kind == letNode ){
            evaluator(node.lChild);
            return (evaluator(node.rChild));
        }
        //if the node is a defnode (=), it looks at the left of the node which is the identifier, and the right which is the value, then puts the values together in the hashmap 'assignments'
        else if (node.kind == defNode){
                //if it needs to go deeper
                if (node.rChild.kind == opNode) {
                    Ass2.assignments.put(String.valueOf(node.lChild.value), evaluator(node.rChild));
                } else {
                    Ass2.assignments.put(String.valueOf(node.lChild.value), (int) node.rChild.value);
                }
                return -1;
        }
        else if (node.kind == opNode) {
            switch ((String) node.value) {
                case "+":
                    assert evaluator(node.lChild) != -1;
                    assert evaluator(node.rChild) != -1;
                    return evaluator(node.lChild) + evaluator(node.rChild);
                case "-":
                    assert evaluator(node.lChild) != -1;
                    assert evaluator(node.rChild) != -1;
                    return evaluator(node.lChild) - evaluator(node.rChild);
                case "*":
                    assert evaluator(node.lChild) != -1;
                    assert evaluator(node.rChild) != -1;
                    return evaluator(node.lChild) * evaluator(node.rChild);
                case "/":
                    assert evaluator(node.lChild) != -1;
                    assert evaluator(node.rChild) != -1;
                    return evaluator(node.lChild) / evaluator(node.rChild);
                case "%":
                    assert evaluator(node.lChild) != -1;
                    assert evaluator(node.rChild) != -1;
                    return evaluator(node.lChild) % evaluator(node.rChild);
                case "^":
                    assert evaluator(node.lChild) != -1;
                    assert evaluator(node.rChild) != -1;
                    int answer = evaluator(node.lChild);
                    if (evaluator(node.rChild) == 0){
                        return 1;
                    }
                    for (int i = 1; i < evaluator(node.rChild); i++){
                        answer = answer * evaluator(node.lChild);
                    }
                    return answer;
            }
        }
        return -1;
    }



    @Override
    public String toString(){
        //just calls the inOrder function
        return inOrder();
    }



    private String parenthesis(String result, ExpTree node, ExpTree child){
        //child = lChild or rChild
        //node = this Exptree
        //result = the returned, modified result String

        //Arrays containing the opnodes to check
        List<String> subtractPlusCheck = new ArrayList<>(Arrays.asList("+", "-"));
        List<String> multiplierCheck = new ArrayList<>(Arrays.asList("*", "/", "^", "%"));
        List<String> reducedMultiplierCheck = new ArrayList<>(Arrays.asList("*", "/", "%"));

        String parenthesis = "(";


        if (child!= null) {
            if (subtractPlusCheck.contains(child.value)){
                if (multiplierCheck.contains(node.value)){
                    result += parenthesis;
                }
            } else if (reducedMultiplierCheck.contains(child.value)) {
                if (node.value == "^" ) {
                    result += parenthesis;
                }
            } else if (child.value == "^") {
                if (node.value == "^" ) {
                    result += parenthesis;
                }
            }

            result += child.toString();
            parenthesis = ")"; //flips the parenthesis to close them

            if(subtractPlusCheck.contains(child.value)){
                if (multiplierCheck.contains(node.value)){
                    result += parenthesis;
                }
            } else if (reducedMultiplierCheck.contains(child.value)){
                if (node.value == "^" ) {
                    result += parenthesis;
                }
            } else if (child.value == "^") {
                if (node.value == "^" ) {
                    result += parenthesis;
                }
            }
        }
        return result;
    }

    //Returns the binary tree inOrder traversal as a String
    public String inOrder(){
        String result = "";

        result = this.parenthesis(result, this, lChild);

        if (this.kind == letNode || this.kind == andNode) {
            result += " " + this.value + " ";
        } else {
            result += " " + this.value;
        }

        result = this.parenthesis(result, this, rChild);

        return result;
    }
}

