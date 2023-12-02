/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import javafx.event.Event;
import javafx.event.EventType;
/**
 *
 * @author Jasper
 */
public class DiscountAddedEvent extends Event {
    public static final EventType<DiscountAddedEvent> DISCOUNT_ADDED = new EventType<>(Event.ANY, "DISCOUNT_ADDED");

    public DiscountAddedEvent() {
        super(DISCOUNT_ADDED);
    }
}

   

