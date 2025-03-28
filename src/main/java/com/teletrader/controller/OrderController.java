package com.teletrader.controller;


import com.teletrader.DTO.OrderDTO;
import com.teletrader.entity.Order;
import com.teletrader.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Endpoint za kreiranje novog naloga
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        // Validacija osnovnih podataka
        if (order.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (order.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        if (!"BUY".equalsIgnoreCase(order.getType()) && !"SELL".equalsIgnoreCase(order.getType())) {
            throw new IllegalArgumentException("Type must be either 'BUY' or 'SELL'");
        }

        Order savedOrder = orderService.createOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    // Endpoint za dobijanje Top 10 Buy naloga
    @GetMapping("/top10-buy")
    public ResponseEntity<List<Order>> getTop10BuyOrders() {
        List<Order> orders = orderService.getTop10BuyOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Endpoint za dobijanje Top 10 Sell naloga
    @GetMapping("/top10-sell")
    public ResponseEntity<List<Order>> getTop10SellOrders() {
        List<Order> orders = orderService.getTop10SellOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
