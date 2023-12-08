/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import java.sql.Blob;
import java.io.InputStream;

/**
 *
 * @author John Paul Uy
 */
public class FrappeItemData {

    private String itemName;
    private Integer smallPrice;
    private Integer mediumPrice;
    private Integer largePrice;
    private Blob image;
    private Integer itemID;
    private InputStream imageInputStream; 
    private String status; 
    

    public FrappeItemData(String itemName, Integer smallPrice, Integer mediumPrice, Integer largePrice, Blob image, int itemID, String status) {
        this.itemName = itemName;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
        this.image = image;
        this.itemID = itemID;
        this.status = status;
    }

    public FrappeItemData(String itemName, Integer smallPrice, Integer mediumPrice, Integer largePrice) {
        this.itemName = itemName;
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
     
     public String getStatus() {
        return status;
    }
         public void setStatus (String status){
             this.status= status;
             
}

}
