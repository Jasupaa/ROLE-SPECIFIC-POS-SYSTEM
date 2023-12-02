/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import java.time.LocalDate;

/**
 *
 * @author Jasper
 */
public class Discount {
   

    private String discCode;
    private double discValue;
    private String descCoup;
    private LocalDate dateCreated;
    private LocalDate dateValid;
    private int id;

    public Discount(int id, String discCode, double discValue, String descCoup, LocalDate dateCreated, LocalDate dateValid) {
        this.discCode = discCode;
        this.discValue = discValue;
        this.descCoup = descCoup;
        this.dateCreated = dateCreated;
        this.dateValid = dateValid;
         this.id = id;
     
    }
     public Discount(String discCode, double discValue, String descCoup, LocalDate dateCreated, LocalDate dateValid) {
        this.discCode = discCode;
        this.discValue = discValue;
        this.descCoup = descCoup;
        this.dateCreated = dateCreated;
        this.dateValid = dateValid;
        
     
    }


   

    // Getters and setters...

    public String getDiscCode() {
        return discCode;
    }

    public void setDiscCode(String discCode) {
        this.discCode = discCode;
    }

    public double getDiscValue() {
        return discValue;
    }

    public void setDiscValue(double discValue) {
        this.discValue = discValue;
    }

    public String getDescCoup() {
        return descCoup;
    }

    public void setDescCoup(String descCoup) {
        this.descCoup = descCoup;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateValid() {
        return dateValid;
    }

    public void setDateValid(LocalDate dateValid) {
        this.dateValid = dateValid;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
    

