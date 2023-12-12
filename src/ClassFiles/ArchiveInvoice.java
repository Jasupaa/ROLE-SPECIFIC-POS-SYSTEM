/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Gwyneth Uy
 */
public class ArchiveInvoice {

    private Date dateTime;
    private String cashier;
    private Integer custID;
    private String orderType;
    private Double total;

    public ArchiveInvoice(Date dateTime, String cashier, Integer custID, String orderType, Double total) {
        this.dateTime = dateTime;
        this.cashier = cashier;
        this.custID = custID;
        this.orderType = orderType;
        this.total = total;

    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public Integer getCustID() {
        return custID;
    }

    public void setCustID(Integer custID) {
        this.custID = custID;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
