package org.jishionlinemall.mall.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findALLByVipId(Long vipId);
    Card findByVipIdAndStatus(Long vipId, String status);
    Card findFirst1ByStatusOrderByIdAsc(String status);
}
