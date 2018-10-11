package org.jishionlinemall.mall.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VIPRepository extends JpaRepository<VIP, Long> {
    VIP findByUserId(Long userId);
    VIP findByPhoneNumber(String phoneNumber);
    VIP findByStudentId(String studentId);
    VIP findByStudentIdAndPassword(String studentId, Integer password);
}
