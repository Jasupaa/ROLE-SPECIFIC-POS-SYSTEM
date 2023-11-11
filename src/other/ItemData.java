/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package other;

/**
 *
 * @author John Paul Uy
 */
import javafx.beans.property.*;

public class ItemData {
    private final IntegerProperty orderID;
    private final StringProperty itemName;
    private final DoubleProperty itemPrice;
    private final IntegerProperty itemQuantity;

    public ItemData(int orderID, String itemName, double itemPrice, int itemQuantity) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemPrice = new SimpleDoubleProperty(itemPrice);
        this.itemQuantity = new SimpleIntegerProperty(itemQuantity);
    }

    public Integer getorderID() {
        return orderID.get();
    }

    public void setorderID(int orderid) {
        orderID.set(orderid);
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

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public DoubleProperty itemPriceProperty() {
        return itemPrice;
    }

    public IntegerProperty itemQuantityProperty() {
        return itemQuantity;
    }

}


