package MainAppFrame;

import Admin.DiscountCRUDController;
import Admin.EditEmployeeFXMLController;
import ClassFiles.Discount;
import ClassFiles.EmployeeData;
import Login.ControllerInterface;
import Login.LoginTest;
import java.sql.Connection;
import java.io.IOException;
import java.sql.PreparedStatement;
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
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.net.URL;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import ClassFiles.EmployeeData;
import ClassFiles.Role;
import Databases.CRUDDatabase;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableRow;

public class AdminFXMLController implements Initializable, ControllerInterface {

    double xOffset, yOffset;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private LineChart<String, Number> revenueLC;

    @FXML
    private AreaChart<String, Number> analysisBC;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label topEmpLBL;

    @FXML
    private Label totalOrdsHandledLBL;

    @FXML
    private BarChart<String, Number> weeklySales;

    @FXML
    private Button mtBTN;

    @FXML
    private Button ftBTN;

    @FXML
    private Button frBTN;

    @FXML
    private Button cfBTN;

    @FXML
    private Button rmBTN;

    @FXML
    private Button snBTN;

    @FXML
    private Button exBTN;

    @FXML
    private Pane milkteaPANE;

    @FXML
    private Pane fruitdrinkPANE;

    @FXML
    private Pane frappePANE;

    @FXML
    private Pane coffeePANE;

    @FXML
    private Pane ricemealPANE;

    @FXML
    private Pane snacksPANE;

    @FXML
    private Pane extrasPANE;

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
    private Label timeLbl;

    @FXML
    private Stage stage;

    @FXML
    private Button milkteaIMGVW;

    @FXML
    private Button milkteaBTN;

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
    private ComboBox<String> sortFilterEmp;

    @FXML
    private Button removeEmp;

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
    private PieChart milkTeaPieChart;

    @FXML
    private PieChart snacksPieChart;

    @FXML
    private PieChart riceMealPieChart;

    @FXML
    private PieChart fruitDrinkPieChart;

    @FXML
    private PieChart frappePieChart;

    @FXML
    private PieChart extrasPieChart;

    @FXML
    private PieChart coffeePieChart;
    
      @FXML
    private Button DiscHistory;
    
     private boolean isHistoryMode = false;

    private ObservableList<EmployeeData> employeeDataList;
    private FilteredList<EmployeeData> filteredEmployeeData;

    public void disableEmployeeCell(EmployeeData employeeData) {
        int index = employeeDataList.indexOf(employeeData);
        if (index >= 0) {
            // Set the employee status to "Terminated"
            employeeData.setEmpStatus("Terminated");

            // Update the order and status in the database
            updateEmployeeOrderAndStatus();

            // Refresh the TableView
            employeeTable.refresh();
        }
    }

    private void updateEmployeeOrderAndStatus() {
        try {
            Connection connection = CRUDDatabase.getConnection(); // Assuming your CRUDDatabase class provides the getConnection method

            // Iterate through the list and update the status in the database
            String updateQuery = "UPDATE employees SET empStatus=? WHERE emp_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                for (EmployeeData employeeData : employeeDataList) {
                    preparedStatement.setString(1, "Terminated".equals(employeeData.getEmpStatus()) ? "Terminated" : "Active");
                    preparedStatement.setInt(2, employeeData.getEmp_id());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<EmployeeData> fetchExistingDataFromDatabase() {
        ObservableList<EmployeeData> employeeDataList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM employees";

        try (Connection connection = CRUDDatabase.getConnection(); // Assuming your CRUDDatabase class provides the getConnection method
                 PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            // Clear the list before populating it
            employeeDataList.clear();

            while (resultSet.next()) {
                int empId = resultSet.getInt("emp_id");
                int pinCode = resultSet.getInt("pin_code");
                String empFirstName = resultSet.getString("empFirstName");
                String empLastName = resultSet.getString("empLastName");
                String empEmail = resultSet.getString("empEmail");
                long empContact = resultSet.getLong("empContact");
                String emp_role = resultSet.getString("emp_role");
                String empStatus = resultSet.getString("empStatus");

                EmployeeData employee = new EmployeeData(empId, empFirstName, empLastName, empEmail, empContact, emp_role, pinCode, empStatus);
                employeeDataList.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeDataList;
    }

    public void setUpEmployeeTable() {
        emp_id.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        empFirstName.setCellValueFactory(new PropertyValueFactory<>("empFirstName"));
        empLastName.setCellValueFactory(new PropertyValueFactory<>("empLastName"));
        empStatus.setCellValueFactory(new PropertyValueFactory<>("empStatus"));
        empContact.setCellValueFactory(new PropertyValueFactory<>("empContact"));
        empEmail.setCellValueFactory(new PropertyValueFactory<>("empEmail"));
        emp_role.setCellValueFactory(new PropertyValueFactory<>("emp_role"));
        pin_code.setCellValueFactory(new PropertyValueFactory<>("pin_code"));

        applyCellStyling(employeeDataList);
    }

    public void loadEmpDataFromDatabase() {
        List<EmployeeData> employeeList = fetchExistingDataFromDatabase();
        employeeDataList.clear();
        employeeDataList.addAll(employeeList);

        // Sort the list by order_in_list, assuming your EmployeeData class has a method getOrderInList()
        employeeDataList.sort(Comparator.comparingInt(EmployeeData::getOrderInList));

        // Apply the list to the TableView
        employeeTable.setItems(employeeDataList);

        // Apply styling to cells
        applyCellStyling(employeeDataList);
    }

    private void applyCellStyling(ObservableList<EmployeeData> employeeDataList) {
        employeeTable.setRowFactory(tv -> new TableRow<EmployeeData>() {
            @Override
            protected void updateItem(EmployeeData item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && "Terminated".equals(item.getEmpStatus())) {
                    setStyle("-fx-background-color: #E0E0E0;"); // Use the desired grayish color
                } else {
                    setStyle(""); // Reset the style for non-terminated rows
                }
            }
        });
    }

    ////////////////////////////////// ADD
    @FXML
    private void openAddEmployeeDialog(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEmployee.fxml"));
            Parent root = loader.load();
            AddEmployeeFXMLController addEmployeeController = loader.getController();
            addEmployeeController.setAdminController(this);
            Stage stage = new Stage();
            stage.setTitle("Add Employee");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(EmployeeData employee) {
        // Assuming employeeTable is your TableView and employeeDataList is your underlying list
        if (!employeeDataList.contains(employee)) {
            employeeTable.getItems().add(employee);

        }
    }

    //////////////////////////////// EDIT
    @FXML
    void handleEditEmployeeButton(ActionEvent event) throws SQLException {
        EmployeeData selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/EditEmployee.fxml"));
                Parent root = loader.load();

                EditEmployeeFXMLController editEmployeeController = loader.getController();
                editEmployeeController.setParentController(this);
                editEmployeeController.setEmployeeData(selectedEmployee);

                // Set the connection before showing the stage
                editEmployeeController.setConnection(CRUDDatabase.getConnection());

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select an employee to edit.");
        }

    }

    private Connection connection; // Initialize this somewhere, maybe in your constructor

    // Other methods...
    public void UpdateEmployee(Connection connection, EmployeeData employeeData) throws SQLException {
        String updateQuery = "UPDATE employees SET "
                + "empFirstName=?, empLastName=?, empContact=?, empEmail=?, emp_role=?, empStatus=? "
                + "WHERE emp_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, employeeData.getEmpFirstName());
            preparedStatement.setString(2, employeeData.getEmpLastName());
            preparedStatement.setLong(3, employeeData.getEmpContact());
            preparedStatement.setString(4, employeeData.getEmpEmail());
            preparedStatement.setString(5, employeeData.getEmp_role());
            preparedStatement.setString(6, employeeData.getEmpStatus());
            preparedStatement.setInt(7, employeeData.getEmp_id());

            preparedStatement.executeUpdate();
        }
    }

    public void updateEmployeeTable() {
        for (EmployeeData employee : employeeDataList) {
            System.out.println("Employee ID: " + employee.getEmp_id() + ", Status: " + employee.getStatus());
        }
        employeeTable.setItems(employeeDataList);
        employeeTable.refresh();
    }

// Sample updateEmployee method using PreparedStatement
    public void updateEmployee(Connection connection, EmployeeData employeeData) throws SQLException {
        String updateQuery = "UPDATE employees SET empFirstName=?, empLastName=?, empContact=?, empEmail=?, emp_role=?, empStatus=? WHERE emp_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, employeeData.getEmpFirstName());
            preparedStatement.setString(2, employeeData.getEmpLastName());
            preparedStatement.setLong(3, employeeData.getEmpContact());
            preparedStatement.setString(4, employeeData.getEmpEmail());
            preparedStatement.setString(5, employeeData.getEmp_role());
            preparedStatement.setString(6, employeeData.getEmpStatus());
            preparedStatement.setInt(7, employeeData.getEmp_id());

            preparedStatement.executeUpdate();
        }
    }

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

    private void updateTopProductsLabels() {
        try (Connection connection = database.getConnection()) {
            String[] tableNames = {"milk_tea", "fruit_drink", "frappe", "coffee", "rice_meal", "snacks", "extras"};

            Map<String, Integer> productQuantities = new HashMap<>();

            for (String tableName : tableNames) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT item_name, SUM(quantity) AS totalQuantity FROM " + tableName + " GROUP BY item_name ORDER BY totalQuantity DESC LIMIT 3"); ResultSet resultSet = statement.executeQuery()) {

                    while (resultSet.next()) {
                        String itemName = resultSet.getString("item_name");
                        int quantity = resultSet.getInt("totalQuantity");
                        productQuantities.put(itemName, quantity);
                    }
                }
            }

            List<Map.Entry<String, Integer>> sortedProducts = productQuantities.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.toList());

            // Assuming you have labels named topOneLBL, topOneQTY, topTwoLBL, topTwoQTY, topThreeLBL, topThreeQTY
            for (int rank = 1; rank <= 3 && rank <= sortedProducts.size(); rank++) {
                Map.Entry<String, Integer> entry = sortedProducts.get(rank - 1);
                String itemName = entry.getKey();
                int quantity = entry.getValue();

                // Update labels based on the rank
                switch (rank) {
                    case 1 ->
                        updateLabel(topOneLBL, topOneQTY, itemName, quantity);
                    case 2 ->
                        updateLabel(topTwoLBL, topTwoQTY, itemName, quantity);
                    case 3 ->
                        updateLabel(topThreeLBL, topThreeQTY, itemName, quantity);
                    // Add more cases if needed
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exceptions
        }
    }

    private void updateLabel(Label nameLabel, Label qtyLabel, String itemName, int quantity) {
        // Update the labels as needed
        nameLabel.setText(itemName);
        String quantityText = "Quantity: " + quantity;
        qtyLabel.setText(quantityText);
    }

    private void initializeChart() {
        // Clear existing data
        revenueLC.getData().clear();

        // Query the database and update the chart
        updateChart();
    }

    private void updateChart() {
        // Get daily revenue data from the database for the 7 highest revenue dates
        try (Connection connection = database.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT DATE(date_time) AS day, SUM(total) AS daily_sum FROM invoice_archive GROUP BY day ORDER BY day DESC LIMIT 7"); ResultSet resultSet = statement.executeQuery()) {

            XYChart.Series<String, Number> series = new XYChart.Series<>();

            while (resultSet.next()) {
                String day = resultSet.getString("day");
                double dailySum = resultSet.getDouble("daily_sum");

                series.getData().add(new XYChart.Data<>(day, dailySum));
            }

            // Add the series to the chart
            revenueLC.getData().clear();
            revenueLC.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your needs
        }
    }

    private List<String> getLast7Days(List<String> existingDates) {
        List<String> last7Days = new ArrayList<>();

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Iterate from currentDate to currentDate - 6 days
        for (int i = 6; i >= 0; i--) {
            LocalDate date = currentDate.minusDays(i);
            last7Days.add(date.toString());
        }

        // Filter out dates that are not in the existingDates list
        last7Days.removeIf(date -> !existingDates.contains(date));

        return last7Days;
    }

    private void updateBarChart() {
        // Get aggregated daily quantity and customer data from all seven tables for the latest 3 dates
        try (Connection connection = database.getConnection()) {
            // Create series for the aggregated quantity and total customers
            XYChart.Series<String, Number> quantitySeries = new XYChart.Series<>();
            XYChart.Series<String, Number> customerSeries = new XYChart.Series<>();

            List<String> dates = new ArrayList<>();

            try (PreparedStatement dateStatement = connection.prepareStatement("SELECT DISTINCT DATE(date_time) AS day "
                    + "FROM (SELECT customer_id, quantity, date_time FROM milk_tea UNION SELECT customer_id, quantity, date_time FROM fruit_drink UNION SELECT customer_id, quantity, date_time FROM frappe UNION "
                    + "SELECT customer_id, quantity, date_time FROM coffee UNION SELECT customer_id, quantity, date_time FROM rice_meal UNION SELECT customer_id, quantity, date_time FROM snacks UNION SELECT customer_id, quantity, date_time FROM extras) AS combined_tables "
                    + "ORDER BY day DESC LIMIT 3"); ResultSet dateResultSet = dateStatement.executeQuery()) {

                while (dateResultSet.next()) {
                    String day = dateResultSet.getString("day");
                    dates.add(day);
                }
            }

            // Fetch aggregated quantity and customer data for the latest 3 distinct dates
            try (PreparedStatement statement = connection.prepareStatement("SELECT DATE(date_time) AS day, "
                    + "SUM(quantity) AS aggregated_quantity, "
                    + "COUNT(DISTINCT customer_id) AS total_customers "
                    + "FROM (SELECT customer_id, quantity, date_time FROM milk_tea UNION SELECT customer_id, quantity, date_time FROM fruit_drink UNION SELECT customer_id, quantity, date_time FROM frappe UNION "
                    + "SELECT customer_id, quantity, date_time FROM coffee UNION SELECT customer_id, quantity, date_time FROM rice_meal UNION SELECT customer_id, quantity, date_time FROM snacks UNION SELECT customer_id, quantity, date_time FROM extras) AS combined_tables "
                    + "WHERE DATE(date_time) IN (?, ?, ?) GROUP BY day ORDER BY day DESC")) {

                for (String date : dates) {
                    statement.setString(1, date);
                    statement.setString(2, date);
                    statement.setString(3, date);

                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        String day = resultSet.getString("day");
                        double aggregatedQuantity = resultSet.getDouble("aggregated_quantity");
                        double totalCustomers = resultSet.getDouble("total_customers");

                        // Add data to the series
                        quantitySeries.getData().add(new XYChart.Data<>(day + " (Qty)", aggregatedQuantity));
                        customerSeries.getData().add(new XYChart.Data<>(day + " (Customers)", totalCustomers));
                    }
                }
            }

            // Add the series to the chart
            analysisBC.getData().clear();
            analysisBC.getData().addAll(quantitySeries, customerSeries);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your needs
        }
    }

    private void reorderSeries(XYChart.Series<String, Number> series, List<String> dates) {
        List<XYChart.Data<String, Number>> sortedData = new ArrayList<>();

        for (String date : dates) {
            // Find the data with the matching date
            Optional<XYChart.Data<String, Number>> matchingData = series.getData().stream()
                    .filter(data -> data.getXValue().startsWith(date))
                    .findFirst();

            matchingData.ifPresent(sortedData::add);
        }

        // Clear the original data and add the sorted data
        series.getData().clear();
        series.getData().addAll(sortedData);
    }

    private Map<String, Integer> calculateTotalQuantityByItem(String tableName) {
        Map<String, Integer> totalQuantityByItem = new HashMap<>();

        try (Connection connection = database.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT item_name, SUM(quantity) AS totalQuantity FROM " + tableName + " GROUP BY item_name"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String itemName = resultSet.getString("item_name");
                int totalQuantity = resultSet.getInt("totalQuantity");
                totalQuantityByItem.put(itemName, totalQuantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exceptions
        }

        return totalQuantityByItem;
    }

    private void populateMilkTeaPieChart() {
        Map<String, Integer> totalQuantityByItem = calculateTotalQuantityByItem("milk_tea");

        // Clear existing data
        milkTeaPieChart.getData().clear();

        // Populate the pie chart
        for (Map.Entry<String, Integer> entry : totalQuantityByItem.entrySet()) {
            String itemName = entry.getKey();
            int totalQuantity = entry.getValue();
            milkTeaPieChart.getData().add(new PieChart.Data(itemName, totalQuantity));
        }
    }

    private void populateFruitDrinkPieChart() {
        Map<String, Integer> totalQuantityByItem = calculateTotalQuantityByItem("fruit_drink");

        // Clear existing data
        fruitDrinkPieChart.getData().clear();

        // Populate the pie chart
        for (Map.Entry<String, Integer> entry : totalQuantityByItem.entrySet()) {
            String itemName = entry.getKey();
            int totalQuantity = entry.getValue();
            fruitDrinkPieChart.getData().add(new PieChart.Data(itemName, totalQuantity));
        }
    }

    private void populateFrappePieChart() {
        Map<String, Integer> totalQuantityByItem = calculateTotalQuantityByItem("frappe");

        // Clear existing data
        frappePieChart.getData().clear();

        // Populate the pie chart
        for (Map.Entry<String, Integer> entry : totalQuantityByItem.entrySet()) {
            String itemName = entry.getKey();
            int totalQuantity = entry.getValue();
            frappePieChart.getData().add(new PieChart.Data(itemName, totalQuantity));
        }
    }

    private void populateCoffeePieChart() {
        Map<String, Integer> totalQuantityByItem = calculateTotalQuantityByItem("coffee");

        // Clear existing data
        coffeePieChart.getData().clear();

        // Populate the pie chart
        for (Map.Entry<String, Integer> entry : totalQuantityByItem.entrySet()) {
            String itemName = entry.getKey();
            int totalQuantity = entry.getValue();
            coffeePieChart.getData().add(new PieChart.Data(itemName, totalQuantity));
        }
    }

    private void populateRiceMealPieChart() {
        Map<String, Integer> totalQuantityByItem = calculateTotalQuantityByItem("rice_meal");

        // Clear existing data
        riceMealPieChart.getData().clear();

        // Populate the pie chart
        for (Map.Entry<String, Integer> entry : totalQuantityByItem.entrySet()) {
            String itemName = entry.getKey();
            int totalQuantity = entry.getValue();
            riceMealPieChart.getData().add(new PieChart.Data(itemName, totalQuantity));
        }
    }

    private void populateSnacksPieChart() {
        Map<String, Integer> totalQuantityByItem = calculateTotalQuantityByItem("snacks");

        // Clear existing data
        snacksPieChart.getData().clear();

        // Populate the pie chart
        for (Map.Entry<String, Integer> entry : totalQuantityByItem.entrySet()) {
            String itemName = entry.getKey();
            int totalQuantity = entry.getValue();
            snacksPieChart.getData().add(new PieChart.Data(itemName, totalQuantity));
        }
    }

    private void populateExtrasPieChart() {
        Map<String, Integer> totalQuantityByItem = calculateTotalQuantityByItem("extras");

        // Clear existing data
        extrasPieChart.getData().clear();

        // Populate the pie chart
        for (Map.Entry<String, Integer> entry : totalQuantityByItem.entrySet()) {
            String itemName = entry.getKey();
            int totalQuantity = entry.getValue();
            extrasPieChart.getData().add(new PieChart.Data(itemName, totalQuantity));
        }
    }

    /*

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
        try (Connection connection = database.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM employees")) {

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

        }
    }

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

     */
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        //set filter employee table
        employeeDataList = fetchExistingDataFromDatabase();
         
        ObservableList<String> choices = FXCollections.observableArrayList("All","Active", "Inactive", "Terminated");
        sortFilterEmp.setItems(choices);
        filteredEmployeeData = new FilteredList<>(employeeDataList);
        sortFilterEmp.setOnAction(event -> filterEmployeeTable(sortFilterEmp.getValue()));
        setUpEmployeeTable();
        filterEmployeeTable(sortFilterEmp.getValue());
        //end of set filter employee table

        updateTotalDailySalesLabel();
        updateTotalDailyProductsSoldLabel();
        updateTotalDailyCustomersLabel();
        updateTotalDailyCustomersHome();
        updateTotalDailyProductsSoldHome();
        updateTotalDailySalesHome();

        populateMilkTeaPieChart();
        populateSnacksPieChart();
        populateRiceMealPieChart();
        populateFruitDrinkPieChart();
        populateFrappePieChart();
        populateExtrasPieChart();
        populateCoffeePieChart();

        updateTopProductsLabels();

        initializeChart();
        updateBarChart();

        employeeDataList = FXCollections.observableArrayList();
        setUpEmployeeTable();
        loadEmpDataFromDatabase();

        // Populate the combo box with categories
        categoryComboBox.getItems().addAll("Milk Tea", "Fruit Drink", "Frappe", "Coffee", "Rice Meal", "Snacks", "Extras");

        // Set default selection
        categoryComboBox.getSelectionModel().selectFirst();

        // Add listener to handle combo box selection changes
        categoryComboBox.setOnAction(this::handleCategoryChange);

        // Initialize pane visibility
        showPaneBasedOnCategory(categoryComboBox.getValue());

        fetchHighestPerformingEmployee();

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
    
    //filter employee table depending on status
   private void filterEmployeeTable(String selectedStatus) {
    filteredEmployeeData.setPredicate(employee -> {
        if (selectedStatus == null || selectedStatus.isEmpty() || selectedStatus.equals("All")) {
            return true;
        }
        return employee.getEmpStatus().equalsIgnoreCase(selectedStatus);
    });
    employeeTable.setItems(filteredEmployeeData);
    }

    @FXML
    private void fetchHighestPerformingEmployee() {
        try (Connection connection = database.getConnection()) {
            String query = "SELECT emp_name, COUNT(emp_name) AS orderCount "
                    + "FROM invoice_archive "
                    + "GROUP BY emp_name "
                    + "ORDER BY orderCount DESC "
                    + "LIMIT 1";

            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    String topEmployeeName = resultSet.getString("emp_name");
                    int orderCount = resultSet.getInt("orderCount");

                    topEmpLBL.setText(topEmployeeName);
                    totalOrdsHandledLBL.setText(String.valueOf(orderCount));
                } else {
                    // Handle case when no data is returned
                    topEmpLBL.setText("No data available");
                    totalOrdsHandledLBL.setText("N/A");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exceptions
        }
    }

    @FXML
    private void handleMilkteaButtonClick(ActionEvent event
    ) {
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
    private void handleFruitDrinkButtonClick(ActionEvent event
    ) {
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
    private void handleCoffeeButtonClick(ActionEvent event
    ) {
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
    private void handleRiceMealsButtonClick(ActionEvent event
    ) {
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
    private void handleSnacksButtonClick(ActionEvent event
    ) {
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
    private void handleExtrasButtonClick(ActionEvent event
    ) {
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
    private void handleFrappeButtonClick(ActionEvent event
    ) {
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

    @FXML
    private void handleDiscountButtonClick(ActionEvent event
    ) {
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
    private void handleEditButtonAction(ActionEvent event
    ) {
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
     @FXML
    private void handleDiscHistoryButton(ActionEvent event) {
           if (isHistoryMode) {
            loadDataFromDatabase();
            DiscHistory.setText("History");
            setButtonsEnabled(true); 
        } else {
            loadDiscHistFromDatabase();
            DiscHistory.setText("Back");
            setButtonsEnabled(false);
        }
        isHistoryMode = !isHistoryMode;
    }
    
    private void setButtonsEnabled(boolean enabled) {
        AddCoup.setDisable(!enabled);
        DelBtn.setDisable(!enabled);
        EditBtn.setDisable(!enabled);
    }

    private void loadDataFromDatabase() {
        List<Discount> discountList = fetchDiscountsFromDatabase();
        discounts.clear();
        discounts.addAll(discountList);
        discountTableView.setItems(discounts);
    }
    
     private void loadDiscHistFromDatabase() {
        List<Discount> discountList = fetchDiscHistoryFromDatabase();
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
    private ObservableList<Discount> fetchDiscHistoryFromDatabase() {
        ObservableList<Discount> discounts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM discount WHERE Date_valid < CURRENT_DATE or limit_usage <= 0";

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

        try (Connection connection = database.getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT SUM(total) AS totalSales FROM invoice_archive"); ResultSet resultSet = statement.executeQuery()) {

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

        } else if (clickedButton == salesRepBTN) {
            home.setVisible(false);
            salesRep.setVisible(true);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(false);

        } else if (clickedButton == invManageBTN) {
            home.setVisible(false);
            salesRep.setVisible(false);
            invManage.setVisible(true);
            empDetails.setVisible(false);
            disCoup.setVisible(false);

        } else if (clickedButton == empDetailsBTN) {
            home.setVisible(false);
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(true);
            disCoup.setVisible(false);

        } else if (clickedButton == disCoupBTN) {
            home.setVisible(false);
            salesRep.setVisible(false);
            invManage.setVisible(false);
            empDetails.setVisible(false);
            disCoup.setVisible(true);
            refreshTableView();
        }

    }

    private void handleCategoryChange(ActionEvent event) {
        String selectedCategory = categoryComboBox.getValue();
        showPaneBasedOnCategory(selectedCategory);
    }

    private void showPaneBasedOnCategory(String category) {
        milkteaPANE.setVisible("Milk Tea".equals(category));
        fruitdrinkPANE.setVisible("Fruit Drink".equals(category));
        frappePANE.setVisible("Frappe".equals(category));
        coffeePANE.setVisible("Coffee".equals(category));
        ricemealPANE.setVisible("Rice Meal".equals(category));
        snacksPANE.setVisible("Snacks".equals(category));
        extrasPANE.setVisible("Extras".equals(category));

        // Hide all other panes
        if (!"Milk Tea".equals(category)) {
            milkteaPANE.setVisible(false);
        }
        if (!"Fruit Drink".equals(category)) {
            fruitdrinkPANE.setVisible(false);
        }
        if (!"Frappe".equals(category)) {
            frappePANE.setVisible(false);
        }
        if (!"Coffee".equals(category)) {
            coffeePANE.setVisible(false);
        }
        if (!"Rice Meal".equals(category)) {
            ricemealPANE.setVisible(false);
        }
        if (!"Snacks".equals(category)) {
            snacksPANE.setVisible(false);
        }
        if (!"Extras".equals(category)) {
            extrasPANE.setVisible(false);
        }
    }

}
