package com.teletrader.service;

import com.teletrader.entity.Order;
import com.teletrader.entity.Trade;
import com.teletrader.entity.Status;
import com.teletrader.repository.OrderRepository;
import com.teletrader.repository.TradeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingEngine {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Transactional
    public void matchOrders(Order newOrder) {
        List<Order> opposingOrders = getOpposingOrders(newOrder);

        for (Order existingOrder : opposingOrders) {
            if (isMatch(newOrder, existingOrder)) {
                executeTrade(newOrder, existingOrder);
            }
        }
    }

    private List<Order> getOpposingOrders(Order order) {
        return order.getType().equals("BUY")
                ? orderRepository.findMatchingSells(order.getPrice())
                : orderRepository.findMatchingBuys(order.getPrice());
    }

    private boolean isMatch(Order order1, Order order2) {
        return order1.getType().equals("BUY")
                ? order1.getPrice() >= order2.getPrice()
                : order2.getPrice() >= order1.getPrice();
    }

    private void executeTrade(Order newOrder, Order existingOrder) {
        int tradeAmount = Math.min(newOrder.getAmount(), existingOrder.getAmount());
        double tradePrice = existingOrder.getPrice(); // Ili neka druga logika za cenu

        // Kreiranje trade-a
        Trade trade = new Trade();
        trade.setBuyOrder(newOrder.getType().equals("BUY") ? newOrder : existingOrder);
        trade.setSellOrder(newOrder.getType().equals("SELL") ? newOrder : existingOrder);
        trade.setAmount(tradeAmount);
        trade.setPrice(tradePrice);
        tradeRepository.save(trade);

        // Ažuriranje ordera
        updateOrderAmounts(newOrder, existingOrder, tradeAmount);
    }

    private void updateOrderAmounts(Order order1, Order order2, int tradedAmount) {
        order1.setAmount(order1.getAmount() - tradedAmount);
        order2.setAmount(order2.getAmount() - tradedAmount);

        if (order1.getAmount() <= 0) order1.setStatus(Status.FILLED);
        if (order2.getAmount() <= 0) order2.setStatus(Status.FILLED);

        orderRepository.saveAll(List.of(order1, order2));
    }

    public void processOrder(Order order) {
        if (!order.getStatus().equals(Status.ACTIVE)) {
            return;
        }
        List<Order> matches = order.getType().equals("BUY")
                ? orderRepository.findMatchingSells(order.getPrice())
                : orderRepository.findMatchingBuys(order.getPrice());

        matches.forEach(match -> {
            if (order.getAmount() > 0 && isMatch(order, match)) {
                executeTrade(order, match);
            }
        });
    }
}
