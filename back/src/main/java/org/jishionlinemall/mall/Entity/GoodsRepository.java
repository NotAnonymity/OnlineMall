package org.jishionlinemall.mall.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    List<Goods> findByCategoryId(Integer categoryId);
    Goods findById(long gid);
}
