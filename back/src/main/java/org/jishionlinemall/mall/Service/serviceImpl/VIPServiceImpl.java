package org.jishionlinemall.mall.Service.serviceImpl;

import org.jishionlinemall.mall.DTO.ChangePasswordDTO;
import org.jishionlinemall.mall.DTO.ForgetPasswordDTO;
import org.jishionlinemall.mall.DTO.VIPRegisterDTO;
import org.jishionlinemall.mall.Entity.UserRepository;
import org.jishionlinemall.mall.Entity.VIP;
import org.jishionlinemall.mall.Entity.VIPRepository;
import org.jishionlinemall.mall.Service.VIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VIPServiceImpl implements VIPService {
    private VIPRepository vipRepository;
    private UserRepository userRepository;

    @Autowired
    public void setRepository(VIPRepository vipRepository, UserRepository userRepository){
        this.vipRepository = vipRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean isVIP(String openId){
        if(vipRepository.findByUserId(userRepository.findByOpenId(openId).getId()) != null)
        { return true;}
        else return false;
    }

    @Override
    public VIP queryVipInfo(String openId){
        return vipRepository.findByUserId(userRepository.findByOpenId(openId).getId());
    }

    @Override
    public boolean registerVIP(VIPRegisterDTO vipRegisterDTO){
        //如果已有重复信息则报错
        if(vipRepository.findByPhoneNumber(vipRegisterDTO.getPhoneNumber()) != null ||
                vipRepository.findByStudentId(vipRegisterDTO.getStudentId()) != null)
            return false;
        else {
            //存储VIP信息
            VIP vip = new VIP();
            vip.setName(vipRegisterDTO.getName());
            vip.setPassword("0000");//因为不再需要使用卡片，故不需要设置密码，后台设置默认密码为0000
            vip.setPhoneNumber(vipRegisterDTO.getPhoneNumber());
            vip.setStudentId(vipRegisterDTO.getStudentId());
            vip.setUserId(userRepository.findByOpenId(vipRegisterDTO.getOpenId()).getId());
            vipRepository.saveAndFlush(vip);
            return true;
        }
    }

    @Override
    public BigDecimal queryBalance(String openId){
        return vipRepository.findByUserId(userRepository.findByOpenId(openId).getId()).getBalance();
    }

    @Override
    public BigDecimal queryExpenditure(String openId){
        return vipRepository.findByUserId(userRepository.findByOpenId(openId).getId()).getExpenditure();
    }

    @Override
    public boolean verifyVIPInfo(ChangePasswordDTO changePasswordDTO){
        String trueStudentId = vipRepository.findByUserId(userRepository.findByOpenId(changePasswordDTO.getOpenId()).getId()).getStudentId();
        String  truePassword = vipRepository.findByUserId(userRepository.findByOpenId(changePasswordDTO.getOpenId()).getId()).getPassword();
        //如果根据openId从数据库中查询到的密码和学号和用户输入的密码和学号都一致，则返回true
        if(trueStudentId.equals(changePasswordDTO.getStudentId()) && truePassword.equals(changePasswordDTO.getOldPassword()))
            return true;
        else return false;
    }
    @Override
    public void changePassword(String openId, String password){
        VIP vip = vipRepository.findByUserId(userRepository.findByOpenId(openId).getId());
        vip.setPassword(password);
        vipRepository.saveAndFlush(vip);
    }

    @Override
    public void deductExpenses(String openId, BigDecimal fee){
        VIP vip = vipRepository.findByUserId(userRepository.findByOpenId(openId).getId());
        vip.setBalance(vip.getBalance().subtract(fee));
        vipRepository.saveAndFlush(vip);
    }

    @Override
    public boolean verifyVIPFogetInfo(ForgetPasswordDTO forgetPasswordDTO){
        VIP vip = vipRepository.findByUserId(userRepository.findByOpenId(forgetPasswordDTO.getOpenId()).getId());
        if(forgetPasswordDTO.getStudentId().equals(vip.getStudentId()) &&
                forgetPasswordDTO.getPhoneNumber().equals(vip.getPhoneNumber()))
            return true;
        else return false;
    }

    @Override
    public void rechargeMoney(String openId, BigDecimal fee){
        VIP vip = vipRepository.findByUserId(userRepository.findByOpenId(openId).getId());
        vip.setBalance(vip.getBalance().add(fee));
        vipRepository.saveAndFlush(vip);
    }
}
