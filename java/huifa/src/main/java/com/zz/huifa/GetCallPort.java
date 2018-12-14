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


public class GetCallPort {

    private static int connectTimeOut = 5000;


    private static int readTimeOut = 10000;


    private static String requestEncoding = "utf-8";



    public static int getConnectTimeOut() {
        return GetCallPort.connectTimeOut;
    }


    public static int getReadTimeOut() {
        return GetCallPort.readTimeOut;
    }

    public static String getRequestEncoding() {
        return requestEncoding;
    }


    public static void setConnectTimeOut(int connectTimeOut) {
        GetCallPort.connectTimeOut = connectTimeOut;
    }

    public static void setReadTimeOut(int readTimeOut) {
        GetCallPort.readTimeOut = readTimeOut;
    }


    public static void setRequestEncoding(String requestEncoding) {
        GetCallPort.requestEncoding = requestEncoding;
    }


    /**
     *
     * 发送带参数的GET的HTTP请求
     *

     *
     * @param reqUrl HTTP请求URL
     * @param parameters 参数映射表
     * @return HTTP响应的字符串
     */
    public static String doGet(String reqUrl, Map parameters, String recvEncoding) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        try {
            StringBuffer params = new StringBuffer();
            for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
                Entry element = (Entry) iter.next();
                params.append(element.getKey().toString());
                params.append("=");
                params.append(URLEncoder.encode(element.getValue().toString(), GetCallPort.requestEncoding));
                params.append("&");
            }

            if (params.length() > 0) {
                params = params.deleteCharAt(params.length() - 1);
            }

            URL url = new URL(reqUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("GET");
            System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(GetCallPort.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
            System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(GetCallPort.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时

            url_con.setDoOutput(true);
            byte[] b = params.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();

            InputStream in = url_con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer temp = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                temp.append(tempLine);
                temp.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = temp.toString();
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

    /**
     *
     * 发送不带参数的GET的HTTP请求
     *

     *
     * @param reqUrl HTTP请求URL
     * @return HTTP响应的字符串
     */
    public static String doGet(String reqUrl, String recvEncoding) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        try {
            StringBuffer params = new StringBuffer();
            String queryUrl = reqUrl;
            int paramIndex = reqUrl.indexOf("?");

            if (paramIndex > 0) {
                queryUrl = reqUrl.substring(0, paramIndex);
                String parameters = reqUrl.substring(paramIndex + 1, reqUrl.length());
                String[] paramArray = parameters.split("&");
                for (int i = 0; i < paramArray.length; i++) {
                    String string = paramArray[i];
                    int index = string.indexOf("=");
                    if (index > 0) {
                        String parameter = string.substring(0, index);
                        String value = string.substring(index + 1, string.length());
                        params.append(parameter);
                        params.append("=");
                        params.append(URLEncoder.encode(value, GetCallPort.requestEncoding));
                        params.append("&");
                    }
                }

                params = params.deleteCharAt(params.length() - 1);
            }

            URL url = new URL(queryUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("GET");
            System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(GetCallPort.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
            System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(GetCallPort.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
            url_con.setDoOutput(true);
            byte[] b = params.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();
            InputStream in = url_con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer temp = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                temp.append(tempLine);
                temp.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = temp.toString();
            rd.close();
            in.close();
        }
        catch (IOException e) {

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
        map.put("conid", "1021");
        map.put("stype", "1");
        map.put("n", "龚来富");
        map.put("id", "330121196212241414");
        map.put("appkey", "b5a21896efc3a547");
        String temp = GetCallPort.doGet("http://testapi.lawxp.com/s.aspx", map, "utf-8");
        System.out.println("返回的消息是:" + temp);
    }

}