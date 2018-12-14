package com.zz.bairong;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import sun.misc.BASE64Encoder;


public class ShenmiApplication {

    static String url = "https://service.china-bestmind.com/api";//查询地址
    static String account="csmsxf122"; //账号
    static String key = "VR8qKEh5AA85eCq8Zr6ZZtP9F392PT5R"; //密码
    public static void main(String[] args){
        String acode = "800800";//服务代码

        //授权书照片，大小小于2MB（jpg或png）
        String photo = ImageToBase64ByLocal("/tmp/xiaomi.jpeg");
        String cName = "福建美达科技有限公司";// 公司名称
        String legalName = "陆某"; //法人名称
        String legalIdCard = "320683198110071016";//法人身份证号
        String legalMobile = "18101947163";//法人手机号码

        String param = "CName=" + cName + "&LegalName=" + legalName + "&LegalIdCard=" + legalIdCard + "&LegalMobile=" + legalMobile + "&photo=" + photo;
        String sign = md5(acode + param + account + md5(key));//生成签名 (MD5大写)

        String post_data = null;
        try {
            post_data = "acode=" + acode + "&param=" + URLEncoder.encode(param, "UTF-8") + "&account="
                    + account + "&sign=" + sign;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        String json = postHtml(url, post_data);

        //返回的 JSON 即为查询到的信息
        System.out.println(json);
    }

    static String md5(String text) {
        byte[] bts;
        try {
            bts = text.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bts_hash = md.digest(bts);
            StringBuffer buf = new StringBuffer();
            for (byte b : bts_hash) {
                buf.append(String.format("%02X", b & 0xff));
            }
            return buf.toString();
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    static String postHtml(String url, String postData) {
        try {
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(postData);
            out.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            StringBuffer response = new StringBuffer();

            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 本地图片转换成base64字符串
     * @param imgFile	图片本地路径
     * @return Base64图片
     */
    public static String ImageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理


        InputStream in = null;
        byte[] data = null;

        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

}