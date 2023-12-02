/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import java.io.InputStream;
import java.sql.Blob;


public class ExtrasItemData {

    private String itemName;
    private Integer price;
    private Blob image;
    private Integer itemID;
    private InputStream imageInputStream;
    
    
     public ExtrasItemData(String itemName, Integer price, Blob image, Integer itemID) {
        this.itemName = itemName;
        this.price = price;
        this.image = image;
        this.itemID = itemID;
    }

    public ExtrasItemData(String itemName, Integer price) {
        this.itemName = itemName;
        this.price = price;

    }
    
    
   
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
