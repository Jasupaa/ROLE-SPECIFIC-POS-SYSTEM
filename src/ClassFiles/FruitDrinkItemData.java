/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import java.sql.Blob;
import java.io.InputStream;

public class FruitDrinkItemData {

    private String itemName;
    private Integer smallPrice;
    private Integer mediumPrice;
    private Integer largePrice;
    private String fruitFlavor;
    private String sinkers;
    private Blob image;
    private Integer itemID;
    private InputStream imageInputStream;
    
    
    public FruitDrinkItemData(String itemName, Integer smallPrice, Integer mediumPrice, Integer largePrice, String fruitFlavor, String sinkers, Blob image, Integer itemID) {
        this.itemName = itemName;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
        this.fruitFlavor = fruitFlavor;
        this.sinkers = sinkers;
        this.image = image;
        this.itemID = itemID;
    }

    public FruitDrinkItemData(String itemName, Integer smallPrice, Integer mediumPrice, Integer largePrice, String fruitFlavor, String sinkers) {
        this.itemName = itemName;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
        this.fruitFlavor = fruitFlavor;
        this.sinkers = sinkers;

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSmallPrice() {
        return smallPrice;
    }

    public void setSmallPrice(Integer smallPrice) {
        this.smallPrice = smallPrice;
    }

    public Integer getMediumPrice() {
        return mediumPrice;
    }

    public void setMediumPrice(Integer mediumPrice) {
        this.mediumPrice = mediumPrice;
    }

    public Integer getLargePrice() {
        return largePrice;
    }

    public void setLargePrice(Integer largePrice) {
        this.largePrice = largePrice;
    }

    public String getFruitFlavor() {
        return fruitFlavor;
    }

    public void getFruitFlavor(String fruitFlavor) {
        this.fruitFlavor = fruitFlavor;
    }

    public String getSinkers() {
        return sinkers;
    }

    public void setSinkers(String sinkers) {
        this.sinkers = sinkers;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }
    
    public InputStream getImageInputStream() {
        return imageInputStream;
    }
     public void setImageInputStream(InputStream imageInputStream) {
        this.imageInputStream = imageInputStream;
    }

}
