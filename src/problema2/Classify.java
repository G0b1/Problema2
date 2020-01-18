/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problema2;

import java.util.Arrays;
import java.util.Stack;

/**
 *
 * @author Gabi
 */
/*
1 John 0
3 Jay 2
4 Unavailable 2
2 Jasmine 1
7 Jeremy 6
6 Jack 5
5 Unavailable 1
8 Johanna 1
 */
public class Classify {

    private String hStr;  // hierarchy string => (“John”, (“Jasmine”, (“Jay”), (“Unavailable”)), (“Unavailable”, (“Jack”, (“Jeremy”))), (“Johanna”))
    private String finalStr = " "; // 0 John => 1 Jasmine 0 => 2 Jay 1 =>..

    private int nodesCount = 0; //employees number;
    private int[] nodeInitPos; //node positions of “ used in node delimitation
    private int[] nodeFinalPos; //node positions of ” used in node delimitation

    private String[] nodeNames; //employees names;
    private String[] allNodeNames;

    public Classify(String hStr) {
        this.hStr = hStr;
    }

    public String getFinalString() {
        setFinalString();
        return finalStr;
    }

    private void init() {
        nodesCount = getNodesCount();

        nodeInitPos = getPositionsOfChar('“');
        nodeFinalPos = getPositionsOfChar('”');

        nodeNames = getNodeNames();
    }

    private void setFinalString() {
        int nodePos = 0;
        int lastParent = 0;

        //initialize first sets of data
        init();

        //an array copy of first node positions in main string
        allNodeNames = getNodeNames();

        //director set
        finalStr = 1 + " " + nodeNames[0] + " " + 0 + "\n";

        //parent stack & save first parent;
        Stack<Integer> parents = new Stack<Integer>();
        parents.push(lastParent);

        while (nodePos < nodesCount && nodesCount > 1) {
            if (isChild(nodePos)) {
                //create node line
                finalStr += (getOldPosition(getName(nodePos)) + 1) + " " + nodeNames[nodePos] + " " + (getOldPosition(getName(parents.lastElement())) + 1) + "\n";

                //remove child FROM MAIN STRING
                hStr = removeChild(nodePos);

                //reinitialize sets of datas for the new MAIN STRING
                init();

                //going back 1 node
                nodePos--;

                //if LAST PARENT becomes a child => remove it from parents stack
                if (parents.lastElement() == nodePos) {
                    parents.pop();
                }
            } else {
                //save current parent in stack
                parents.push(nodePos);
                nodePos++;
            }
        }

    }

    //count nodes
    private int getNodesCount() {
        int count = 0;

        for (int i = 0; i < hStr.length(); i++) {
            if (hStr.charAt(i) == '“') {
                count++;
            }
        }
        return count;
    }

    //get position of specified char
    private int[] getPositionsOfChar(char c) {
        int[] positions = new int[nodesCount];
        int index = hStr.indexOf(c);

        for (int i = 0; i < nodesCount; i++) {
            positions[i] = index;
            index = hStr.indexOf(c, index + 1);
        }
        return positions;
    }

    //get all nodenames as array
    private String[] getNodeNames() {
        String[] names = new String[nodesCount];

        for (int i = 0; i < nodesCount; i++) {
            names[i] = hStr.substring(nodeInitPos[i] + 1, nodeFinalPos[i]);
        }
        return names;
    }

    //get current name of node
    private String getName(int pos) {
        return nodeNames[pos];
    }

    private int getOldPosition(String name) {
        int pos = Arrays.asList(allNodeNames).indexOf(name);
        if (name.equals("Unavailable")) {

        }
        return pos;
    }

    //check if node is a child
    private boolean isChild(int pos) {
        int endNode = nodeFinalPos[pos];

        if (hStr.charAt(endNode + 1) == ')') {
            return true;
        } else {
            return false;
        }
    }

    //remove child from main string
    private String removeChild(int pos) {
        String str = hStr;
        String child = "";

        //check if node is unavailable to remove from old names because this name can occur often
        //@@TODO if name occurs more than once create an exception
        int temp = getOldPosition(getName(pos));
        if (allNodeNames[temp].equals("Unavailable")) {
            allNodeNames[temp] = "";
        }

        //build child substring => , \(\“ChildName\”\)
        child = ", \\(\\“" + nodeNames[pos] + "\\”\\)";

        //cut escaping
        child = child.replace("\\", "");

        //System.out.println(child);
        //remove child from string
        str = str.replace(child, "");

        return str;
    }
}
