package com.teletrader.repository;

import com.teletrader.entity.Order;
import com.teletrader.entity.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Pronalazi prvih 10 BUY naloga sortirano opadajuće po ceni
    List<Order> findTop10ByTypeOrderByPriceDesc(String type);

    // Pronalazi prvih 10 SELL naloga sortirano rastuće po ceni
    List<Order> findTop10ByTypeOrderByPriceAsc(String type);
}