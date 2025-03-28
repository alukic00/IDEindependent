package com.teletrader.service;

import com.teletrader.DTO.OrderDTO;
import com.teletrader.entity.Order;
import com.teletrader.entity.OrderType;
import com.teletrader.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getTop10BuyOrders() {
        return orderRepository.findTop10ByTypeOrderByPriceDesc("BUY");
    }

    public List<Order> getTop10SellOrders() {
        return orderRepository.findTop10ByTypeOrderByPriceAsc("SELL");
    }

    public List<Order> createOrders(List<Order> orders) {
        return orderRepository.saveAll(orders); // Koristi JPA saveAll()
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }



}
