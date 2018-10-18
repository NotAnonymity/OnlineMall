package org.jishionlinemall.mall.Entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "t_goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "cost", nullable = false)
    private BigDecimal cost = new BigDecimal(0.00);//进价,初始值为0.00
    @Column(name = "price", nullable = false)
    private BigDecimal price = new BigDecimal(0.00);//售价,初始值为0.00
    @Column(name = "amount", nullable = false)
    private Integer amount = 0;//数量,设置默认值为0
    @Column(name = "image_url", nullable = false)
    private String imageUrl;//商品图片存储地址
    @Column(name = "sell_count", nullable = false)
    private Integer sellCount = 0;//商品在某一时间段的销售量，初始值为0
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;//商品种类id号

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
