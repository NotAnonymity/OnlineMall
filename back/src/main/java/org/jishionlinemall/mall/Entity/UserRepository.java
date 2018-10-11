package org.jishionlinemall.mall.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findById(long Id);
    User findByOpenId(String openId);
}
