package org.jishionlinemall.mall.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(long userId);
    Cart findByUserIdAndGoodsId(long userId, long goodsId);
}
