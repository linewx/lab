package com.zz.jiao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 对接样例
 * @author wushujia
 *
 */
public class ClientDemo {
	
	private static final String server = "http://yz.geotmt.com" ; // geo 提供
	private static final int encrypted = 0 ; // 是否加密传输  1是0否
	private static final String encryptionType = "AES2" ; // 加密类型和加密秘钥向GEO索取 AES(秘钥长度不固定)、AES2(秘钥长度16)、DES(秘钥长度8)、DESede(秘钥长度24)、XOR(秘钥只能是数字)
	private static final String encryptionKey = "1234567812345678" ; // 加密类型和加密秘钥向GEO索取(如果是获取数据的时候传的是RSA那么这里自己定义即可)
	
	private static final String spiderEncryptionType = "AES2" ; // 爬虫的加密类型和加密秘钥向GEO索取 AES(秘钥长度不固定)、DES(秘钥长度8)、DESede(秘钥长度24)
	private static final String spiderEncryptionKey = "1234567812345678" ; // 爬虫的加密类型和加密秘钥向GEO索取(如果是获取数据的时候传的是RSA那么这里自己定义即可)
	
	private static final String username = "" ; // 账户向GEO申请开通
	private static final String password = "" ; // GEO提供
	private static final String uno = "" ; // GEO提供
	private static final String etype = "" ; // 只能填写""!
	private static final int dsign = 0 ; // 是否进行数字签名 1是0否
	
	
	// 构造客户端(线程安全)
	public static final Client client = new Client();  // 如果接入只是一个账号的话(一个账号对应一个对象)那么该类只需构造一次即可
	
	static{
		client.setServer(server);
		client.setEncrypted(encrypted);
		client.setEncryptionType(encryptionType);
		client.setEncryptionKey(encryptionKey);
		
		client.setSpiderEncryptionType(spiderEncryptionType);
		client.setSpiderEncryptionKey(spiderEncryptionKey);
		
		client.setUsername(username);
		client.setPassword(password);
		client.setUno(uno);
		client.setEtype(etype);
		client.setDsign(dsign);
		client.setTokenIdMap(Token.getInstance().getTokenIdMap());
		client.setGetTokenTimeMap(Token.getInstance().getGetTokenTimeMap());
		client.setDigitalSignatureKeyMap(Token.getInstance().getDigitalSignatureKeyMap());
	}
	///////////////////////ai新平台开始///////////////////////////////////////////////////////////////
	
	private static final String server_ai = "http://yz.geotmtai.com" ; // geo 提供
	private static final int encrypted_ai = 0 ; // 是否加密传输  1是0否
	private static final String encryptionType_ai = "AES2" ; // 加密类型和加密秘钥向GEO索取 AES(秘钥长度不固定)、AES2(秘钥长度16)、DES(秘钥长度8)、DESede(秘钥长度24)、XOR(秘钥只能是数字)
	private static final String encryptionKey_ai = "1234567812345678" ; // 加密类型和加密秘钥向GEO索取(如果是获取数据的时候传的是RSA那么这里自己定义即可)
	
	private static final String spiderEncryptionType_ai = "AES2" ; // 爬虫的加密类型和加密秘钥向GEO索取 AES(秘钥长度不固定)、DES(秘钥长度8)、DESede(秘钥长度24)
	private static final String spiderEncryptionKey_ai = "1234567812345678" ; // 爬虫的加密类型和加密秘钥向GEO索取(如果是获取数据的时候传的是RSA那么这里自己定义即可)
	
	private static final String username_ai = "" ; // 账户向GEO申请开通
	private static final String password_ai = "" ; // GEO提供
	private static final String uno_ai = "" ; // GEO提供
	private static final String etype_ai = "" ; // 只能填写""!
	private static final int dsign_ai = 0 ; // 是否进行数字签名 1是0否
	
	
	// 构造客户端(线程安全)
	public static final Client client_ai = new Client(); // 如果接入只是一个账号的话(一个账号对应一个对象)那么该类只需构造一次即可

	static {
		client_ai.setServer(server_ai);
		client_ai.setEncrypted(encrypted_ai);
		client_ai.setEncryptionType(encryptionType_ai);
		client_ai.setEncryptionKey(encryptionKey_ai);
		
		client_ai.setSpiderEncryptionType(spiderEncryptionType_ai);
		client_ai.setSpiderEncryptionKey(spiderEncryptionKey_ai);
		
		client_ai.setUsername(username_ai);
		client_ai.setPassword(password_ai);
		client_ai.setUno(uno_ai);
		client_ai.setEtype(etype_ai);
		client_ai.setDsign(dsign_ai);
        client_ai.setTokenIdMap(TokenAI.getInstance().getTokenIdMap());
        client_ai.setGetTokenTimeMap(TokenAI.getInstance().getGetTokenTimeMap());
        client_ai.setDigitalSignatureKeyMap(TokenAI.getInstance().getDigitalSignatureKeyMap());
	}

	public static void main(String[] args){
		//client.loginOut(); // 一般不需要调用此方法!!!如果前面一次进行了数字签名(或进行加密传输)然后又不想进行数字签名(或后面不想加密传输)那么需要先调用注销接口 client.loginOut()
		String data = getData() ;  // 风控数据请求
		System.out.println(data+"1j12g3j12hgjh12");
		
		//ai新平台调用
		String data_ai = getDataAi() ;  // 风控数据请求AI
		System.out.println(data_ai+"1j12g3j12hgjh12");
	}
	/**
	 * 风控数据请求
	 */
	public static String getData(){
		// 请求地址
		String path = server+"/civp/getview/api/u/queryUnify" ;
		// 请求参数(client里面会自动加密,所以这里请使用明文)
		Map<String,String> params = new HashMap<String,String>();
		params.put("innerIfType", "A2") ;
		params.put("cid", "17702166514") ;
		params.put("idNumber", "460006198912180030") ;
		params.put("realName", "张三") ;
		/**
		 *  1、授权码位数固定为50位；
			2、组成：
			10位时间戳+8位客户编号+32位序列号
			例如：15155535250020010000000000000000000000000000030000
			
			格式要求：
			（1）1-10位为时间戳，仅数字格式，由客户提供，为发起查询时间的时间戳；例如：查询时间2018/1/10 11:5:25——时间戳1515553525
			（2）11-18位为客户编号。仅数字；由集奥给客户提供（当前集奥给客户提供的是6位客户编号，客户需要在前面补2个0）。例如：集奥提供给客户的客户编号为：200100——8位客户编号：00200100
			（3）19-50为序列号，仅数字或字母；由客户提供，需要保证唯一，如不满32位，在前面用0补齐。如：客户自定义序列号30000——32位序列号：00000000000000000000000000030000
		 */
		long time = System.currentTimeMillis()/1000;
		String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // 该处为示意，应该为能关联上用户授权信息的ID左补全成32位
		params.put("authCode", ""+time+client.lpad(uno+"", 8, '0')+uuid) ;  // 授权码得对应上客户合同编号
		// 请求数据接口返回json
		String data = client.getData(path,params,"civp") ;
		System.out.println(data);
		return data ;
	}
	
	/**
	 * 风控数据请求 ai平台
	 */
	public static String getDataAi(){
		// 请求地址
		String path = server_ai+"/civp/getview/api/u/queryUnify" ;
		// 请求参数(client里面会自动加密,所以这里请使用明文)
		Map<String,String> params = new HashMap<String,String>();
		params.put("innerIfType", "A2") ;
		params.put("cid", "17702166514") ;
		params.put("idNumber", "460006198912180030") ;
		params.put("realName", "张三") ;
		/**
		 *  1、授权码位数固定为50位；
			2、组成：
			10位时间戳+8位客户编号+32位序列号
			例如：15155535250020010000000000000000000000000000030000
			
			格式要求：
			（1）1-10位为时间戳，仅数字格式，由客户提供，为发起查询时间的时间戳；例如：查询时间2018/1/10 11:5:25——时间戳1515553525
			（2）11-18位为客户编号。仅数字；由集奥给客户提供（当前集奥给客户提供的是6位客户编号，客户需要在前面补2个0）。例如：集奥提供给客户的客户编号为：200100——8位客户编号：00200100
			（3）19-50为序列号，仅数字或字母；由客户提供，需要保证唯一，如不满32位，在前面用0补齐。如：客户自定义序列号30000——32位序列号：00000000000000000000000000030000
		 */
		long time = System.currentTimeMillis()/1000;
		String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // 该处为示意，应该为能关联上用户授权信息的ID左补全成32位
		params.put("authCode", ""+time+client_ai.lpad(uno_ai+"", 8, '0')+uuid) ;  // 授权码得对应上客户合同编号
		// 请求数据接口返回json
		String data = client_ai.getData(path,params,"civp") ;
		System.out.println(data);
		return data ;
	}




}
