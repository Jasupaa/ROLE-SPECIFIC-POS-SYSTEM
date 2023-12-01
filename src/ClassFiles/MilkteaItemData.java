/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import com.mysql.cj.jdbc.Blob;

public class MilkteaItemData {

    private String itemName;
    private String addons;
    private Integer smallPrice;
    private Integer mediumPrice;
    private Integer largePrice;
    private Blob image;

    public MilkteaItemData(String itemName, String addons, Integer smallPrice, Integer mediumPrice, Integer largePrice, Blob image) {
        this.itemName = itemName;
        this.addons = addons;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
        this.image = image;
    }

    public MilkteaItemData(String itemName, String addons, Integer smallPrice, Integer mediumPrice, Integer largePrice) {
        this.itemName = itemName;
        this.addons = addons;
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
}
