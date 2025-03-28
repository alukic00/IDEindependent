package com.teletrader.service;

import com.teletrader.DTO.OrderDTO;
import com.teletrader.entity.Order;
import com.teletrader.entity.OrderType;
import com.teletrader.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MatchingEngine matchingEngine;

    @Autowired
    public OrderService(OrderRepository orderRepository, MatchingEngine matchingEngine) {
        this.orderRepository = orderRepository;
        this.matchingEngine = matchingEngine; // Injektujte
    }

    public List<Order> getTop10BuyOrders() {
        return orderRepository.findTop10ByTypeOrderByPriceDesc("BUY");
    }

    public List<Order> getTop10SellOrders() {
        return orderRepository.findTop10ByTypeOrderByPriceAsc("SELL");
    }

    public List<Order> createOrders(List<OrderDTO> orderRequests) {
        // Konvertujte DTO u Order entitete sa automatskim postavljanjem vremena i statusa
        List<Order> orders = orderRequests.stream()
                .map(dto -> {
                    Order order = new Order();
                    order.setPrice(dto.getPrice());
                    order.setAmount(dto.getAmount());
                    order.setType(dto.getType());
                    // createdAt i status će biti postavljeni u @PrePersist
                    return order;
                })
                .collect(Collectors.toList());

        List<Order> savedOrders = orderRepository.saveAll(orders);

        // 3. Procesiranje svakog ordera kroz matching engine
        savedOrders.forEach(matchingEngine::processOrder);

        // 4. Ažuriranje promenjenih ordera nakon procesiranja
        orderRepository.saveAll(savedOrders);

        return savedOrders;
        //return orderRepository.saveAll(orders);
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
