/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;


import java.sql.Blob;
import java.io.InputStream;


public class MilkteaItemData {

    private String itemName;
    private String addons;
    private String addonsPrice;
    private Integer smallPrice;
    private Integer mediumPrice;
    private Integer largePrice;
    private Blob image;
    private Integer itemID;
    private InputStream imageInputStream;
    private String status; 

    public MilkteaItemData(String itemName, String addons, String addonsPrice, Integer smallPrice, Integer mediumPrice, Integer largePrice, Blob image, Integer itemID,String status) {
        this.itemName = itemName;
        this.addons = addons;
         this.addonsPrice = addonsPrice;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
        this.image = image;
        this.itemID = itemID;
        this.status = status;
    }

    public MilkteaItemData(String itemName, String addons, String addonsPrice, Integer smallPrice, Integer mediumPrice, Integer largePrice) {
        this.itemName = itemName;
        this.addons = addons;
        this.addonsPrice = addonsPrice;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
       
    }

    public String getItemName() {
        return itemName;
    }

    public String getAddons() {
        return addons;
    }

    public Integer getSmallPrice() {
        return smallPrice;
    }

    public Integer getMediumPrice() {
        return mediumPrice;
    }

    public Integer getLargePrice() {
        return largePrice;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
     public Integer getItemID()  {
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
             public String getAddonsPrice() {
        return addonsPrice;
    }

    public void setAddonsPrice(String addonsPrice) {
        this.addonsPrice = addonsPrice;
    }
}