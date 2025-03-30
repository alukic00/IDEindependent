package com.teletrader.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@Table(name = "orders")
@NoArgsConstructor // Generiše podrazumevani konstruktor bez argumenata
@AllArgsConstructor // Generiše konstruktor sa svim poljima
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
    private Status status; // Podrazumevni status



    @PrePersist
    protected void onCreate() {
        this.status = Status.ACTIVE;
        this.createdAt = LocalDateTime.now()
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }


}
