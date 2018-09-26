package com.zz.sandbox.shujuyuan.bairong;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.bfd.facade.MerchantServer;
import com.bfd.util.ParamUtils;

public class StrategyApiTest{

    private static volatile String tokenid;
    private static MerchantServer ms=new MerchantServer();;

    public static String getTokenid(){
        if(StringUtils.isBlank(tokenid)){
            synchronized (StrategyApiTest.class) {
                if(StringUtils.isBlank(tokenid)){
                    tokenid = generateTokenid();
                }
            }
        }
        return tokenid;
    }

    private static String generateTokenid(){
        String tokenid = "";
        try {
            //登陆
            String userName = "mashangxw";
            String pwd = "mashangxw";
            String loginName = "LoginApi";
            String apiCode = "3002859";
            String login_res_str = ms.login(userName, pwd, loginName, apiCode);
            System.out.println(login_res_str);
            if(StringUtils.isNotBlank(login_res_str)){
                JSONObject loginJson=JSONObject.fromObject(login_res_str);
                if(loginJson.containsKey("tokenid")){
                    tokenid = loginJson.getString("tokenid");
                }else{
                    System.out.println("返回结果异常，无tokenid！结果为："+login_res_str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenid;
    }

    public static String getBrData(String jsonData,String apicode){
        String res = "";
        try {
            res = ms.getApiData(jsonData, apicode);
            if(StringUtils.isNotBlank(res)){
                JSONObject json = JSONObject.fromObject(res);
                if(json.containsKey("code")){
                    if(json.getString("code").equals("100007")){
                        tokenid = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        JSONObject jso = new JSONObject();
        JSONObject reqData = new JSONObject();
        jso.put("apiName", "strategyApi");//策略调用
        jso.put("apiName", "verificationApi");//验证管理调用
        jso.put("tokenid", StrategyApiTest.getTokenid());
        reqData.put("id","320");//身份证号码
        reqData.put("cell","");//手机号码
        reqData.put("name","");//姓名
        reqData.put("strategy_id", "STR0000001");//策略编号
        reqData.put("conf_id", "MCP00000001");//验证管理编号
        jso.put("reqData", reqData);
        String result = getBrData(jso.toString(),"");
        if(StringUtils.isNotBlank(result)){
//				System.out.println(tokenid);
            JSONObject json = JSONObject.fromObject(result);
            if(json.containsKey("code")&&json.getString("code").equals("100007")){
                jso.put("tokenid", StrategyApiTest.getTokenid());
                result = getBrData(jso.toString(),"");
            }
        }
        System.out.println(result);
    }
}
