package org.jishionlinemall.mall.Entity;

import javax.persistence.*;

//此表用于关联good和cart(多对多)

@Entity
@Table(name = "t_goods_order")
public class GoodsAndOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = true)
    private Long id;
    @Column(name = "gid", nullable = false)
    private Long gid;//商品id
    @Column(name = "gAmount", nullable = false)
    private Integer gAmount;//购买商品数量
    @Column(name = "sn", nullable = false)
    private String sn;//订单id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }



    public Integer getgAmount() {
        return gAmount;
    }

    public void setgAmount(Integer gAmount) {
        this.gAmount = gAmount;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
