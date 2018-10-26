package org.jishionlinemall.mall.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jishionlinemall.mall.Entity.CategoryRepository;
import org.jishionlinemall.mall.Entity.Order;

import java.util.List;

public interface PurchaseService {
    JSONObject getCategories();
    JSONObject getGoods(Integer categoryId);
    JSONObject getCart(long userId);
    void cartAddGoods(long userId, long goodsId);
    String cartReduceGoods(long userId, long goodsId);
    void cartClean(long userId);
    JSONObject orderOp(long userId, Order order);
    JSONObject pay(long userId);
    JSONObject historyOrder(long userId);
}
