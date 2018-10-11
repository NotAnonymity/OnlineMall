package org.jishionlinemall.mall.Entity;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name="status", nullable = false)
    private String status; //表示此卡片的使用状态，便于丢失时的挂失操作，包括unused,used,lost三种状态,，默认为unused状态
    @Column(name="number",nullable = false)
    private String number;//卡片号码，展示给用户，便于领取卡片
    @Column(name = "vip_id",nullable = false)
    private Long vipId;
    @Column(name = "create_time",nullable = false)
    @Generated(GenerationTime.INSERT)//自动生成日期
    private Timestamp createTime;
    @Column(name = "rfid")
    private String rfid;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVipId() {
        return vipId;
    }

    public void setVipId(Long vipId) {
        this.vipId = vipId;
    }
}
