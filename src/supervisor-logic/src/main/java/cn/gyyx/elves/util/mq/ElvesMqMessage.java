package cn.gyyx.elves.util.mq;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @ClassName: ElvesMqMessage
 * @Description: elves 模块通讯mq消息类。
 * 				<p>elves各模块提供服务供其他模块调用，模块间的消息请求和回复都要符合约定的规则。</p>
 * 					消息结构： {
 * 						"mykey":{fromModule}.{toModule}.{action},
 * 						"mqtype":"call.{directRoutingKey}"/"cast",
 * 						"mybody":{
 * 							"paramKey1":"paramValue1"
 * 						}
 * 					}</p> 
 * @author East.F
 * @date 2017年6月2日 下午2:28:36
 */
public class ElvesMqMessage {
	/*
	 * mqkey:{fromModule}.{toModule}.{action}
	 */
	private String mqkey;
	
	private String fromModule;
	
	private String toModule;
	
	private String action;
	
	/*
	 * mqtype: call.{directRoutingKey}/cast
	 */
	private String mqtype;
	
	private String directRoutingKey;
	
	/*
	 * mqBody 可以为空，非空则是Map<String,Object>类型的参数集合
	 */
	private Map<String,Object> mqbody;
	
	private ElvesMqMessage(Map<String,Object> elvesMsg){
		parse(elvesMsg);
	}
	
	public static ElvesMqMessage getInstance(String elvesMsg){
		if(ElvesMqMessage.validateElvesMqMessage(elvesMsg)){
			return new ElvesMqMessage(JSON.parseObject(elvesMsg,new TypeReference<Map<String, Object>>(){}));
		}
		return null;
	}
	
	/**
	 * @Title: parse
	 * @Description: 解析数据
	 * @param elvesMsg 设定文件
	 * @return void    返回类型
	 */
	private void parse(Map<String,Object> elvesMsg){
		this.mqkey = elvesMsg.get("mqkey").toString().trim();
		this.mqtype = elvesMsg.get("mqtype").toString().trim();
		if(null!=elvesMsg.get("mqbody")){
			this.mqbody = JSON.parseObject(elvesMsg.get("mqbody").toString().trim(),new TypeReference<Map<String, Object>>(){});
		}else{
			this.mqbody =new HashMap<String, Object>();
		}
		this.fromModule = mqkey.split("[.]")[0];
		this.toModule = mqkey.split("[.]")[1];
		this.action = mqkey.split("[.]")[2];
		if(!"cast".equals(mqtype)){
			this.directRoutingKey =mqtype.split("[.]")[1];
		}
	}
	

	public String getMqkey() {
		return mqkey;
	}

	public void setMqkey(String mqkey) {
		this.mqkey = mqkey;
	}

	public String getFromModule() {
		return fromModule;
	}

	public void setFromModule(String fromModule) {
		this.fromModule = fromModule;
	}

	public String getToModule() {
		return toModule;
	}

	public void setToModule(String toModule) {
		this.toModule = toModule;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMqtype() {
		return mqtype;
	}

	public void setMqtype(String mqtype) {
		this.mqtype = mqtype;
	}

	public String getDirectRoutingKey() {
		return directRoutingKey;
	}

	public void setDirectRoutingKey(String directRoutingKey) {
		this.directRoutingKey = directRoutingKey;
	}

	public Map<String, Object> getMqbody() {
		return mqbody;
	}

	public void setMqbody(Map<String, Object> mqbody) {
		this.mqbody = mqbody;
	}

	/**
	 * @Title: validateElvesMqMessage
	 * @Description: 校验接收到的mq消息body 内容， 是否符合elves模块通讯的规范
	 * @param msgStr
	 * @return boolean    返回类型
	 */
	public static boolean validateElvesMqMessage(String msgStr){
		try {
			Map<String,Object> reqMsgMap =JSON.parseObject(msgStr,new TypeReference<Map<String, Object>>(){});
			if(null==reqMsgMap||null==reqMsgMap.get("mqkey")||null==reqMsgMap.get("mqtype")){
				return false;
			}
			String[] arr = reqMsgMap.get("mqkey").toString().trim().split("[.]");
			if(null==arr||arr.length!=3){
				return false;
			}
			
			String mqtype = reqMsgMap.get("mqtype").toString().trim();
			if(mqtype.startsWith("call")){
				String[] arr2 = mqtype.split("[.]");
				if(arr2.length!=2){
					return false;
				}
			}else {
				if(!"cast".equals(mqtype)){
					return false;
				}
			}
			
			if(null!=reqMsgMap.get("mqbody")){
				JSON.parseObject(reqMsgMap.get("mqbody").toString().trim(),new TypeReference<Map<String, Object>>(){});
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
