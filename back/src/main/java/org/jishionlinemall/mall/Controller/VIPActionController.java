package org.jishionlinemall.mall.Controller;

import com.github.wxpay.sdk.WXPay;
import net.sf.json.JSONObject;
import org.jishionlinemall.mall.DTO.*;
import org.jishionlinemall.mall.Entity.Card;
import org.jishionlinemall.mall.Entity.VIP;
import org.jishionlinemall.mall.Service.CardService;
import org.jishionlinemall.mall.Service.OrderService;
import org.jishionlinemall.mall.Service.VIPService;
import org.jishionlinemall.mall.Util.MyWXPayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vip")
public class VIPActionController {
    private Logger log = LoggerFactory.getLogger(VIPActionController.class);
    private VIPService vipService;
    private CardService cardService;
    private OrderService orderService;

    @Autowired
    public void registService(VIPService vipService, CardService cardService, OrderService orderService) {
        this.vipService = vipService;
        this.cardService = cardService;
        this.orderService = orderService;
    }

    //VIP用户注册
    @RequestMapping("/register")
    public VIPRegisterDTO registerVip(@RequestBody VIPRegisterDTO vipRegisterDTO) {
        try {
            if (vipService.registerVIP(vipRegisterDTO)) {
                log.info("注册会员成功");
                //添加新卡并将VIP账户与此卡片绑定
                cardService.addNewCard(vipService.queryVipInfo(vipRegisterDTO.getOpenId()).getId());
                vipRegisterDTO.setStatus("success");
            } else {
                vipRegisterDTO.setStatus("fail");
                vipRegisterDTO.setErrMsg("注册会员失败，已有重复学号或手机号");
            }
        } catch (Exception e) {
            log.info("注册会员失败");
            vipRegisterDTO.setStatus("fail");
            vipRegisterDTO.setErrMsg("连接数据库失败");
            e.printStackTrace();
        }
        return vipRegisterDTO;
    }


    //获取VIP的信息
    @RequestMapping("/getInfo")
    public VIPInfoDTO getVipInfo(@RequestBody VIPInfoDTO vipInfoDTO) {
        try {
            VIP vip = vipService.queryVipInfo(vipInfoDTO.getOpenId());
            vipInfoDTO.setBalance(vip.getBalance());
            vipInfoDTO.setExpenditure(vip.getExpenditure());
            vipInfoDTO.setName(vip.getName());
            //发送VIP卡的卡号，如果会员没有"using"状态的卡，则返回卡号信息为null
            Card card = cardService.findCardByOpenId(vipInfoDTO.getOpenId());
            if (card == null) {
                vipInfoDTO.setCardNumber(null);
            } else {
                vipInfoDTO.setCardNumber(card.getNumber());
            }
            vipInfoDTO.setStatus("success");
        } catch (Exception e) {
            vipInfoDTO.setStatus("fail");
            vipInfoDTO.setErrMsg("查询数据库失败");
        }
        return vipInfoDTO;
    }

    //修改VIP密码
    @RequestMapping("/changePassword")
    public ChangePasswordDTO changeInfo(@RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            if (vipService.verifyVIPInfo(changePasswordDTO)) {
                vipService.changePassword(changePasswordDTO.getOpenId(), changePasswordDTO.getNewPassword());
                log.info("修改密码成功");
                changePasswordDTO.setStatus("success");
            } else {
                changePasswordDTO.setStatus("fail");
                changePasswordDTO.setErrMsg("密码或学号错误");
            }
        } catch (Exception e) {
            log.info("修改密码失败");
            changePasswordDTO.setStatus("fail");
            changePasswordDTO.setErrMsg("连接数据库失败");
        }
        return changePasswordDTO;
    }

    //申报挂失并补办会员卡
    @RequestMapping("/reportLoss")
    public ReportLossDTO reportLoss(@RequestBody ReportLossDTO reportLossDTO) {
        //将会员账户对应的卡片信息进行挂失处理
        try {
            cardService.reportTheLoss(reportLossDTO);
            log.info("挂失卡片成功");
            reportLossDTO.setLossStatus("success");
        } catch (Exception e) {
            log.info(("挂失卡片失败"));
            reportLossDTO.setLossStatus("fail");
        }
        //扣取账户内5元作为补办卡片费，如账户余额不足则设置状态为失败
        try {

            Card card = cardService.findUnusedCard();//查找未使用的卡片
            if (card != null) {
                //设置补卡费为5元
                BigDecimal serviceCharge = new BigDecimal(5.0);
                int result = vipService.queryBalance(reportLossDTO.getOpenId()).compareTo(serviceCharge);
                //如果账户余额大于5元则进行补卡操作
                if (result == 0 || result == 1) {
                    //将未使用卡片与会员账户绑定
                    cardService.bandCardAndVIP(card, reportLossDTO.getOpenId());
                    //扣除费用
                    vipService.deductExpenses(reportLossDTO.getOpenId(), serviceCharge);
                    //生成订单
                    orderService.generateOrder(reportLossDTO.getOpenId(), serviceCharge, orderService.generateOrderSn());
                    reportLossDTO.setNewCardStatus("success");
                } else {
                    reportLossDTO.setNewCardStatus("fail");
                    reportLossDTO.setErrMsg("余额不足，请充值");
                }
            } else {
                reportLossDTO.setNewCardStatus("fail");
                reportLossDTO.setErrMsg("会员卡片不足，请联系管理员增加卡片");
            }

        } catch (Exception e) {
            e.printStackTrace();
            reportLossDTO.setNewCardStatus("fail");
            reportLossDTO.setErrMsg("连接数据库失败");
        }
        return reportLossDTO;
    }

    //找回密码
    @RequestMapping("/forgetPassword")
    public ForgetPasswordDTO forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        try {
            if (vipService.verifyVIPFogetInfo(forgetPasswordDTO)) {
                forgetPasswordDTO.setStatus("success");
            } else {
                forgetPasswordDTO.setStatus("fail");
                forgetPasswordDTO.setErrMsg("账户信息验证失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            forgetPasswordDTO.setStatus("fail");
            forgetPasswordDTO.setErrMsg("连接数据库失败");
        }
        return forgetPasswordDTO;
    }

    //政策信息
    @RequestMapping("/policyInfo")
    public JSONObject getPolicyInfo() {
        JSONObject info1 = new JSONObject();
        JSONObject info2 = new JSONObject();
        JSONObject INFO = new JSONObject();
        info1.put("title", "会员优惠说明");
        info1.put("content", "会员卡支付全场98折，一次性充值满一定金额还可享受额外优惠");
        info2.put("title", "会员制度说明");
        info2.put("content", "根据会员积分，可享受特别折扣");
        INFO.put("1", info1);
        INFO.put("2", info2);
        return INFO;
    }

    //充值
    @RequestMapping("/rechargeExpense")
    public RechargeExpenseDTO rechargeExpense(@RequestBody RechargeExpenseDTO rechargeExpenseDTO) {
        MyWXPayConfig config = new MyWXPayConfig();
        WXPay wxPay = new WXPay(config);
        Map<String, String> data = new HashMap<>();
        data.put("body", "jishiXCX");
        data.put("out_trade_no", orderService.generateOrderSn());
        //前端传来的数据单位为元，微信端接收的支付单位是分，在此需要将元转换为分
        BigDecimal transform = new BigDecimal(100);//设置转换倍数
        String pennyFee = rechargeExpenseDTO.getFee().multiply(transform).toString();//转换后的价格
        data.put("total_fee", pennyFee);
        data.put("spbill_create_ip", "123.12.12.123");//终端Ip，随意填
        data.put("notify_url", "https://api2.fyscu.xyz/vip/WXPayBack");
        data.put("trade_type", "JSAPI");
        data.put("openid", rechargeExpenseDTO.getOpenId());
        try {
            Map<String, String> resp = wxPay.unifiedOrder(data);
            if (resp.get("return_code").equals("FAIL")) {
                //请求失败
                rechargeExpenseDTO.setErrMsg(resp.get("return_msg"));
                log.info("通信失败");
                rechargeExpenseDTO.setStatus("fail");
            } else {
                if (resp.get("result_code").equals("FAIL")) {
                    //业务执行失败
                    rechargeExpenseDTO.setErrMsg(resp.get("err_code_des"));
                    log.info("业务执行失败");
                    rechargeExpenseDTO.setStatus("fail");
                } else {
                    //设置返回信息
                    rechargeExpenseDTO.setAppId(resp.get("appid"));
                    rechargeExpenseDTO.setDataPackage("prepay_id=" + resp.get("prepay_id"));
                    rechargeExpenseDTO.setNonceStr(resp.get("nonce_str"));
                    rechargeExpenseDTO.setTimeStamp(String.valueOf((new Date()).getTime() / 1000));
                    rechargeExpenseDTO.setSignType("MD5");
                    rechargeExpenseDTO.setStatus("success");
                    //创建签名
                    Map<String, String> createSign = new HashMap<>();
                    createSign.put("appId", resp.get("appid"));
                    createSign.put("nonceStr", resp.get("nonce_str"));
                    createSign.put("package=prepay_id", resp.get("prepay_id"));
                    createSign.put("timeStamp", String.valueOf((new Date()).getTime() / 1000));
                    createSign.put("signType", "MD5");
                    String paySign = com.github.wxpay.sdk.WXPayUtil.generateSignature(createSign, "q6057258741801110870518011108705");
                    rechargeExpenseDTO.setPaySign(paySign);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rechargeExpenseDTO;
    }

    //微信回调
    @RequestMapping("/WXPayBack")
    public String wxPayBack(@RequestBody String strXML) throws Exception {
        log.info("开始执行微信支付回调函数");
        //解析微信端发来的xml信息
        Map<String, String> resp = com.github.wxpay.sdk.WXPayUtil.xmlToMap(strXML);
        //设置返回微信端的信息
        Map<String, String> reback = new HashMap<>();

        if (resp.get("return_code").equals("SUCCESS")) {
            log.info("微信支付回调成功");
            //如果微信端发来的数据为重复消息，则返回成功消息,不再进行操作
            if (orderService.isRepeatOrder(resp.get("out_trade_no"))) {
                log.info("微信支付回调为重复消息");
                log.info("微信支付返回的商户订单号为" + resp.get("out_trade_no"));
                reback.put("return_code", "SUCCESS");
                return com.github.wxpay.sdk.WXPayUtil.mapToXml(reback);
            } else {
                if (resp.get("result_code").equals("SUCCESS")) {
                    log.info("微信支付回调支付成功");
                    //支付成功
                    //微信端转换的金额单位是分， 在此将其转化为元
                    BigDecimal transform = new BigDecimal(100);//设置转换倍数
                    BigDecimal totalFee = new BigDecimal(resp.get("total_fee"));//微信传来的订单价格，单位为分
                    BigDecimal yuanFee = totalFee.divide(transform);//实际订单价格，单位为元
                    orderService.generateOrder(resp.get("openid"), yuanFee, resp.get("out_trade_no"));//生成订单
                    yuanFee = activityRecharge(yuanFee);//进行充值优惠计算
                    vipService.rechargeMoney(resp.get("openid"), yuanFee);//会员账户充值
                }
                //告知微信已成功收到消息
                reback.put("return_code", "SUCCESS");
                return com.github.wxpay.sdk.WXPayUtil.mapToXml(reback);
            }
        } else {
            log.info("微信支付回调失败");
            reback.put("return_code", "FAIL");
            reback.put("return_msg", "支付通知失败");
            return com.github.wxpay.sdk.WXPayUtil.mapToXml(reback);
        }
    }

    //根据前端传来的金额进行优惠活动。满30送1元，满50送2元，满100元送5元
    private BigDecimal activityRecharge(BigDecimal yuanFee){
        BigDecimal [] hundredCountAndReminder = yuanFee.divideAndRemainder(BigDecimal.valueOf(100));
        BigDecimal finallyFee = yuanFee;
        finallyFee = finallyFee.add(hundredCountAndReminder[0].multiply(BigDecimal.valueOf(5)));
        if(hundredCountAndReminder[1].compareTo(BigDecimal.valueOf(50)) != -1){
            finallyFee = finallyFee.add(BigDecimal.valueOf(2));
        }
        else if((hundredCountAndReminder[1].compareTo(BigDecimal.valueOf(30)) != -1)){
            finallyFee = finallyFee.add(BigDecimal.valueOf(1));
        }
        return finallyFee;
    }
}
