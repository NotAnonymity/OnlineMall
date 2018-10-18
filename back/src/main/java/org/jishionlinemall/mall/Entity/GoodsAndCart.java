package org.jishionlinemall.mall.Entity;

import javax.persistence.*;

//此表用于关联good和cart(多对多)

@Entity
@Table(name = "t_goods_cart")
public class GoodsAndCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name = "gid",nullable = false)
    private Long gid;//商品id号
    @Column(name = "cid",nullable = false)
    private Long cid;//购物车id号

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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }
}
