/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

/**
 *
 * @author Gwyneth Uy
 */
public class ArchiveCardData {

    private String customerID;

    // Constructor that takes a String argument
    public ArchiveCardData(String customerID) {
        this.customerID = customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerID() {
        return customerID;
    }

}

