package com.teletrader.service;

import com.teletrader.entity.Order;
import com.teletrader.entity.Trade;
import com.teletrader.entity.Status;
import com.teletrader.repository.OrderRepository;
import com.teletrader.repository.TradeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MatchingEngine {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Transactional
    public void matchOrdersWithPriceTimePriority(Order newOrder) {

        List<Order> opposingOrders = getOpposingOrdersWithPriority(newOrder);

        for (Order existingOrder : opposingOrders) {
            if (newOrder.getAmount() <= 0) break; // Prekid ako je order potpuno ispunjen

            if (isMatch(newOrder, existingOrder)) {
                executeTrade(newOrder, existingOrder);
            }
        }
    }

    private List<Order> getOpposingOrdersWithPriority(Order order) {
        return order.getType().equals("BUY") ?
                // Za BUY: SELL ordere sortirane prvo po ceni (niža=bolja), pa po vremenu (starije=bolje)
                orderRepository.findByTypeAndStatusAndPriceLessThanEqualOrderByPriceAscCreatedAtAsc(
                        "SELL", Status.ACTIVE, order.getPrice()) :

                // Za SELL: BUY ordere sortirane prvo po ceni (viša=bolja), pa po vremenu (starije=bolje)
                orderRepository.findByTypeAndStatusAndPriceGreaterThanEqualOrderByPriceDescCreatedAtAsc(
                        "BUY", Status.ACTIVE, order.getPrice());
    }


    private boolean isMatch(Order order1, Order order2) {
        return order1.getType().equals("BUY")
                ? order1.getPrice() >= order2.getPrice()
                : order2.getPrice() >= order1.getPrice();
    }

    private void executeTrade(Order newOrder, Order existingOrder) {
        int tradeAmount = Math.min(newOrder.getAmount(), existingOrder.getAmount());
        double tradePrice = existingOrder.getPrice();
        // Kreiranje trade-a
        Trade trade = new Trade();
        trade.setBuyOrder(newOrder.getType().equals("BUY") ? newOrder : existingOrder);
        trade.setSellOrder(newOrder.getType().equals("SELL") ? newOrder : existingOrder);
        trade.setAmount(tradeAmount);
        trade.setPrice(tradePrice);
        trade.setExecutedAt(new Date().toString());
        tradeRepository.save(trade);


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
