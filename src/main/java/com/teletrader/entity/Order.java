package com.teletrader.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;
import java.util.Date;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt; // Postavlja trenutno vreme

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status; // Podrazumevni status

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.status = Status.ACTIVE;
    }


    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    // Setter metode
    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Dodatno: toString() metoda za bolje logovanje
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                '}';
    }

}
