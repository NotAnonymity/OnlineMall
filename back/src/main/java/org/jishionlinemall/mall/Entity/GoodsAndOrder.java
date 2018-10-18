package org.jishionlinemall.mall.Entity;

import javax.persistence.*;

//此表用于关联good和cart(多对多)

@Entity
@Table(name = "t_goods_order")
public class GoodsAndOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name = "gid", nullable = false)
    private Long gid;//商品id
    @Column(name = "oid", nullable = false)
    private Long oid;//订单id

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

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }
}
