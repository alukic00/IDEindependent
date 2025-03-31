package com.teletrader.entity;

import jakarta.persistence.*;
import lombok.*;
;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private double totalPrice;


    @Column(nullable = false)
    private String type; // "BUY" ili "SELL"

    @Column(columnDefinition = "TEXT", nullable = false)
    private String createdAt;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;



    @PrePersist
    protected void onCreate() {
        this.status = Status.ACTIVE;
        this.createdAt = LocalDateTime.now()
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }


}
