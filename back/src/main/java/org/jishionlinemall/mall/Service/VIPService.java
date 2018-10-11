package org.jishionlinemall.mall.Service;

import org.jishionlinemall.mall.DTO.ChangePasswordDTO;
import org.jishionlinemall.mall.DTO.ForgetPasswordDTO;
import org.jishionlinemall.mall.DTO.VIPRegisterDTO;
import org.jishionlinemall.mall.Entity.VIP;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface VIPService {
    boolean isVIP(String openId);//根据userID判断是否是会员
    boolean registerVIP(VIPRegisterDTO vipRegisterDTO);//注册VIP会员
    BigDecimal queryBalance(String openId);//查询余额
    BigDecimal queryExpenditure(String openId);//查询消费额
    VIP queryVipInfo(String openId);//查询VIP信息
    boolean verifyVIPInfo(ChangePasswordDTO changePasswordDTO);//核实修改密码信息是否正确
    boolean verifyVIPFogetInfo(ForgetPasswordDTO forgetPasswordDTO);//核实忘记密码信息是否正确
    @Transactional
        // @Transactional 注解该方法为一个事务，在操作数据库失败时回滚到初始状态
    void changePassword(String openId, String password);//修改密码
    @Transactional
    void deductExpenses(String openId, BigDecimal fee);//扣除卡的费用
    @Transactional
    void rechargeMoney(String openId, BigDecimal fee);//充值费用
}
