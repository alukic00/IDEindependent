package com.teletrader.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order buyOrder;
    @ManyToOne private Order sellOrder;
    private double price;
    private int amount;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String executedAt;

}