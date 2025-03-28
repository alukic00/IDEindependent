package com.teletrader.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order buyOrder;
    @ManyToOne private Order sellOrder;
    private double price;
    private int amount;
    private LocalDateTime executedAt = LocalDateTime.now();

    public Order getBuyOrder() {
        return buyOrder;
    }
    public void setBuyOrder(Order buyOrder) {
        this.buyOrder = buyOrder;
    }
    public Order getSellOrder() {
        return sellOrder;
    }
    public void setSellOrder(Order sellOrder) {
        this.sellOrder = sellOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }
    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }
}