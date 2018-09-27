package com.zz.jiao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例
 * @author wushujia
 * @author xiahaihua
 */
public class TokenAI {
	private final Map<String,String> tokenIdMap = new ConcurrentHashMap<String,String>() ;
	private final Map<String,Long> getTokenTimeMap = new ConcurrentHashMap<String,Long>() ;
	private final Map<String,String> digitalSignatureKeyMap = new ConcurrentHashMap<String,String>() ;
	private TokenAI(){}
	private final static TokenAI token = new TokenAI();
	public static TokenAI getInstance() {
        return token ;
    }
	public Map<String, String> getTokenIdMap() {
		return tokenIdMap;
	}
	public Map<String, Long> getGetTokenTimeMap() {
		return getTokenTimeMap;
	}
	public Map<String, String> getDigitalSignatureKeyMap() {
		return digitalSignatureKeyMap;
	}
}
