package org.jishionlinemall.mall.Controller;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.jishionlinemall.mall.Entity.Goods;
import org.jishionlinemall.mall.Service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mall")
public class PurchaseController {
    private  Logger log = LoggerFactory.getLogger(PurchaseController.class);
    private PurchaseService purchaseService;

    @Autowired
    public void setPurchaseService(PurchaseService purchaseService){
        this.purchaseService = purchaseService;
    }
    @RequestMapping("/getCategoryList")
    public JSONObject getCategoryList(){
        JSONObject rtn = new JSONObject();
        try{
            rtn.put("data",purchaseService.getCategories().toString());
            rtn.put("status","success");
            rtn.put("msg","");

        }catch(Exception e){
            e.printStackTrace();
            log.info("获取分类表失败");
            rtn.put("status","fail");
            rtn.put("msg","连接数据库失败");
        }
       return rtn;
    }

    @RequestMapping("/getGoodsList")
    public JSONObject getGoodsList(@RequestBody  String in){
        JSONObject GoodsListId = JSONObject.fromObject(in);
        JSONObject rtn = new JSONObject();
        try{
            Integer cid = GoodsListId.getInt("categoryId");
            rtn.put("data",purchaseService.getGoods(cid).toString());
            rtn.put("status","success");
            rtn.put("msg","");
        }catch (Exception e){
            e.printStackTrace();
            log.info("获取类型"+GoodsListId.getString("categoryId")+"的商品表失败");
            rtn.put("status","fail");
            rtn.put("msg","连接数据库失败");
        }
        return rtn;
    }

    @RequestMapping("/getCartInfo")
    public JSONObject getCartInfo(@RequestBody String in){
        JSONObject userId = JSONObject.fromObject(in);
        JSONObject rtn = new JSONObject();
        try{
            long uId = userId.getLong("userId");
            rtn.put("data",purchaseService.getCart(uId).toString());
            rtn.put("status","success");
            rtn.put("msg","");
        }catch (Exception e){
            e.printStackTrace();
            log.info("获取用户"+userId.getString("userId")+"的购物车失败");
            rtn.put("status","fail");
            rtn.put("msg","连接数据库失败");
        }
        return rtn;
    }

    @RequestMapping("/addGoodsToCart")
    public JSONObject addGoodsToCart(@RequestBody String in){
        JSONObject cartInfo = JSONObject.fromObject(in);
        JSONObject rtn = new JSONObject();
        try{
            long userId = cartInfo.getLong("userId");
            long goodsId = cartInfo.getLong("goodsId");
            purchaseService.cartAddGoods(userId, goodsId);
            rtn.put("data","");
            rtn.put("status","success");
            rtn.put("msg","");
        }catch (Exception e){
            e.printStackTrace();
            log.info("对用户"+cartInfo.getString("userId")+"添加物品"+cartInfo.getString("goodsId")+"到购物车失败");
            rtn.put("status","fail");
            rtn.put("msg","连接数据库失败");
        }
        return rtn;
    }

    @RequestMapping("/reduceCartGoods")
    public JSONObject reduceCartGoods(@RequestBody String in){
        JSONObject cartInfo = JSONObject.fromObject(in);
        JSONObject rtn = new JSONObject();
        try{
            long userId = cartInfo.getLong("userId");
            long goodsId = cartInfo.getLong("goodsId");
            String msg = purchaseService.cartReduceGoods(userId, goodsId);
            rtn.put("data","");
            rtn.put("status","success");
            rtn.put("msg", msg);
        }catch (Exception e){
            e.printStackTrace();
            log.info("对用户"+cartInfo.getString("userId")+"删除物品到购物车失败");
            rtn.put("status","fail");
            rtn.put("msg","连接数据库失败");
        }
        return rtn;
    }

    @RequestMapping("/cleanCart")
    public JSONObject cleanCart(@RequestBody String in){
        JSONObject uId = JSONObject.fromObject(in);
        JSONObject rtn = new JSONObject();
        try{
            long userId = uId.getLong("userId");
            purchaseService.cartClean(userId);
            rtn.put("data","");
            rtn.put("status","success");
            rtn.put("msg", "");
        }catch (Exception e){
            e.printStackTrace();
            log.info("对用户"+uId.getString("userId")+"清空购物车失败");
            rtn.put("status","fail");
            rtn.put("msg","连接数据库失败");
        }
        return rtn;
    }

    @RequestMapping("/payCart")
    public JSONObject payCart(@RequestBody String in){
        JSONObject uId = JSONObject.fromObject(in);
        JSONObject rtn = new JSONObject();

        try{
            rtn = purchaseService.pay(uId.getInt("userId"));
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户"+uId.getString("userId")+"结算购物车失败");
            rtn.put("status","fail");
            rtn.put("msg","连接数据库失败");
        }
        return rtn;
    }

    @RequestMapping("/getHistoryOrder")
    public JSONObject getHistoryOrder(@RequestBody String in){
        JSONObject uId = JSONObject.fromObject(in);
        JSONObject rtn = new JSONObject();

        try{
            rtn.put("data",purchaseService.historyOrder(uId.getInt("userId")));
            rtn.put("status","success");
            rtn.put("msg","");
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户"+uId.getString("userId")+"历史订单获取失败");
            rtn.put("status","fail");
            rtn.put("msg","连接数据库失败");
        }
        return rtn;
    }
}
