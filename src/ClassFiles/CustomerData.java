/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

/**
 *
 * @author Gwyneth Uy
 */
public class CustomerData {

    private String customerID;
    private String cashierHandler;

    // Constructor that takes a String argument
    public CustomerData(String customerID, String cashierHandler) {
        this.customerID = customerID;
        this.cashierHandler = cashierHandler;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCashierHandler() {
        return cashierHandler;
    }

    public void setCashierHandler(String cashierHandler) {
        this.cashierHandler = cashierHandler;
    }

}
