package com.zz.jiao;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.KeyPair;
import java.util.*;

import com.alibaba.fastjson.JSON;


@SpringBootApplication
public class Application {
    private String server = "http://yzcs.geotmt.com" ; // http://yz.geotmt.com、https://yz.geotmt.com
    private int encrypted = 1 ; // 是否加密传输
    private String encryptionType = "AES" ; // AES(秘钥长度不固定)、DES(秘钥长度8)、DESede(秘钥长度24)
    private String encryptionKey = "MSXWtest@geo123" ; // 加密类型和加密秘钥向GEO索取(如果是获取数据的时候传的是RSA那么这里自己定义即可)

    private String spiderEncryptionType = "AES" ; // AES(秘钥长度不固定)、DES(秘钥长度8)、DESede(秘钥长度24)
    private String spiderEncryptionKey = "MSXWtest@geo123" ; // 加密类型和加密秘钥向GEO索取(如果是获取数据的时候传的是RSA那么这里自己定义即可)

    private String username = "msxwtest" ; // 账户向GEO申请开通
    private String password = "MSXWtest@geo1" ;
    private String uno = "200085" ;
    private String etype = "" ;
    private int dsign = 0 ; // 是否进行数字签名 1是0否
    public String getEtype() {
        return etype;
    }

    public void setEtype(String etype) {
        this.etype = etype;
    }

    public int getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(int encrypted) {
        this.encrypted = encrypted;
    }

    public String getServer() {
        return server;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUno() {
        return uno;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public int getDsign() {
        return dsign;
    }

    public void setDsign(int dsign) {
        this.dsign = dsign;
    }

    public Map<String, String> getTokenIdMap() {
        return tokenIdMap;
    }

    public void setTokenIdMap(Map<String, String> tokenIdMap) {
        this.tokenIdMap = tokenIdMap;
    }

    public Map<String, Long> getGetTokenTimeMap() {
        return getTokenTimeMap;
    }

    public void setGetTokenTimeMap(Map<String, Long> getTokenTimeMap) {
        this.getTokenTimeMap = getTokenTimeMap;
    }

    public Map<String, String> getDigitalSignatureKeyMap() {
        return digitalSignatureKeyMap;
    }

    public void setDigitalSignatureKeyMap(Map<String, String> digitalSignatureKeyMap) {
        this.digitalSignatureKeyMap = digitalSignatureKeyMap;
    }

    private static final int httpConnectTimeout = 10000 ;
    private static final int httpReadTimeout = 10000 ;
    private Map<String,String> tokenIdMap = Token.getInstance().getTokenIdMap() ;
    private Map<String,Long> getTokenTimeMap = Token.getInstance().getGetTokenTimeMap() ;
    private Map<String,String> digitalSignatureKeyMap = Token.getInstance().getDigitalSignatureKeyMap() ;

    private static final long tokenCycle = 86400000l ;
    private static final long tokenCyc = 35000l ;  // 避免高并发时刚好token过期造成同时多个线程一起申请新token，如果在此时间内有过更新那么直接返回内存里面的token

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            Application client = new Application();
            String path = client.server + "/civp/getview/api/u/queryUnify";
            Map<String, String> params = new HashMap<String, String>();
            params.put("innerIfType", "B8,A2");
            params.put("cid", "17702166519");
            params.put("idNumber", "460006198912180030");
            params.put("realName", "张三");
            params.put("authCode", "1515553525" + "200085" + "1fc09960eb8848d59f91543df2be3944");
            String data = client.getData(path, params, "civp");
            System.out.println(data);
        };
    }

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);


    }
    public String getData(String path,Map<String,String> params,String sys){
        return getData(path,params,sys,httpConnectTimeout,httpReadTimeout) ;
    }
    /**
     *
     * @param params
     * @param sys
     * @return
     */
    public Map<String,Object> encryptAndDigitalSignature(Map<String,String> params,String sys){
        Map<String,String> headers = new HashMap<String,String>() ;
        TreeMap<String,String> paraMap = new TreeMap<String,String>() ;
        String tokenId = getToken(true) ;
        StringBuilder sb = new StringBuilder() ;
        if (params != null) {
            Set<String> set = params.keySet() ;
            for (String k : set) {
                String value = params.get(k) ;
                if(dsign==1){
                    paraMap.put(k, value) ;
                }
                if(encrypted == 1){
                    // value = Secret.encrypt(encryptionType,value, encryptionKey)  ; // 加密
                    value = encrypt(value, sys) ; // 加密
                }
                sb.append(k).append("=").append(value).append("&") ;
            }
        }
        String sbStr = sb.toString() ;
        if(sb!=null&&sb.length()>0) {
            sbStr = sb.substring(0, sb.length()-1) ;
        }
        if(dsign==1){
            paraMap.put("tokenId", tokenId) ;
            String digitalSignature = DigitalSignature.clientDigitalSignature(paraMap, null, "", digitalSignatureKeyMap.get(username)) ;
            headers.put("digitalSignature", digitalSignature) ;
        }
        String newParams = sbStr+"&tokenId="+tokenId ;
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("para", newParams) ;
        result.put("headers", headers) ;
        return result ;
    }
    /**
     * 该方法可以通过用户名密码直接调用数据接口，且token过期会自动重新获取
     * @param path
     * @param params
     * @param sys
     * @param httpConnectTimeout 毫秒
     * @param httpReadTimeout 毫秒
     * @return
     */
    public String getData(String path,Map<String,String> params,String sys,int httpConnectTimeout,int httpReadTimeout){
        Map<String,String> headers = new HashMap<String,String>() ;
        TreeMap<String,String> paraMap = new TreeMap<String,String>() ;
        String tokenId = getToken(true) ;  // 等计费功能上线后第三个参数才能填RSA不然都只能是空
        StringBuilder sb = new StringBuilder() ;
        if (params != null) {
            Set<String> set = params.keySet() ;
            for (String k : set) {
                String value = params.get(k) ;
                if(dsign==1){
                    paraMap.put(k, value) ;
                }
                if(encrypted == 1){
                    // value = Secret.encrypt(encryptionType,value, encryptionKey)  ; // 加密
                    value = encrypt(value, sys) ; // 加密
                }
                sb.append(k).append("=").append(value).append("&") ;
            }
        }
        String sbStr = sb.toString() ;
        if(sb!=null&&sb.length()>0) {
            sbStr = sb.substring(0, sb.length()-1) ;
        }
        if(dsign==1){
            paraMap.put("tokenId", tokenId) ;
            String digitalSignature = DigitalSignature.clientDigitalSignature(paraMap, null, "", digitalSignatureKeyMap.get(username)) ;
            headers.put("digitalSignature", digitalSignature) ;
        }
        String newParams = sbStr+"&tokenId="+tokenId ;
        String data = getDataByTokenId(path,newParams,headers,sys,httpConnectTimeout,httpReadTimeout) ;
        if(data!=null&&!"".equals(data)){
            Map<String,Object> map = JSON.parseObject(data, Map.class) ;
            String code = map.get("code")+"" ;
            if("-100".equals(code)||"-200".equals(code)||"-300".equals(code)||"-301".equals(code)){
                System.out.println("tokenId无效重新获取tokenId");
                tokenId = getToken(false) ;  // 等计费功能上线后第三个参数才能填RSA不然都只能是空
                if(dsign==1){
                    paraMap.put("tokenId", tokenId) ;
                    String digitalSignature = DigitalSignature.clientDigitalSignature(paraMap, null, "", digitalSignatureKeyMap.get(username)) ;
                    headers.put("digitalSignature", digitalSignature) ;
                }
                newParams = sbStr+"&tokenId="+tokenId ;
                data = getDataByTokenId(path,newParams,headers,sys,httpConnectTimeout,httpReadTimeout) ;
            }
        }
        return data ;
    }

    /**
     * @param first
     * @return
     */
    public String getToken(boolean first){
        if(first){
            if(getNewToken()){
                // 走网络获取token需要同步
                synchronized (username.intern()) {
                    return getToken() ;
                }
            }else{
                return tokenIdMap.get(username) ;
            }
        }else{
            synchronized (username.intern()) {
                Long tokenTime = getTokenTimeMap.get(username) ;
                long getTokenTime = tokenTime==null ? 0l : tokenTime;
                if(System.currentTimeMillis()-getTokenTime>=tokenCyc){
                    // 避免高并发
                    tokenIdMap.put(username, "") ; // 使token失效
                }
                String tokenId = getToken() ;
                return tokenId ;
            }
        }
    }
    /**
     * 获取数据访问权限,该接口24小时请求一次即可！
     * @return token
     */
    public String getToken(){
        if(getNewToken()){
            String path = server+"/civp/getview/api/o/login" ;
            // 加密用户名密码
            String eUsername = username ;
            String ePassword = password ;
            String eDsign = dsign+"" ;
            if(encrypted == 1){
				/*
				eUsername = Secret.encrypt(encryptionType,username, encryptionKey) ;
				ePassword = Secret.encrypt(encryptionType,password, encryptionKey) ;
				eDsign = Secret.encrypt(encryptionType,dsign+"", encryptionKey) ;
				*/
                eUsername = encrypt(username) ;
                ePassword = encrypt(password) ;
                eDsign = encrypt(dsign+"") ;
            }
            String params = "" ;
            if("RSA".equalsIgnoreCase(etype)){
                KeyPair keyPair = RSAUtils.getKeyPair();
                String paraEncryptionType = RSAUtils.encrypt(keyPair.getPublic(), encryptionType) ;
                String paraEncryptionKey = RSAUtils.encrypt(keyPair.getPublic(), encryptionKey) ;
                params = "username="+eUsername+"&password="+ePassword+"&etype="+etype+"&encryptionType="+paraEncryptionType+"&encryptionKey="+paraEncryptionKey+"&encrypted="+encrypted+"&dsign="+eDsign ;
            }else{
                params = "username="+eUsername+"&password="+ePassword+"&uno="+uno+"&encrypted="+encrypted+"&dsign="+eDsign ;
            }
            String reqencoding = "UTF-8" ;
            String respencoding = "UTF-8" ;
            String requestMethod = "POST" ;
            Map<String,String> headerMap = null ;
            String rs = HttpClient.getRs(path, params, reqencoding, respencoding, requestMethod, httpConnectTimeout, httpReadTimeout, headerMap) ;
            if(rs!=null&&!"".equals(rs)){
                // 解密返回值
                if(rs.startsWith("{")){
                    System.out.println("未加密:"+rs);
                }else{
                    // rs = Secret.decrypt(encryptionType,rs, encryptionKey) ;
                    rs = decrypt(rs) ;
                    System.out.println("解密:"+rs);
                }
                Map<String,Object> map = JSON.parseObject(rs, Map.class) ;
                if("200".equals(map.get("code")+"")){
                    long getTokenTime = System.currentTimeMillis() ;
                    String tokenId = map.get("tokenId")+"" ;
                    tokenIdMap.put(username, tokenId) ;
                    getTokenTimeMap.put(username, getTokenTime) ;
                    Map data = (Map)map.get("data") ;
                    if(data!=null){
                        String digitalSignatureKey = (String)data.get("digitalSignatureKey") ;
                        if(digitalSignatureKey!=null){
                            digitalSignatureKeyMap.put(username, digitalSignatureKey) ;
                        }
                    }
                    return tokenId ;
                }else{
                    System.out.println("登录失败!code="+map.get("code"));
                    return "" ;
                }
            }else{
                return "" ;
            }
        }else{
            return tokenIdMap.get(username) ;
        }
    }
    /**
     * 获取数据
     * @param path
     * @param params
     * @param headers
     * @param sys
     * @param httpConnectTimeout
     * @param httpReadTimeout
     * @return
     */
    public String getDataByTokenId(String path,String params,Map<String,String> headers,String sys,int httpConnectTimeout,int httpReadTimeout){
        String reqencoding = "UTF-8" ;
        String respencoding = "UTF-8" ;
        String requestMethod = "POST" ;
        String rs = HttpClient.getRs(path, params, reqencoding, respencoding, requestMethod, httpConnectTimeout, httpReadTimeout, headers) ;
        if(rs!=null&&!"".equals(rs)){
            // 解密返回值
            if(rs.startsWith("{")){
                System.out.println("未加密:"+rs);
            }else{
                rs = decrypt(rs, sys) ;
                System.out.println("解密:"+rs);
            }
            Map<String,Object> map = JSON.parseObject(rs, Map.class) ;
			/*
			if(!"200".equals(map.get("code")+"")){
				System.out.println("数据请求失败!code="+map.get("code"));
			}
			*/
        }
        return rs ;
    }
    /**
     * 登出接口
     * @return
     */
    public String loginOut(String sys){
        String tokenId = tokenIdMap.get(username) ;
        if(tokenId!=null&&!"".equals(tokenId)){
            String digitalSignatureKey = digitalSignatureKeyMap.get(username);
            Map<String,String> headers = new HashMap<String,String>() ;
            if(digitalSignatureKey!=null&&!"".equals(digitalSignatureKey)){
                TreeMap<String,String> paraMap = new TreeMap<String,String>() ;
                paraMap.put("tokenId", tokenId) ;
                String digitalSignature = DigitalSignature.clientDigitalSignature(paraMap, null, "", digitalSignatureKeyMap.get(username)) ;
                headers.put("digitalSignature", digitalSignature) ;
            }
            return loginOut(tokenId,headers,sys) ;
        }
        System.out.println("用户未登录");
        return "" ;
    }
    /**
     * 登出(该token权限将被回收),该接口一般不需要调用
     * @param tokenId
     * @return
     */
    public String loginOut(String tokenId,Map<String,String> headerMap,String sys){
        String path = server+"/civp/getview/api/u/logout" ;
        String params = "tokenId="+tokenId ;
        String reqencoding = "UTF-8" ;
        String respencoding = "UTF-8" ;
        String requestMethod = "POST" ;
        String rs = HttpClient.getRs(path, params, reqencoding, respencoding, requestMethod, httpConnectTimeout, httpReadTimeout, headerMap) ;
        if(rs!=null&&!"".equals(rs)){
            // 解密返回值
            if(rs.startsWith("{")){
                System.out.println("未加密:"+rs);
            }else{
                rs = decrypt(rs, sys) ;
                System.out.println("解密:"+rs);
            }
            Map<String,Object> map = JSON.parseObject(rs, Map.class) ;
            if("200".equals(map.get("code")+"")){
                System.out.println("退出成功");
                tokenIdMap.remove(username) ;
                getTokenTimeMap.remove(username) ;
                digitalSignatureKeyMap.remove(username) ;
            }else{
                System.out.println("退出失败");
            }
        }
        return rs ;
    }
    /**
     * 加密,登陆接口
     * @param text
     * @return
     */
    private String encrypt(String text){
        return encrypt(text,"civp") ;
    }
    /**
     * 加密
     * @param rs
     * @param sys
     * @return
     */
    private String encrypt(String text,String sys){
        if("civp".equalsIgnoreCase(sys)){
            text = Secret.encrypt(encryptionType,text, encryptionKey) ;
            return text ;
        }else{
            text = Secret.encrypt(spiderEncryptionType,text, spiderEncryptionKey) ;
            return text ;
        }
    }
    /**
     * 解密,登陆接口
     * @param rs
     * @return
     */
    private String decrypt(String rs){
        return decrypt(rs,"civp") ;
    }
    /**
     * 解密
     * @param rs
     * @param sys
     * @return
     */
    private String decrypt(String rs,String sys){
        if("civp".equalsIgnoreCase(sys)){
            rs = Secret.decrypt(encryptionType,rs, encryptionKey) ;
            return rs ;
        }else{
            rs = Secret.decrypt(spiderEncryptionType,rs, spiderEncryptionKey) ;
            return rs ;
        }
    }

    /**
     * 判定token是否还有效
     * @param username
     * @return
     */
    public boolean getNewToken(){
        Long tokenTime = getTokenTimeMap.get(username) ;
        long getTokenTime = tokenTime==null ? 0l : tokenTime;
        return tokenIdMap.get(username)==null||"".equals(tokenIdMap.get(username))||System.currentTimeMillis()-getTokenTime>=tokenCycle ;
    }

    public String getSpiderEncryptionType() {
        return spiderEncryptionType;
    }

    public void setSpiderEncryptionType(String spiderEncryptionType) {
        this.spiderEncryptionType = spiderEncryptionType;
    }

    public String getSpiderEncryptionKey() {
        return spiderEncryptionKey;
    }

    public void setSpiderEncryptionKey(String spiderEncryptionKey) {
        this.spiderEncryptionKey = spiderEncryptionKey;
    }

    public String rpad(String str, int strLength, char chr) {
        int strLen = str.length();
        if (strLen < strLength) {
            StringBuffer sb = new StringBuffer(str);
            while (strLen < strLength) {
                sb.append(chr); // 右补0
                strLen = sb.length();
            }
            str = sb.toString();
        }else{
            str = str.substring(0, strLength) ;
        }
        return str;
    }

    public String rpad(String str, int strLength) {
        return rpad(str, strLength, '0');
    }

    public String lpad(String str, int strLength, char chr) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                str = chr + str; // 左补0
                strLen = str.length();
            }
        }
        return str;
    }

    public String lpad(String str, int strLength) {
        return lpad(str, strLength, '0');
    }
}

