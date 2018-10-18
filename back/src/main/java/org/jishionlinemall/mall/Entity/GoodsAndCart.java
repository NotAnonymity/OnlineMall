package org.jishionlinemall.mall.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//此表用于关联good和cart(多对多)

@Entity
@Table(name = "t_goods_cart")
public class GoodsAndCart {
    @Column(name = "gid",nullable = false)
    private Long gid;//商品id号
    @Column(name = "cid",nullable = false)
    private Long cid;//购物车id号

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
