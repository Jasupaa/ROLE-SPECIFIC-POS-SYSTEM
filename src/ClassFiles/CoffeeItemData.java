/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import java.sql.Blob;
import java.io.InputStream;


public class CoffeeItemData {

    private String itemName;
    private String type;
    private Integer smallPrice;
    private Integer mediumPrice;
    private Integer largePrice;
    private Blob image;
    private Integer itemID;
    private InputStream imageInputStream;
    
    
      public CoffeeItemData(String itemName, String type, Integer smallPrice, Integer mediumPrice, Integer largePrice, Blob image, Integer itemID) {
        this.itemName = itemName;
        this.type = type;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
        this.image = image;
        this.itemID = itemID;
    }

    public CoffeeItemData(String itemName, String type, Integer smallPrice, Integer mediumPrice, Integer largePrice) {
        this.itemName = itemName;
        this.type = type;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
       
    }
    
    

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

