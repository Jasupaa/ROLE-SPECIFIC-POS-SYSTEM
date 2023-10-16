module com.mycompany.role_specific_pos_system1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.role_specific_pos_system1 to javafx.fxml;
    exports com.mycompany.role_specific_pos_system1;
}
