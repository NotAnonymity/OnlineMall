package org.jishionlinemall.mall.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findBySn(String sn);

    List<Order> findByUserId(long userId);
}
