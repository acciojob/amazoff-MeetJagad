package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    //@Autowired
    OrderRepository orderRepository = new OrderRepository();
    public String addOrder(Order orderId){
        String result = orderRepository.addOrder(orderId);
        return result;
    }

    public String addDeliveryPartner(String deliveryPartnerId){
        String result = orderRepository.addDeliveryPartener(deliveryPartnerId);
        return result;
    }
    public String addOrderPartnerPair(String orderId, String deliveryPartnerId){
        String result = orderRepository.addOrderPartenerPair(orderId, deliveryPartnerId);
        return result;
    }
    public Order getOrderById(String orderId) {
        Order result = orderRepository.getOrderById(orderId);
        return result;
    }
    public DeliveryPartner getPartnerById(String deliveryPartnerId) {
        DeliveryPartner result = orderRepository.getPartnerById(deliveryPartnerId);
        return result;
    }
    public int getOrderCountByPartnerId(String deliveryPartnerId) {
        int result = orderRepository.getOrderCountByPartnerId(deliveryPartnerId);
        return result;
    }
    public List<String> getOrdersByPartnerId(String deliveryPartnerId) {
        List<String> result = orderRepository.getOrdersByPartnerId(deliveryPartnerId);
        return result;
    }

    public List<String> getAllOrders() {
        List<String> result = orderRepository.getAllOrders();
        return result;
    }

    public int getCountOfUnassignedOrders() {
        int countOfOrders = orderRepository.getCountOfUnassignedOrders();
        return countOfOrders;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String deliveryPartnerId) {
        int countOfOrders = orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time, deliveryPartnerId);
        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String deliveryPartnerId) {
        String time = orderRepository.getLastDeliveryTimeByPartnerId(deliveryPartnerId);
        return time;
    }

    public String deletePartnerById(String deliveryPartnerId) {
        String result = orderRepository.deletePartnerById(deliveryPartnerId);
        return result;
    }

    public String deleteOrderById(String orderId) {
        String result = orderRepository.deleteOrderById(orderId);
        return result;
    }
}