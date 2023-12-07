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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleLongProperty;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class EmployeeData {

    private final SimpleIntegerProperty emp_id;
    private final SimpleStringProperty empFirstName;
    private final SimpleStringProperty empLastName;
    private final SimpleStringProperty empEmail;
    private final SimpleLongProperty empContact;
    private final SimpleStringProperty emp_role;
    public final SimpleIntegerProperty pin_code;
    private SimpleStringProperty empStatus;

    // Constructor
    public EmployeeData(int emp_id, String empFirstName, String empLastName, String empEmail, long empContact, String emp_role, int pin_code, String empStatus) {
        this.emp_id = new SimpleIntegerProperty(emp_id);
        this.empFirstName = new SimpleStringProperty(empFirstName);
        this.empLastName = new SimpleStringProperty(empLastName);
        this.empEmail = new SimpleStringProperty(empEmail);
        this.empContact = new SimpleLongProperty(empContact);
        this.emp_role = new SimpleStringProperty(emp_role);
        this.pin_code = new SimpleIntegerProperty(pin_code);
        this.empStatus = new SimpleStringProperty(empStatus);
    }

    public int getEmp_id() {
        return emp_id.get();
    }

    public String getEmpFirstName() {
        return empFirstName.get();
    }

    public String getEmpLastName() {
        return empLastName.get();
    }

    public String getEmpEmail() {
        return empEmail.get();
    }

    public long getEmpContact() {
        return empContact.get();
    }

    public String getEmp_role() {
        return emp_role.get();
    }

    public final SimpleIntegerProperty pin_codeProperty() {
        return this.pin_code;
    }

    public final int getPin_code() {
        return this.pin_codeProperty().get();
    }

    public final void setPin_code(final int pin_code) {
        this.pin_codeProperty().set(pin_code);
    }

    public String getEmpStatus() {
        return empStatus.get();
    }

    public final SimpleStringProperty statusProperty() {
        return this.empStatus;
    }

    public final String getStatus() {
        return this.statusProperty().get();
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus.set(empStatus);
    }

    private static final String INSERT_EMPLOYEES_SQL
            = "INSERT INTO `employees` (`empFirstName`, `empContact`, `empEmail`, `emp_role`, `pin_code`, `empLastName`, `empStatus`) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public PreparedStatement getInsertStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_EMPLOYEES_SQL);
        statement.setString(1, empFirstName.get());
        statement.setLong(2, empContact.get());
        statement.setString(3, empEmail.get());
        statement.setString(4, emp_role.get());
        statement.setInt(5, pin_code.get());
        statement.setString(6, empLastName.get());
        statement.setString(7, empStatus.get());

        return statement;
    }

    public EmployeeData(int emp_id, int pin_code, String empFirstName, String empLastName, String empEmail, long empContact, String emp_role) {
        this.emp_id = new SimpleIntegerProperty(emp_id);
        this.empFirstName = new SimpleStringProperty(empFirstName);
        this.empLastName = new SimpleStringProperty(empLastName);
        this.empEmail = new SimpleStringProperty(empEmail);
        this.empContact = new SimpleLongProperty(empContact);
        this.emp_role = new SimpleStringProperty(emp_role);
        this.pin_code = new SimpleIntegerProperty(pin_code);
        this.empStatus = new SimpleStringProperty("Active");
    }
}
