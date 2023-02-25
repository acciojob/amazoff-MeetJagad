package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

@Repository
public class OrderRepository {
    HashMap<String, Order> orderdatabase =new HashMap<>();
    HashMap<String, DeliveryPartner> deliveryPartenerdb =new HashMap<>();
    HashMap<String, List<String>> orderToPartenerdb=new HashMap<>();
    HashMap<String, String> orderAssigneddb=new HashMap<>();

    public String addOrder(Order order){
        orderdatabase.put(order.getId(), order);
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
        for (String s : orderdatabase.keySet()) {
            if (s.equals(orderId)) {
                return orderdatabase.get(s);
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
        for (String s : orderdatabase.keySet()) {
            orders.add(s);
        }
        return orders;
    }
    public int getCountOfUnassignedOrders() {
        int countOfOrders = orderdatabase.size() - orderAssigneddb.size();
        return countOfOrders;
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String deliveryPartnerId) {
        int countOfOrders = 0;
        List<String> list = orderToPartenerdb.get(deliveryPartnerId);
        int deliveryTime = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3));
        for (String s : list) {
            Order order = orderdatabase.get(s);
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
            Order order = orderdatabase.get(s);
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
        orderdatabase.remove(orderId);
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

    public String addDeliveryPartener(String deliveryPartnerId) {
        return deliveryPartnerId;
    }
}