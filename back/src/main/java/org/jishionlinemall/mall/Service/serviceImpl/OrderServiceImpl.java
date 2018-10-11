package org.jishionlinemall.mall.Service.serviceImpl;

import org.jishionlinemall.mall.Entity.Order;
import org.jishionlinemall.mall.Entity.OrderRepository;
import org.jishionlinemall.mall.Entity.UserRepository;
import org.jishionlinemall.mall.Entity.VIPRepository;
import org.jishionlinemall.mall.Service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    private Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderRepository orderRepository;
    private VIPRepository vipRepository;
    private UserRepository userRepository;

    @Autowired
    public void setRepository(OrderRepository orderRepository, VIPRepository vipRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.vipRepository = vipRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void generateOrder(String openId, BigDecimal fee, String sn){
        Order order = new Order();
        order.setSn(sn);
        order.setFee(fee);
        order.setUserId(userRepository.findByOpenId(openId).getId());
        order.setStatus("paid");
        orderRepository.saveAndFlush(order);
    }

    @Override
    public String generateOrderSn(){
        return generateSn();
    }

    @Override
    public boolean isRepeatOrder(String sn){
        Order order = orderRepository.findBySn(sn);
        if(order != null)
        {
            log.info("数据库查询订单号已存在");
            log.info(order.getSn()+" "+order.getStatus()+" "+order.getDate());
            return true;
        }
        else
        {
            log.info("数据库查询订单号不存在");
            return false;
        }
    }

    private String generateSn(){
        SimpleDateFormat time_format = new SimpleDateFormat("yyyyMMddHHmmss");
        return String.format("%s", time_format.format(new Date()));
    }
}
