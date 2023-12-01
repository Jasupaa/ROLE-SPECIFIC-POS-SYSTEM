/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import MainAppFrame.CashierFXMLController;

/**
 *
 * @author John Paul Uy
 */
public class ControllerManager {

    private static CashierFXMLController cashierController;

    public static void setCashierController(CashierFXMLController controller) {
        cashierController = controller;
    }

    public static CashierFXMLController getCashierController() {
        return cashierController;
    }
}
