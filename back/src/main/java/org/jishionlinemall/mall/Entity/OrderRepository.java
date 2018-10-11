package org.jishionlinemall.mall.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findBySn(String sn);
}
