package ClassFiles;

import javafx.beans.property.*;

public class ItemData2 {
    private final IntegerProperty orderID;
    private final StringProperty itemName;
    private final DoubleProperty itemPrice;
    private final IntegerProperty itemQuantity;
    private final StringProperty employeeName;
    private final StringProperty dateTime;

    public ItemData2(int orderID, String itemName, double itemPrice, int itemQuantity) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemPrice = new SimpleDoubleProperty(itemPrice);
        this.itemQuantity = new SimpleIntegerProperty(itemQuantity);
        this.employeeName = new SimpleStringProperty("");
        this.dateTime = new SimpleStringProperty("");
    }

    public Integer getOrderID() {
        return orderID.get();
    }

    public void setOrderID(int orderID) {
        this.orderID.set(orderID);
    }

    public String getItemName() {
        return itemName.get();
    }

    public void setItemName(String name) {
        itemName.set(name);
    }

    public Double getItemPrice() {
        return itemPrice.get();
    }

    public void setItemPrice(double price) {
        itemPrice.set(price);
    }

    public Integer getItemQuantity() {
        return itemQuantity.get();
    }

    public void setItemQuantity(int quantity) {
        itemQuantity.set(quantity);
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public void setEmployeeName(String name) {
        employeeName.set(name);
    }

    public String getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(String dateTime) {
        this.dateTime.set(dateTime);
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public DoubleProperty itemPriceProperty() {
        return itemPrice;
    }

    public IntegerProperty itemQuantityProperty() {
        return itemQuantity;
    }

    public StringProperty employeeNameProperty() {
        return employeeName;
    }

    public StringProperty dateTimeProperty() {
        return dateTime;
    }
}
