package com.zz.huifa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class PostCallPort {

    private static int connectTimeOut = 5000;


    private static int readTimeOut = 10000;


    private static String requestEncoding = "utf-8";



    public static int getConnectTimeOut() {
        return PostCallPort.connectTimeOut;
    }


    public static int getReadTimeOut() {
        return PostCallPort.readTimeOut;
    }

    public static String getRequestEncoding() {
        return requestEncoding;
    }


    public static void setConnectTimeOut(int connectTimeOut) {
        PostCallPort.connectTimeOut = connectTimeOut;
    }

    public static void setReadTimeOut(int readTimeOut) {
        PostCallPort.readTimeOut = readTimeOut;
    }


    public static void setRequestEncoding(String requestEncoding) {
        PostCallPort.requestEncoding = requestEncoding;
    }




    /**
     *
     * 发送带参数的POST的HTTP请求
     *

     *
     * @param reqUrl HTTP请求URL
     * @param parameters 参数映射表
     * @return HTTP响应的字符串
     */
    public static String doPost(String reqUrl, Map parameters, String recvEncoding) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        try {
            StringBuffer params = new StringBuffer();
            for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
                Entry element = (Entry) iter.next();
                params.append(element.getKey().toString());
                params.append("=");
                params.append(URLEncoder.encode(element.getValue().toString(), PostCallPort.requestEncoding));
                params.append("&");
            }

            if (params.length() > 0) {
                params = params.deleteCharAt(params.length() - 1);
            }

            URL url = new URL(reqUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("POST");
            System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(PostCallPort.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
            System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(PostCallPort.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时

            url_con.setDoOutput(true);
            byte[] b = params.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();

            InputStream in = url_con.getInputStream();
            //responseContent =CallHFWServices.InputStream2String(in, "UTF-8");
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            rd.close();
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (url_con != null) {
                url_con.disconnect();
            }
        }
        return responseContent;
    }


    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("conid", "1022");
        map.put("stype", "1");
        map.put("keynum", "kn0001");
        map.put("msg", "龚来富,330121196212241414|朱可可,330722197103186261|");
        map.put("appkey", "b5a21896efc3a547");
        String temp = PostCallPort.doPost("http://testapi.lawxp.com/s.aspx", map, "utf-8");
        System.out.println("返回的消息是:" + temp);
    }

}
                        