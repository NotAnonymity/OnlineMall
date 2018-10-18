package org.jishionlinemall.mall.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//此表用于关联good和cart(多对多)

@Entity
@Table(name = "t_goods_order")
public class GoodsAndOrder {
    @Column(name = "gid", nullable = false)
    private Long gid;//商品id
    @Column(name = "oid", nullable = false)
    private Long oid;//订单id

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }
}
