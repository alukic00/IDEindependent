package com.teletrader.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data // Generiše gettere, settere, toString, equals i hashCode metode
@NoArgsConstructor // Generiše podrazumevani konstruktor bez argumenata
@AllArgsConstructor // Generiše konstruktor sa svim poljima
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int amount;


    @Column(nullable = false)
    private String type; // "BUY" ili "SELL"


}
