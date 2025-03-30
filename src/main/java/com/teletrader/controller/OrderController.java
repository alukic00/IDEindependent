package com.teletrader.controller;


import com.teletrader.DTO.OrderDTO;
import com.teletrader.entity.Order;
import com.teletrader.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Transactional
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Endpoint za kreiranje novog naloga

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Order>> createOrders(@RequestBody List<OrderDTO> orderRequests) {
        List<Order> createdOrders = orderService.createOrders(orderRequests);
        return new ResponseEntity<>(createdOrders, HttpStatus.CREATED);
    }


    @GetMapping("/top-buys")
    public ResponseEntity<List<Order>> getTop10Buys() {
        List<Order> topBuys = orderService.getTop10BuyOrders();
        return ResponseEntity.ok(topBuys);
    }

    @GetMapping("/top-sells")
    public ResponseEntity<List<Order>> getTop10Sells() {
        List<Order> topSells = orderService.getTop10SellOrders();
        return ResponseEntity.ok(topSells);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Order>> getActiveOrders() {
        List<Order> activeOrders = orderService.getActiveOrders();
        return ResponseEntity.ok(activeOrders);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.ok().body("Order sa ID " + id + " je uspešno obrisan");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Order sa ID " + id + " nije pronađen");
    }
}
