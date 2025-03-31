package com.teletrader.repository;

import com.teletrader.entity.Order;
import com.teletrader.entity.OrderType;
import com.teletrader.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findTop10ByTypeOrderByPriceDesc(String type);


    List<Order> findTop10ByTypeOrderByPriceAsc(String type);

    List<Order> findByStatus(Status status);

    @Query("SELECT o FROM Order o WHERE o.type = 'SELL' AND o.price <= ?1 AND o.status = 'ACTIVE' ORDER BY o.price ASC")
    List<Order> findMatchingSells(double maxPrice);


    @Query("SELECT o FROM Order o WHERE o.type = 'BUY' AND o.price >= ?1 AND o.status = 'ACTIVE' ORDER BY o.price DESC")
    List<Order> findMatchingBuys(double minPrice);


    List<Order> findByTypeAndStatusAndPriceLessThanEqualOrderByPriceAscCreatedAtAsc(
            String type, Status status, double maxPrice);


    List<Order> findByTypeAndStatusAndPriceGreaterThanEqualOrderByPriceDescCreatedAtAsc(
            String type, Status status, double minPrice);
}