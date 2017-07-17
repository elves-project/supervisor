package cn.gyyx.supervisor.model;

import java.io.Serializable;

/**
 * @ClassName: AuthKey
 * @Description: 权限 auth_key表
 * @author East.F
 * @date 2016年12月12日 下午2:36:01
 */
public class AuthKey implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String authId;			//权限ID
	private String authKey;			//权限key
	private String authName;		//名称
	private String createTime;		//创建时间
	private int appId;				//绑定的AppId
	private String appName;			//绑定的AppName
	private String instruct;		//绑定的instruct
	
	public String getInstruct() {
		return instruct;
	}
	public void setInstruct(String instruct) {
		this.instruct = instruct;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
