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

    // Pronalazi prvih 10 BUY naloga sortirano opadajuće po ceni
    List<Order> findTop10ByTypeOrderByPriceDesc(String type);

    // Pronalazi prvih 10 SELL naloga sortirano rastuće po ceni
    List<Order> findTop10ByTypeOrderByPriceAsc(String type);

    List<Order> findByStatus(Status status);

    @Query("SELECT o FROM Order o WHERE o.type = 'SELL' AND o.price <= ?1 AND o.status = 'ACTIVE' ORDER BY o.price ASC")
    List<Order> findMatchingSells(double maxPrice);

    // Za SELL: pronalazi BUY ordere sa cenom >= target ceni (sortirano opadajuće)
    @Query("SELECT o FROM Order o WHERE o.type = 'BUY' AND o.price >= ?1 AND o.status = 'ACTIVE' ORDER BY o.price DESC")
    List<Order> findMatchingBuys(double minPrice);

    // Za BUY ordere (traži SELL)
    List<Order> findByTypeAndStatusAndPriceLessThanEqualOrderByPriceAscCreatedAtAsc(
            String type, Status status, double maxPrice);

    // Za SELL ordere (traži BUY)
    List<Order> findByTypeAndStatusAndPriceGreaterThanEqualOrderByPriceDescCreatedAtAsc(
            String type, Status status, double minPrice);
}