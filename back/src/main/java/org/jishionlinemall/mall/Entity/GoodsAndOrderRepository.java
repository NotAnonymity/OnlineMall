package org.jishionlinemall.mall.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodsAndOrderRepository extends JpaRepository<GoodsAndOrder, Long> {
   // @Query(value = "select u.gid from GoodsAndOrderRepository u where o.oid == %nn");
    //List<Goods> findByOid(@Param("nn") long oid);
    List<GoodsAndOrder> findBySn(String sn);
}
