package org.jishionlinemall.mall.Service.serviceImpl;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jishionlinemall.mall.Entity.*;
import org.jishionlinemall.mall.Service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private Logger log = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    private CategoryRepository categoryRepository;
    private GoodsRepository goodsRepository;
    private  CartRepository cartRepository;
    private GoodsAndOrderRepository goodsAndOrderRepository;
    private OrderRepository orderRepository;
    private VIPRepository vipRepository;

    @Autowired
    public void setRepository(CategoryRepository categoryRepository, GoodsRepository goodsRepository, CartRepository cartRepository,
                              GoodsAndOrderRepository goodsAndOrderRepository, OrderRepository orderRepository, VIPRepository vipRepository){
        this.categoryRepository = categoryRepository;
        this.goodsRepository = goodsRepository;
        this.cartRepository = cartRepository;
        this.goodsAndOrderRepository = goodsAndOrderRepository;
        this.orderRepository = orderRepository;
        this.vipRepository = vipRepository;
    }
    @Override
    public JSONObject getCategories(){
        List<Category> categories = categoryRepository.findAll();
        JSONObject rtn = new JSONObject();
        JSONArray arrayList = new JSONArray();
        for(Category i : categories){
            JSONObject tmp = new JSONObject();
            tmp.put("id",i.getId().toString());
            tmp.put("name",i.getName());
            arrayList.add(tmp);
        }

        rtn.put("arrayList", arrayList.toString());
        return rtn;
    }

    @Override
    public JSONObject getGoods(Integer categoryId){
        JSONObject rtn = new JSONObject();
        List<Goods> goods = goodsRepository.findByCategoryId(categoryId);
        JSONArray arrayList = new JSONArray();
        for(Goods i : goods){
            JSONObject tmp = new JSONObject();
            tmp.put("id",i.getId().toString());
            tmp.put("name",i.getName());
            tmp.put("amount",i.getAmount().toString());
            tmp.put("categoryId",i.getCategoryId().toString());
            tmp.put("cost",i.getCost().toString());
            tmp.put("imageUrl",i.getImageUrl());
            tmp.put("price",i.getPrice().toString());
            tmp.put("sellCount",i.getSellCount().toString());
            arrayList.add(tmp);
        }
        rtn.put("arrayList", arrayList.toString());
        return rtn;
    }

    @Override
    public  JSONObject getCart(long userId){
        JSONObject rtn = new JSONObject();
        List<Cart> cartInfo= cartRepository.findByUserId(userId);
        JSONArray arrayList = new JSONArray();
        for(Cart i : cartInfo){
            JSONObject tmp = new JSONObject();
            tmp.put("id",i.getId().toString());
            tmp.put("userId",i.getUserId().toString());
            tmp.put("goodsId",i.getGoodsId().toString());
            tmp.put("goodsAmount",i.getGoodsAmount().toString());
            arrayList.add(tmp);
        }
        rtn.put("arrayList", arrayList.toString());
        return rtn;
    }
    @Override
    public void cartAddGoods(long userId, long goodsId){
        Cart tmp = cartRepository.findByUserIdAndGoodsId(userId, goodsId);
        if(tmp == null){
            tmp = new Cart();
            tmp.setUserId(userId);
            tmp.setGoodsId(goodsId);
            tmp.setGoodsAmount(1);
        }
        else
            tmp.setGoodsAmount(tmp.getGoodsAmount()+1);

        cartRepository.saveAndFlush(tmp);
    }
    @Override
    public String cartReduceGoods(long userId, long goodsId){
        Cart tmp = cartRepository.findByUserIdAndGoodsId(userId, goodsId);
        String err = "";
        if(tmp == null){
            err = "对不存在的物品减数";
            return err;
        }
        tmp.setGoodsAmount(tmp.getGoodsAmount()-1);

        cartRepository.saveAndFlush(tmp);
        return err;
    }

    @Override
    public void cartClean(long userId){
        List<Cart> cartInfo = cartRepository.findByUserId(userId);
        if(cartInfo != null)
            cartRepository.deleteAll(cartInfo);
    }

    @Override
    public JSONObject orderOp(long userId, Order order){
        JSONObject rtn = new JSONObject();
        List<Cart> cartList = cartRepository.findByUserId(userId);
        BigDecimal fee = new BigDecimal(0);
        long flag = 0;
        VIP user = vipRepository.findByUserId(userId);
        if(user == null)    log.info("该用户不存在");
        for(Cart i : cartList){
            Goods goods = goodsRepository.findById((long)i.getGoodsId());
            if(goods.getAmount().compareTo(i.getGoodsAmount()) == -1){
                flag = i.getGoodsId();
            }
            GoodsAndOrder goodsAndOrder = new GoodsAndOrder();
            goodsAndOrder.setGid(i.getGoodsId());
            goodsAndOrder.setSn(order.getSn());
            goodsAndOrder.setgAmount(i.getGoodsAmount());
            goodsAndOrderRepository.saveAndFlush(goodsAndOrder);

            fee.add(goods.getCost().multiply(new BigDecimal(i.getGoodsAmount())));
        }
        if(flag != 0){
            rtn.put("status","fail");
            rtn.put("msg","货物"+flag+"不足");
            order.setStatus("fail");
        }
        else if(user.getBalance().compareTo(fee) == -1){
            rtn.put("status","fail");
            rtn.put("msg","余额不足");
            order.setStatus("fail");
        }
        else{

            for(Cart i : cartList) {
                Goods goods = goodsRepository.findById((long) i.getGoodsId());
                goods.setAmount(goods.getAmount() - i.getGoodsAmount());
                goods.setSellCount(goods.getSellCount() + 1);
                fee.add(goods.getCost().multiply(new BigDecimal(i.getGoodsAmount())));
            }
            cartRepository.deleteAll(cartList);
            user.setExpenditure(user.getExpenditure().add(fee));
            order.setFee(fee);
            rtn.put("status","success");
            rtn.put("msg","");
            order.setStatus("paid");

        }
        return rtn;
    }


    @Override
    public JSONObject pay(long userId){//仍有bug
        JSONObject rtn = new JSONObject();
        List<Order>  orderList = orderRepository.findByUserId(userId);
        //这里逻辑有问题，我们究竟支持怎样的用户-订单关系？
        if(orderList.isEmpty()) {
            Order order = new Order();
            order.setDate(new java.sql.Date(new Date().getTime()));
            order.setUserId(userId);
            order.setFee(new BigDecimal(0));
            order.setSn(String.format("%s",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
            rtn = orderOp(userId, order);
            orderRepository.saveAndFlush(order); // error:Field 'date' doesn't have a default value.OrderServiceImpl 主键没自增但可以实现saveandflush
        }else{  //这一块要改。
            for(Order i : orderList){
                rtn = orderOp(userId,i);
            }
        }

        return rtn;
    }

    @Override
    public JSONObject historyOrder(long userId){
        JSONObject rtn = new JSONObject();
        JSONArray array = new JSONArray();
        List<Order> orderList = orderRepository.findByUserId(userId);
        for(Order i : orderList){
            JSONObject tmp = new JSONObject();
            tmp.put("id",i.getId().toString());
            tmp.put("date",i.getDate().toString());
            tmp.put("fee",i.getFee().toString());
            tmp.put("status",i.getStatus().toString());
            List<GoodsAndOrder> goodsAndOrderList = goodsAndOrderRepository.findBySn(i.getSn());
            JSONArray goodsInfoArray = new JSONArray();
            for(GoodsAndOrder j : goodsAndOrderList){
                long gid = j.getGid();
                Goods goods = goodsRepository.findById(gid);
                JSONObject goodsInfo = new JSONObject();
                goodsInfo.put("goodsName",goods.getName());
                goodsInfo.put("goodsAmount",j.getgAmount().toString());
                goodsInfo.put("goodsPrice",goods.getPrice().toString());
                goodsInfo.put("goodsImage_url",goods.getImageUrl());
                goodsInfoArray.add(goodsInfo);
            }
            tmp.put("goodsInfo",goodsInfoArray.toString());
            array.add(tmp);
        }
        rtn.put("arraylist",array.toString());
        return rtn;
    }


}
