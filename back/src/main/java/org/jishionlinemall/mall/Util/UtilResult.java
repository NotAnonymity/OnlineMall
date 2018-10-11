package org.jishionlinemall.mall.Util;

import net.sf.json.JSONObject;

public class UtilResult {
    public JSONObject Result(String status, String errMsg, JSONObject jsonObject)
    {
        JSONObject jsonObjectResult = new JSONObject();
        jsonObjectResult.put("status", status);
        jsonObjectResult.put("errMsg", errMsg);
        jsonObjectResult.put("data", jsonObject);
        return jsonObject;
    }
}
