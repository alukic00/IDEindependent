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
