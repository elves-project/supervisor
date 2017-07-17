package cn.gyyx.supervisor.model;

import java.io.Serializable;

public class AppAgent implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;					//主键ID
	
	private Integer appId;				//app主键ID
	
	private String ip;					//ip
	
	private String assetId;				//资产ID

	private String appName;
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
}
