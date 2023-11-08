/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package other;

/**
 *
 * @author John Paul Uy
 */
public class ItemData {
    private String itemName;
    private double itemPrice;
    private int itemQuantity;

    public ItemData(String itemName, double itemPrice, int itemQuantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double price) {
        this.itemPrice = price;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int quantity) {
        this.itemQuantity = quantity;
    }
}

