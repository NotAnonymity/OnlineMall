package org.jishionlinemall.mall.Controller;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.jishionlinemall.mall.DTO.LoginDTO;
import org.jishionlinemall.mall.Service.UserService;
import org.jishionlinemall.mall.Service.VIPService;
import org.jishionlinemall.mall.Util.UtilHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    private UserService userService;
    private VIPService vipService;

    @Autowired
    public void registerService(UserService userService, VIPService vipService){
        this.userService = userService;
        this.vipService = vipService;
    }

    @Value("testAppId") //根据properties的配置来加载APPID,注意不要留空格
    private String appId;
    @Value("testAppSecret")
    private String appSecret;

    @RequestMapping("/getOpenId")
    public LoginDTO getOpenId(@RequestBody LoginDTO loginDTO){
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        String requestUrl = WX_URL.replace("APPID", appId).replace("SECRET", appSecret).replace("JSCODE", loginDTO.getCode());
        try {
            log.info("申请openid和session_key,对应URL:"+requestUrl);
            JSONObject jsonObject = UtilHttpRequest.httpsRequest(requestUrl, "GET", null);
            if (jsonObject != null) {
                try {
                    loginDTO.setOpenId(jsonObject.getString("openid"));
                    //如果判断是新用户，则在数据库中注册新用户
                    if(userService.isNewUser(jsonObject.getString("openid"))){
                        userService.insertNewUser(jsonObject.getString("openid"));
                        loginDTO.setStatus("success");
                        loginDTO.setIsNewUser("yes");
                        loginDTO.setIsVip("no");
                    }
                    else {
                        //如果不是新用户，判断是否是VIP
                        if(vipService.isVIP(jsonObject.getString("openid")))
                        {
                            loginDTO.setStatus("success");
                            loginDTO.setIsVip("yes");
                            loginDTO.setIsNewUser("no");
                            loginDTO.setExpenditure(vipService.queryExpenditure(jsonObject.getString("openid")));
                        }
                        else {
                            loginDTO.setStatus("success");
                            loginDTO.setIsNewUser("no");
                            loginDTO.setIsVip("no");
                        }
                    }
                } catch (JSONException e) {
                    log.info("获取openId失败");
                    loginDTO.setStatus("fail");
                }
            }else {
                loginDTO.setStatus("fail");
            }
        } catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return loginDTO;
    }
}
