package org.jishionlinemall.mall.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface OrderService {
    @Transactional
    void generateOrder(String openId, BigDecimal fee, String sn);//生成订单
    String generateOrderSn();//生成订单sn码
    boolean isRepeatOrder(String sn);//校验是否有重复订单
}
