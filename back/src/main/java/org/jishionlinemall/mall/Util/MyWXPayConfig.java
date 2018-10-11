package org.jishionlinemall.mall.Util;

import java.io.InputStream;

public class MyWXPayConfig implements com.github.wxpay.sdk.WXPayConfig{
    public String getAppID(){
        return "wx3f6d33cd55e6f064";
    }
    public String getMchID(){
        return "1510077381";
    }
    public String getKey(){
        return "q6057258741801110870518011108705";
    }
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    //获取证书文件的方法，具体实现在官方JDK的README有
    // 因为证书只有在退款时才需要用到，所以在此不实现此方法
    public InputStream getCertStream() {
        return null;
    }
}
