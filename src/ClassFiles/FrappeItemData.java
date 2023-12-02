/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import com.mysql.cj.jdbc.Blob;

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

    public FrappeItemData(String itemName, Integer smallPrice, Integer mediumPrice, Integer largePrice, Blob image) {
        this.itemName = itemName;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
        this.image = image;
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

}
