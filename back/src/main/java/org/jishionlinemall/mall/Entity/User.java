package org.jishionlinemall.mall.Entity;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//必须添加主键生成策略的属性,IDENTITY：主键由数据库自动生成（主要是自动增长型）
    @Column(name = "id", nullable = false)  //标记字段属性，使用此nullable属性可以当数据为空时在JPA阶段就抛出异常，无需抛出数据库异常
    private Long id;
    @Column(name = "open_id", nullable = false)
    private String openId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}

