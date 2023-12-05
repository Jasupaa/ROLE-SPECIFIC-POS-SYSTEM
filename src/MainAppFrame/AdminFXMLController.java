package MainAppFrame;

import Admin.DiscountCRUDController;
import ClassFiles.Discount;
import Login.ControllerInterface;
import Login.LoginTest;
import java.sql.Connection;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import ClassFiles.EmployeeData;
import ClassFiles.Role;
import java.sql.Statement;

public class AdminFXMLController implements Initializable, ControllerInterface {

    double xOffset, yOffset;

    @FXML
    private Button SRhomeBTN;

    @FXML
    private Button MLhomeBTN;

    @FXML
    private Button EMPhomeBTN;

    @FXML
    private Button DChomeBTN;

    @FXML
    private Button AddCoup;

    @FXML
    private AnchorPane AdminPane;

    @FXML
    private ImageView CloseButton;

    @FXML
    private Label dateLbl;

    @FXML
    private AnchorPane home;

    @FXML
    private Button homeBTN;

    @FXML
    private AnchorPane disCoup;

    @FXML
    private Button disCoupBTN;

    @FXML
    private AnchorPane empDetails;

    @FXML
    private Button empDetailsBTN;

    @FXML
    private AnchorPane invManage;

    @FXML
    private Button invManageBTN;

    @FXML
    private AnchorPane salesRep;

    @FXML
    private Button salesRepBTN;
    
    @FXML
    private Button archiBTN;
    
    @FXML
    private AnchorPane archives;
    
    @FXML
    private Button editEmp;
    
    @FXML
    private TableView<EmployeeData> employeeTable1;

     @FXML
    private TableColumn<EmployeeData, Integer> emp_id1;

    @FXML
    private TableColumn<EmployeeData, String> empFirstName1;
    
    @FXML
    private TableColumn<EmployeeData, String> empLastName1;

    @FXML
    private TableColumn<EmployeeData, String> empEmail1;

    @FXML
    private TableColumn<EmployeeData, Integer> empContact1;

    @FXML
    private TableColumn<EmployeeData, String> emp_role1;
    @FXML
    private TableColumn<EmployeeData, Integer> pin_code1;
    @FXML
    private TableColumn<EmployeeData, String> empStatus1;

    @FXML
    private Label timeLbl;

    @FXML
    private Stage stage;

    @FXML
    private Button milkteaIMGVW;

    @FXML
    private Button milkteaBTN;

    @FXML
    private TableView<Discount> discountTableView;

    @FXML
    private TableColumn<Discount, String> codeColumn;

    @FXML
    private TableColumn<Discount, Double> discountColumn;

    @FXML
    private TableColumn<Discount, String> descriptionColumn;

    @FXML
    private TableColumn<Discount, LocalDate> createdAtColumn;

    @FXML
    private TableColumn<Discount, LocalDate> validAtColumn;

    @FXML
    private TableColumn<Discount, Integer> UsageColumn;

    @FXML
    private ImageView salesIV;

    @FXML
    private ImageView empIV;

    @FXML
    private ImageView disIV;

    @FXML
    private ImageView menuIV;

    private ObservableList<Discount> discounts;

    @FXML
    private Button DelBtn;

    @FXML
    private Button EditBtn;

    @FXML
    private Label Customer;

    @FXML
    private Label Daily;

    @FXML
    private Label Products;
    @FXML
    private Button addEmployee;
    
    @FXML
    private AnchorPane editEmployee;

    @FXML
    private Button editButton;

    @FXML
    private Button removeEmployee;
    
    @FXML
    private TextField FirstNameTxtLbl;
    @FXML
    private TextField LastNameTxtLbl;
    @FXML
    private TextField ContactTxtLbl;
    @FXML
    private TextField emailTxtLbl;
    
    @FXML
    private TableView<EmployeeData> employeeTable;

    @FXML
    private TableColumn<EmployeeData, Integer> emp_id;

    @FXML
    private TableColumn<EmployeeData, String> empFirstName;
    
    @FXML
    private TableColumn<EmployeeData, String> empLastName;

    @FXML
    private TableColumn<EmployeeData, String> empEmail;

    @FXML
    private TableColumn<EmployeeData, Integer> empContact;

    @FXML
    private TableColumn<EmployeeData, String> emp_role;
    @FXML
    private TableColumn<EmployeeData, Integer> pin_code;
    @FXML
    private TableColumn<EmployeeData, String> empStatus;
    @FXML
    private Button removeEmp;
    

    @FXML
    private Label topOneLBL;

    @FXML
    private Label topOneQTY;

    @FXML
    private Label topThreeLBL;

    @FXML
    private Label topThreeQTY;

    @FXML
    private Label topTwoLBL;

    @FXML
    private Label topTwoQTY;

    @FXML
    private Label homeNOC;

    @FXML
    private Label homeREV;

    @FXML
    private Label homeTO;

    @FXML
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private volatile boolean stop = false;

    private void DateLabel() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("E dd MMM yyyy", Locale.ENGLISH);
        String formattedDate = currentDate.format(DateFormat);
        dateLbl.setText(formattedDate);
    }

    private void Timenow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while (!stop) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    timeLbl.setText(timenow); // This is the label
                });
            }
        });

        thread.start();
    }

    @FXML
    private void handleMouseEnter(MouseEvent event) {
        // Handle mouse enter (hover in)
        salesIV.setVisible(false);
    }

    @FXML
    private void handleMouseExit(MouseEvent event) {
        // Handle mouse exit (hover out)
        salesIV.setVisible(true);
    }

    @FXML
    private void handleMouseEnter1(MouseEvent event) {
        // Handle mouse enter (hover in)
        menuIV.setVisible(false);
    }

    @FXML
    private void handleMouseExit1(MouseEvent event) {
        // Handle mouse exit (hover out)
        menuIV.setVisible(true);
    }

    @FXML
    private void handleMouseEnter2(MouseEvent event) {
        // Handle mouse enter (hover in)
        empIV.setVisible(false);
    }

    @FXML
    private void handleMouseExit2(MouseEvent event) {
        // Handle mouse exit (hover out)
        empIV.setVisible(true);
    }

    @FXML
    private void handleMouseEnter3(MouseEvent event) {
        // Handle mouse enter (hover in)
        disIV.setVisible(false);
    }

    @FXML
    private void handleMouseExit3(MouseEvent event) {
        // Handle mouse exit (hover out)
        disIV.setVisible(true);
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    employeeDataList = FXCollections.observableArrayList();

   
    if (employeeDataList.isEmpty()) {
        emp_id.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        empFirstName.setCellValueFactory(new PropertyValueFactory<>("empFirstName"));
        empLastName.setCellValueFactory(new PropertyValueFactory<>("empLastName"));
        empStatus.setCellValueFactory(new PropertyValueFactory<>("empStatus"));
        empContact.setCellValueFactory(new PropertyValueFactory<>("empContact"));
        empEmail.setCellValueFactory(new PropertyValueFactory<>("empEmail"));
        emp_role.setCellValueFactory(new PropertyValueFactory<>("emp_role"));
        pin_code.setCellValueFactory(new PropertyValueFactory<>("pin_code"));
        
        fetchExistingDataFromDatabase();

        // Update the TableView
        updateEmployeeTable();
        employeeTable.setItems(employeeDataList);
        employeeTable1.setItems(employeeDataList);

    }
        updateTotalDailySalesLabel();
        updateTotalDailyProductsSoldLabel();
        updateTotalDailyCustomersLabel();
        updateTotalDailyCustomersHome();
        updateTotalDailyProductsSoldHome();
        updateTotalDailySalesHome();

        discounts = FXCollections.observableArrayList();
        setupDiscountColumns();
        loadDataFromDatabase();

        milkteaBTN.setStyle("-fx-background-color: #111315; -fx-background-radius: 20px");
        DateLabel();
        Timenow();

        CloseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                try {
                    Stage stage = (Stage) CloseButton.getScene().getWindow();
                    stage.close();

                    // Open the login window.
                    LoginTest loginTest = new LoginTest();
                    loginTest.start(new Stage());
                } catch (Exception ex) {
                    Logger.getLogger(AdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /* @RODEL ito yung action event para sa mga buttons na pa-square */
    @FXML
    private void handleMilkteaButtonClick(ActionEvent event) {
        try {
            // Load the MilkteaCRUDFXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDsFXML/MilkteaCRUD.fxml"));
            Parent root = loader.load();

            // Create a new stage for the MilkteaCRUDFXML
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    @FXML
    private void handleFruitDrinkButtonClick(ActionEvent event) {
        try {
            // Load the MilkteaCRUDFXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDsFXML/FruitDrinkCRUD.fxml"));
            Parent root = loader.load();

            // Create a new stage for the MilkteaCRUDFXML
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    @FXML
    private void handleCoffeeButtonClick(ActionEvent event) {
        try {
            // Load the MilkteaCRUDFXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDsFXML/CoffeeCRUD.fxml"));
            Parent root = loader.load();

            // Create a new stage for the MilkteaCRUDFXML
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    @FXML
    private void handleRiceMealsButtonClick(ActionEvent event) {
        try {
            // Load the MilkteaCRUDFXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDsFXML/RiceMealsCRUD.fxml"));
            Parent root = loader.load();

            // Create a new stage for the MilkteaCRUDFXML
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    @FXML
    private void handleSnacksButtonClick(ActionEvent event) {
        try {
            // Load the MilkteaCRUDFXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDsFXML/SnacksCRUD.fxml"));
            Parent root = loader.load();

            // Create a new stage for the MilkteaCRUDFXML
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    @FXML
    private void handleExtrasButtonClick(ActionEvent event) {
        try {
            // Load the MilkteaCRUDFXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDsFXML/ExtrasCRUD.fxml"));
            Parent root = loader.load();

            // Create a new stage for the MilkteaCRUDFXML
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    @FXML
    private void handleFrappeButtonClick(ActionEvent event) {
        try {
            // Load the MilkteaCRUDFXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CRUDsFXML/FrappeCRUD.fxml"));
            Parent root = loader.load();

            // Create a new stage for the MilkteaCRUDFXML
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }
     public void updateEmployeeTable() {
    for (EmployeeData employee : employeeDataList) {
        System.out.println("Employee ID: " + employee.getEmp_id() + ", Status: " + employee.getStatus());
    }
    employeeTable.setItems(employeeDataList);
    employeeTable.refresh();
}

    public void addEmployee(EmployeeData employee) {
    employeeTable.getItems().add(employee);
    System.out.println("Adding employee: " + employee.getEmpFirstName());
    employeeDataList.add(employee);
    employeeTable.setItems(employeeDataList);
    }


    @FXML
    private void openAddEmployeeDialog() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
        Parent root = loader.load();
        AddEmployeeFXMLController addEmployeeController = loader.getController();
        addEmployeeController.setParentController(this);
        Stage stage = new Stage();
        stage.setTitle("Add Employee");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    private ObservableList<EmployeeData> employeeDataList = FXCollections.observableArrayList();
    
    private void fetchExistingDataFromDatabase() {
    try (Connection connection = database.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT * FROM employees")) {

        while (resultSet.next()) {
            EmployeeData employee = new EmployeeData(
                    resultSet.getInt("emp_id"),
                    resultSet.getInt("pin_code"),
                    resultSet.getString("empFirstName"),
                    resultSet.getString("empLastName"),
                    resultSet.getString("empEmail"),
                    resultSet.getLong("empContact"), 
                    resultSet.getString("emp_role")
            );

            
            employee.setEmpStatus(resultSet.getString("empStatus"));
            System.out.println("Employee ID: " + employee.getEmp_id() + ", Status: " + employee.getStatus());
            employeeDataList.add(employee);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        
    }}
    
   @FXML
    private void removeEmployee() {
        // Get the selected employee from the employeeTable in EmployeeDetails
        EmployeeData selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            // Remove the selected employee from the shared data list
            employeeDataList.remove(selectedEmployee);

            // Add the selected employee to the employeeTable1 in Archives
            employeeTable1.getItems().add(selectedEmployee);
        }
    }




    @FXML
    private void handleDiscountButtonClick(ActionEvent event) {
        try {
            // Load the DiscountCrud.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/DiscountCrud.fxml"));
            Parent root = loader.load();

            // Create a new stage for the DiscountCrud.fxml
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Access the DiscountCRUDController and set the reference to AdminFXMLController
            DiscountCRUDController discountCrudController = loader.getController();
            discountCrudController.setAdminController(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditButtonAction(ActionEvent event) {
        // Get the selected discount from the TableView
        Discount selectedDiscount = discountTableView.getSelectionModel().getSelectedItem();

        if (selectedDiscount != null) {
            try {
                // Load the DiscountCrud.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/DiscountEdit.fxml"));
                Parent root = loader.load();

                // Access the DiscountCrudController to set the fields
                DiscountCRUDController discountCrudController = loader.getController();
                discountCrudController.setAdminController(this);

                // Pass the selected discount to the DiscountCrudController
                discountCrudController.setDiscount(selectedDiscount);

                // Create a new stage for the DiscountCrud.fxml
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception (e.g., show an error dialog)
            }
        } else {
            // Prompt the user to select a discount
            // You can show an alert or any other form of user notification
            System.out.println("Please select a discount to edit.");
        }
    }

    private void loadDataFromDatabase() {
        List<Discount> discountList = fetchDiscountsFromDatabase();
        discounts.clear();
        discounts.addAll(discountList);
        discountTableView.setItems(discounts);
    }

    private ObservableList<Discount> fetchDiscountsFromDatabase() {
        ObservableList<Discount> discounts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM discount WHERE Date_valid >= CURRENT_DATE AND limit_usage > 0";

        try (Connection connection = database.getConnection(); // Assuming your database class is named 'database'
                 PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String discCode = resultSet.getString("disc_code");
                double discValue = resultSet.getDouble("disc_value");
                String descCoup = resultSet.getString("Desc_coup");
                java.sql.Date dateCreatedSql = resultSet.getDate("Date_created");
                LocalDate dateCreated = (dateCreatedSql != null) ? dateCreatedSql.toLocalDate() : null;
                int usageLim = resultSet.getInt("limit_usage");

                // Convert java.sql.Date to LocalDate
                java.sql.Date dateValidSql = resultSet.getDate("Date_valid");
                LocalDate dateValid = (dateValidSql != null) ? dateValidSql.toLocalDate() : null;

                Discount discount = new Discount(id, discCode, discValue, descCoup, dateCreated, dateValid, usageLim);
                discounts.add(discount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discounts;
    }

    private double calculateTotalDailySales() {
        double totalSales = 0.0;

        try (Connection connection = database.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT SUM(total) AS totalSales FROM invoice"); ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                totalSales = resultSet.getDouble("totalSales");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exceptions
        }

        return totalSales;
    }

    private void updateTotalDailySalesLabel() {
        double totalSales = calculateTotalDailySales();
        Daily.setText(String.format("%.2f", totalSales));
    }

    private int calculateTotalDailyProductsSold() {
        int totalProductsSold = 0;

        try (Connection connection = database.getConnection()) {
            String[] tableNames = {"milk_tea", "fruit_drink", "frappe", "coffee", "rice_meal", "snacks", "extras"};

            for (String tableName : tableNames) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT SUM(quantity) AS productsSold FROM " + tableName); ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {
                        totalProductsSold += resultSet.getInt("productsSold");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exceptions
        }

        return totalProductsSold;
    }

    private void updateTotalDailyProductsSoldLabel() {
        int totalProductsSold = calculateTotalDailyProductsSold();
        // Adjust the label name if needed
        Products.setText(String.valueOf(totalProductsSold));
    }

    private int calculateTotalDailyCustomers() {
        int totalCustomers = 0;

        try (Connection connection = database.getConnection()) {
            String[] tableNames = {"milk_tea", "fruit_drink", "frappe", "coffee", "rice_meal", "snacks", "extras"};

            for (String tableName : tableNames) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(DISTINCT customer_id) AS uniqueCustomers FROM " + tableName); ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {
                        totalCustomers += resultSet.getInt("uniqueCustomers");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exceptions
        }

        return totalCustomers;
    }

    private void updateTotalDailyCustomersLabel() {
        int totalCustomers = calculateTotalDailyCustomers();
        Customer.setText(String.valueOf(totalCustomers));
    }

    private void updateTotalDailyCustomersHome() {
        int totalCustomers = calculateTotalDailyCustomers();
        homeNOC.setText(String.valueOf(totalCustomers));
    }

    private void updateTotalDailyProductsSoldHome() {
        int totalProductsSold = calculateTotalDailyProductsSold();
        // Adjust the label name if needed
        homeTO.setText(String.valueOf(totalProductsSold));
    }

    private void updateTotalDailySalesHome() {
        double totalSales = calculateTotalDailySales();
        homeREV.setText(String.format("%.2f", totalSales));
    }

    private void setupDiscountColumns() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("discCode"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discValue"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descCoup"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        validAtColumn.setCellValueFactory(new PropertyValueFactory<>("dateValid"));
        UsageColumn.setCellValueFactory(new PropertyValueFactory<>("usageLim"));

    }

    private void deleteDiscountFromDatabase(String discCode) {
        try (Connection connection = database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM discount WHERE disc_code = ?")) {

            preparedStatement.setString(1, discCode);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Discount successfully deleted from the database.");
            } else {
                System.out.println("Failed to delete discount from the database. The discount with disc_code '" + discCode + "' may not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Discount selectedDiscount = discountTableView.getSelectionModel().getSelectedItem();

        if (selectedDiscount != null) {
            String discCode = selectedDiscount.getDiscCode();
            deleteDiscountFromDatabase(discCode);
            refreshTableView(); // Refresh the table view after deletion
        } else {
            System.out.println("Please select a discount to delete.");
        }
    }

    public void refreshTableView() {
        loadDataFromDatabase();
    }

    private Button lastClickedButton = null;

    @FXML
    public void SwitchForm(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        if (clickedButton == lastClickedButton) {
            // Ignore the click if the same button was clicked twice in a row
            return;
        }

        if (clickedButton == homeBTN) {
            home.setVisible(true);
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(false);
            archives.setVisible(false);

        } else if (clickedButton == SRhomeBTN) {
            home.setVisible(true);
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(false);

        } else if (clickedButton == MLhomeBTN) {
            home.setVisible(true);
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(false);

        } else if (clickedButton == EMPhomeBTN) {
            home.setVisible(true);
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(false);

        } else if (clickedButton == DChomeBTN) {
            home.setVisible(true);
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(false);
            archives.setVisible(false);

        } else if (clickedButton == salesRepBTN) {
            home.setVisible(false);
            salesRep.setVisible(true);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(false);
            archives.setVisible(false);

        } else if (clickedButton == invManageBTN) {
            home.setVisible(false);
            salesRep.setVisible(false);
            invManage.setVisible(true);
            empDetails.setVisible(false);
            disCoup.setVisible(false);
            archives.setVisible(false);

        } else if (clickedButton == empDetailsBTN) {
            home.setVisible(false);
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(true);
            disCoup.setVisible(false);
            archives.setVisible(false);

        } else if (clickedButton == disCoupBTN) {
            home.setVisible(false);
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(true);
            archives.setVisible(false);
            refreshTableView();
        
        } else if (clickedButton == archiBTN) {
            home.setVisible(false);
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(false);
            archives.setVisible(true);
            refreshTableView();
        }

    }}
