/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problema2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Gabi
 */

/*
2. In a company’s hierarchy, some employees manage other employees, who can in turn manage other employees. 
The input format for the hierarchy is (name, sub-hierarchy_1, sub-hierarchy_2, … , sub-hierarchy_n),
with every sub-hierarchy having the same format, recursively. Your task is to write code that parses the hierarchy, 
removes employees that are marked as “Unavailable” along with all the employees they manage and prints the
resulting hierarchy.Input:
(“John”, (“Jasmine”, (“Jay”), (“Unavailable”)), (“Unavailable”, (“Jack”, (“Jeremy”))), (“Johanna”))Output:
(“John”, (“Jasmine”, (“Jay”)), (“Johanna”))
*/
public class Problema2 {

    static Map<Integer, Node> employees = new HashMap<Integer, Node>();
    static Node root;

    private void readDataFromString(String str) {
        List<String> value = new ArrayList<String>();
        Node employee = null;
        String[] v, x;

        v = str.split("\n");
        for (String a : v) {
            x = a.split(" ");
            for (String b : x) {
                value.add(b);
            }
            employee = new Node(value.get(0), value.get(1), value.get(2));

            employees.put(employee.getId(), employee);
            if (employee.getReportToId() == 0) {
                root = employee;
            }
            value.clear();
        }
    }

    //scan whole employee hashMap to form a list of subordinates for the given id
    private List<Node> getSubordinatesById(int rid) {
        List<Node> subordinates = new ArrayList<Node>();
        for (Node e : employees.values()) {
            if (e.getReportToId() == rid) {
                subordinates.add(e);
            }
        }
        return subordinates;
    }

    //build tree recursively
    private void buildHierarchyTree(Node localRoot) {
        Node employee = localRoot;
        List<Node> subordinates = getSubordinatesById(employee.getId());
        employee.setSubordinates(subordinates);
        if (subordinates.size() == 0) {
            return;
        }

        for (Node e : subordinates) {
            buildHierarchyTree(e);
        }
    }

    //print tree recursively
    private void printHierarchyTree(Node localRoot, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
        System.out.println(localRoot.getName());

        List<Node> subordinates = localRoot.getSubordinates();
        System.out.print(" ");
        for (Node e : subordinates) {
            printHierarchyTree(e, level + 1);
        }
    }

    List<Node> sub = new ArrayList<Node>();

    //get children recursively
    public List<Node> getNodeById(int id) {
        Node item = null;
        int newId = 0;

        for (Node e : employees.values()) {
            if (e.getReportToId() == id) {
                item = e;
                sub.add(item);
            }
        }
        newId = item.getId();

        if (exist(newId)) {
            getNodeById(newId);
        }

        return sub;
    }

    public boolean exist(int newId) {
        for (Node node : employees.values()) {
            if (newId == node.getReportToId()) {
                return true;
            }
        }
        return false;
    }

    public void remove(String name) {
        List<Node> ch = new ArrayList<Node>();
        List<Node> parent = new ArrayList<Node>();
        int idChild = 0;
        int newId = 0;

        for (Node e : employees.values()) {
            if (e.getName().equals(name)) {
                idChild = e.getId();// Unavailable
                parent.add(e);
            }
        }
        for (Node child : getNodeById(idChild)) {
            employees.remove(child.getId(), child);
        }
        for (Node p : parent) {
            employees.remove(p.getId(), p);
        }
    }

    public int getSizeOfEmp() {
        int size = employees.size();
        return size;
    }

    String str = "";
    String lastStr = "";
    int lastLevel = 0;
    int lastSize = 0;
    int c = 0;

    public String output(Node root, int lvl) {
        c++;
        List<Node> subordinates = root.getSubordinates();
        if (lastSize == 0 && lvl == lastLevel && lvl != 0) {
            str = str.substring(0, str.length() - 3) + ", ";
        }
        str += "(“" + root.getName() + "”";
        if (subordinates.isEmpty()) {
            int count = lvl;
            while (count != 0) {
                str += ")";
                count--;
            }
            if (c == getSizeOfEmp()) {
                str += ")";
            } else {
                str += ", ";
            }
        } else {
            str += ", ";
        }
        lastLevel = lvl;
        lastSize = subordinates.size();
        for (Node e : subordinates) {
            output(e, lvl + 1);
        }
        return str;
    }

    public static void main(String[] args) throws IOException {

        Problema2 t2 = new Problema2();
        String e = "(“John”, (“Jasmine”, (“Jay”), (“Unavailable”)), (“Unavailable”, (“Jack”, (“Jeremy”))), (“Johanna”))";
        //(“John”, (“Jasmine”, (“Jay”)), (“Johanna”))

        String finalString;

        Classify convert = new Classify(e);
        finalString = convert.getFinalString();
        //System.out.println(finalString);

        t2.readDataFromString(finalString);
        System.out.println("Input: " + e);
        t2.buildHierarchyTree(root);
        //t2.printHierarchyTree(root, 0);
        
        
        t2.remove("Unavailable");
        t2.buildHierarchyTree(root);
        //t2.printHierarchyTree(root, 0);

        System.out.println("Output = " + t2.output(root, 0));
    }
}
