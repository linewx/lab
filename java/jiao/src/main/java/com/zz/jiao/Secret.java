package com.zz.jiao;

import java.security.KeyPair;
import java.security.MessageDigest;

/**
 * 统一加密解密类
 * @author wushujia
 *
 */
public class Secret {
	/**
	 * 加密
	 * @param encryptionType 加密类型
	 * @param content 需要加密的内容
	 * @param encryptionKey 加密密码
	 * @return
	 */
	public static String encrypt(String encryptionType,String content, String encryptionKey) {
				
		if(encryptionType.startsWith("DESede")){
			content = TripleDES.encrypt(content, encryptionKey) ;
    	}else if(encryptionType.startsWith("DES")){
    		content = DES.encrypt(content, encryptionKey) ;
    	}else if(encryptionType.startsWith("AES2")){
    		content = AES2.encrypt(content, encryptionKey) ;
    	}else if(encryptionType.startsWith("AES")){
    		content = AES.encrypt(content, encryptionKey) ;
    	}else if(encryptionType.startsWith("XOR")){
    		content = XOREncrypt.encrypt(content, encryptionKey) ;
    	}else if(encryptionType.startsWith("RSA")){
    		String[] rsakey = encryptionKey.split(";") ;
    		String publicKeygetModulus = rsakey[0] ; // 模
    		String publicKeygetPublicExponent = rsakey[1] ; // 公钥指数
    		String privateKeygetPrivateExponent = rsakey[2] ; // 私钥指数
    		KeyPair keyPair = RSAUtils.getKeyPair(publicKeygetModulus,publicKeygetPublicExponent,privateKeygetPrivateExponent);
    		content = RSAUtils.encrypt(keyPair.getPublic(), content) ;
    	}
		return content ;
	}
    
	/**
	 * 解密
	 * @param encryptionType 解密类型
	 * @param content 解密内容
	 * @param encryptionKey 解密密钥
	 * @return
	 */
	public static String decrypt(String encryptionType,String content, String encryptionKey) {
		if(encryptionType.startsWith("DESede")){
			content = TripleDES.decrypt(content, encryptionKey) ;
    	}else if(encryptionType.startsWith("DES")){
    		content = DES.decrypt(content, encryptionKey) ;
    	}else if(encryptionType.startsWith("AES2")){
    		content = AES2.decrypt(content, encryptionKey) ;
    	}else if(encryptionType.startsWith("AES")){
    		content = AES.decrypt(content, encryptionKey) ;
    	}else if(encryptionType.startsWith("XOR")){
    		content = XOREncrypt.decrypt(content, encryptionKey) ;
    	}else if(encryptionType.startsWith("RSA")){
    		String[] rsakey = encryptionKey.split(";") ;
    		String publicKeygetModulus = rsakey[0] ; // 模
    		String publicKeygetPublicExponent = rsakey[1] ; // 公钥指数
    		String privateKeygetPrivateExponent = rsakey[2] ; // 私钥指数
    		KeyPair keyPair = RSAUtils.getKeyPair(publicKeygetModulus,publicKeygetPublicExponent,privateKeygetPrivateExponent);
    		content = RSAUtils.decrypt(keyPair.getPrivate(), content) ;
    	}
		return content ;
	}
	/**
	 * md5加密
	 * @param source
	 * @return
	 */
	public static String md5(String source) {
	    StringBuffer sb = new StringBuffer(32); 
	    try {
	        MessageDigest md    = MessageDigest.getInstance("MD5");  
	        byte[] array        = md.digest(source.getBytes("utf-8")); 
	        for (int i = 0; i < array.length; i++) {
	            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
	        }
	    } catch (Exception e) {
	        e.printStackTrace() ;
	        return null;  
	    }
	    return sb.toString();  
	}
}
