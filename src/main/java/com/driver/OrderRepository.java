package com.driver;


import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    HashMap<String, Order> orderdb = new HashMap<>();
    HashMap<String, DeliveryPartner> deliveryPartenerdb = new HashMap<>();
    HashMap<String, List<String>> orderToPartenerdb = new HashMap<>();
    HashMap<String, String> orderAssigneddb = new HashMap<>();

    public String addOrder(Order order){
        orderdb.put(order.getId(), order);
        return "added successfully";
    }
    public String addDeliveryPartener(String deliveryPartenerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(deliveryPartenerId);
        deliveryPartenerdb.put(deliveryPartenerId, deliveryPartner);
        return "added successfully";
    }
    public String addOrderPartenerPair(String orderid, String deliveryPartenerid){
        List<String> list = orderToPartenerdb.getOrDefault(deliveryPartenerid, new ArrayList<>());
        list.add(orderid);
        orderToPartenerdb.put(deliveryPartenerid, list);
        orderAssigneddb.put(orderid, deliveryPartenerid);
        DeliveryPartner deliveryPartner = deliveryPartenerdb.get(deliveryPartenerid);
        deliveryPartner.setNumberOfOrders(list.size());
        return "added successfully";

    }
    public Order getOrderById(String orderId) {
        for (String s : orderdb.keySet()) {
            if (s.equals(orderId)) {
                return orderdb.get(s);
            }
        }
        return null;
    }
    public DeliveryPartner getPartnerById(String deliveryPartnerId) {
        if (deliveryPartenerdb.containsKey(deliveryPartnerId)) {
            return deliveryPartenerdb.get(deliveryPartnerId);
        }
        return null;
    }
    public int getOrderCountByPartnerId(String deliveryPartnerId) {
        int orders = orderToPartenerdb.getOrDefault(deliveryPartnerId, new ArrayList<>()).size();
        return orders;
    }
    public List<String> getOrdersByPartnerId(String deliveryPartnerId) {
        List<String> orders = orderToPartenerdb.getOrDefault(deliveryPartnerId, new ArrayList<>());
        return orders;
    }
    public List<String> getAllOrders() {
        List<String> orders = new ArrayList<>();
        for (String s : orderdb.keySet()) {
            orders.add(s);
        }
        return orders;
    }
    public int getCountOfUnassignedOrders() {
        int countOfOrders = orderdb.size() - orderAssigneddb.size();
        return countOfOrders;
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String deliveryPartnerId) {
        int countOfOrders = 0;
        List<String> list = orderToPartenerdb.get(deliveryPartnerId);
        int deliveryTime = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3));
        for (String s : list) {
            Order order = orderdb.get(s);
            if (order.getDeliveryTime() > deliveryTime) {
                countOfOrders++;
            }
        }
        return countOfOrders;
    }
    public String getLastDeliveryTimeByPartnerId(String deliveryPartnerId) {
        String time = "";
        List<String> list = orderToPartenerdb.get(deliveryPartnerId);
        int deliveryTime = 0;
        for (String s : list) {
            Order order = orderdb.get(s);
            deliveryTime = Math.max(deliveryTime, order.getDeliveryTime());
        }
        int hour = deliveryTime / 60;
        String sHour = "";
        if (hour < 10) {
            sHour = "0" + String.valueOf(hour);
        } else {
            sHour = String.valueOf(hour);
        }
        int min = deliveryTime % 60;
        String sMin = "";
        if (min < 10) {
            sMin = "0" + String.valueOf(min);
        } else {
            sMin = String.valueOf(min);
        }
        time = sHour + ":" + sMin;
        return time;
    }
    public String deletePartnerById(String deliveryPartnerId) {
        deliveryPartenerdb.remove(deliveryPartnerId);
        List<String> list = orderToPartenerdb.getOrDefault(deliveryPartnerId, new ArrayList<>());
        ListIterator<String> itr = list.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            orderAssigneddb.remove(s);
        }
        orderToPartenerdb.remove(deliveryPartnerId);
        return "Deleted Successfully";
    }
    public String deleteOrderById(String orderId) {
        orderdb.remove(orderId);
        String partnerId = orderAssigneddb.get(orderId);
        orderAssigneddb.remove(orderId);
        List<String> list = orderToPartenerdb.get(partnerId);
        ListIterator<String> itr = list.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            if (s.equals(orderId)) {
                itr.remove();
            }
        }
        orderToPartenerdb.put(partnerId, list);
        return "Deleted Successfully";
    }
}