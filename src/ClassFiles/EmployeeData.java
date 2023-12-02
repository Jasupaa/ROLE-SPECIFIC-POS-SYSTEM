/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ClassFiles;

/**
 *
 * @author John Paul Uy
 */
import javafx.beans.property.*;

public class EmployeeData {
    private final IntegerProperty empID;
    private final StringProperty empFirstName;
    private final StringProperty empLastName;
    private final StringProperty empEmail;
    private final IntegerProperty empContact;
    private final StringProperty empRole;

    public EmployeeData(int empID, String empFirstName, String empLastName, String empEmail, int empContact, String empRole) {
        this.empID = new SimpleIntegerProperty(empID);
        this.empFirstName = new SimpleStringProperty(empFirstName);
        this.empLastName = new SimpleStringProperty(empLastName);
        this.empEmail = new SimpleStringProperty(empEmail);
        this.empContact = new SimpleIntegerProperty(empContact);
        this.empRole = new SimpleStringProperty(empRole);
    }

    public Integer getempID() {
        return empID.get();
    }

    public void setempID(int empid) {
        empID.set(empid);
    }    
    
    public String getempFirstName() {
        return empFirstName.get();
    }

    public void setempFirstName(String firstname) {
        empFirstName.set(firstname);
    }
    public String getempLastName() {
        return empFirstName.get();
    }

    public void setempLastName(String lastname) {
        empFirstName.set(lastname);
    }
    public String getempEmail() {
        return empFirstName.get();
    }

    public void setempEmail(String empemail) {
        empFirstName.set(empemail);
    }
    public Integer getempContact() {
        return empContact.get();
    }

    public void setempContact(int contact) {
        empContact.set(contact);
    }

    public String getempRole() {
        return empRole.get();
    }

    public void setempRole(String quantity) {
        empRole.set(quantity);
    }

    public StringProperty empFirstNameProperty() {
        return   empFirstName;
    }
    
    public StringProperty empLastNameProperty() {
        return   empLastName;
    }
    
    public StringProperty empEmailProperty() {
        return   empEmail;
    }
    
    public IntegerProperty empContactProperty() {
        return empContact;
    }

    public StringProperty empRoleProperty() {
        return empRole;
    }

}


