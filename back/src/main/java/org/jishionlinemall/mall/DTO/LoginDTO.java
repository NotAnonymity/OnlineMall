package org.jishionlinemall.mall.DTO;

import java.math.BigDecimal;

public class LoginDTO {
    private String code;//前端发来的code

    private String status;
    private String openId;
    private String isVip;//判断是否是VIP,yes是VIP，no不是VIP
    private String isNewUser;//判断是否是新用户
    private BigDecimal expenditure;//消费金额

    public String getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(String isNewUser) {
        this.isNewUser = isNewUser;
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public BigDecimal getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(BigDecimal expenditure) {
        this.expenditure = expenditure;
    }
}
