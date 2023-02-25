package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

//    Orders:
//    Each order has a unique orderId and a delivery time (in the 24-hour HH:MM format).
//    This means that the order with the given Id needs to be delivered exactly at HH:MM on the next day.
//    Each order is assigned to at most one delivery partner.
//    This means that either the order is assigned to exactly one delivery partner or it is left unassigned.

    public Order(String id, String deliveryTime) {

        this.id=id;
        int hour=Integer.valueOf(deliveryTime.substring(0,2));
        int min=Integer.valueOf(deliveryTime.substring(3));
        this.deliveryTime=hour*60+min;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM

    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;}
}
