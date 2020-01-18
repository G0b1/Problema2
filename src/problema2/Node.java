/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problema2;

import java.util.List;

/**
 *
 * @author Gabi
 */
class Node {

    int empId;
    String name;
    int reportToId;
    List<Node> subordinates;

    public Node(String id, String empName, String rid) {
        try {
            this.empId = Integer.parseInt(id);
            this.name = empName;
            this.reportToId = Integer.parseInt(rid);
        } catch (Exception e) {
            System.err.println("Exception creating employee:" + e);
        }
    }

    List<Node> getSubordinates() {
        return subordinates;
    }

    void setSubordinates(List<Node> subordinates) {
        this.subordinates = subordinates;
    }

    int getId() {
        return empId;
    }

    void setId(int id) {
        this.empId = id;
    }

    String getName() {
        return name;
    }

    void setName(String n) {
        name = n;
    }

    int getReportToId() {
        return reportToId;
    }
}
