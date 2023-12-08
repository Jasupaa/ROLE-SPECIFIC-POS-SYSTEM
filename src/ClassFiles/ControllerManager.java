/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassFiles;

import MainAppFrame.CashierConfirmationFXMLController;
import MainAppFrame.CashierFXMLController;
import MainAppFrame.SettlePaymentFXMLController;

/**
 *
 * @author John Paul Uy
 */
public class ControllerManager {

    private static CashierFXMLController cashierController;
    private static SettlePaymentFXMLController settlePaymentController;
    private static CashierConfirmationFXMLController cashierConfirmationController;

    public static void setCashierController(CashierFXMLController controller) {
        cashierController = controller;
    }

    public static CashierFXMLController getCashierController() {
        return cashierController;
    }

    public static SettlePaymentFXMLController getSettlePaymentController() {
        return settlePaymentController;
    }

    public static void setSettlePaymentController(SettlePaymentFXMLController controller) {
        settlePaymentController = controller;
    }

    public static CashierConfirmationFXMLController getCashierConfirmationController() {
        return cashierConfirmationController;
    }

    public static void setCashierConfirmationController(CashierConfirmationFXMLController controller) {
        cashierConfirmationController = controller;
    }
}
