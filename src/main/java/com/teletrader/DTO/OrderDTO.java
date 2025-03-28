package com.teletrader.DTO;

import com.teletrader.entity.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@AllArgsConstructor


public class OrderDTO {
    private double price;
    private int amount;
    private String type;

    // getteri i setteri
    public double getPrice() { return price; }
    public int getAmount() { return amount; }
    public String getType() { return type; }
    public void setPrice(double price) { this.price = price; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setType(String type) { this.type = type; }

}