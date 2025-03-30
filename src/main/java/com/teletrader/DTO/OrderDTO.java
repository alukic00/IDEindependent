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



}