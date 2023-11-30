/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

/**
 *
 * @author Gwyneth Uy
 */
public class OrderCardData {

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAddOns() {
        return addOns;
    }

    public void setAddOns(String addOns) {
        this.addOns = addOns;
    }

    public String getFruitFlavor() {
        return fruitFlavor;
    }

    public void setFruitFlavor(String fruitFlavor) {
        this.fruitFlavor = fruitFlavor;
    }

    public String getSinkers() {
        return sinkers;
    }

    public void setSinkers(String sinkers) {
        this.sinkers = sinkers;
    }

    public String getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(String sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public OrderCardData(String itemName, Integer quantity, String size, String addOns, String fruitFlavor, String sinkers, String sugarLevel) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.size = size;
        this.addOns = addOns;
        this.fruitFlavor = fruitFlavor;
        this.sinkers = sinkers;
        this.sugarLevel = sugarLevel;
    }

    private String itemName;
    private Integer quantity;
    private String size;
    private String addOns;
    private String fruitFlavor;
    private String sinkers;
    private String sugarLevel;

}
