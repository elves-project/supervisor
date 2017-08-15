package cn.gyyx.supervisor.model;

import java.io.Serializable;

/**
 * @ClassName: App
 * @Description: 应用信息app表
 * @author East.F
 * @date 2016年12月9日 下午4:05:09
 */
public class App implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer appId; // 主键ID
	private String instruct; // 指令
	private String appName; // app名称
	private String founder; // 创建者
	private String createTime; // 创建时间
	private String updateTime; // 更新时间
	private Integer versionId; // 当前版本id

	private String bindUrl;		//绑定agent数据的url接口
	
	private String version; //app的当前版本
    private String status;
    
    private int agentCount;  //当前app限定的agent数目
	
	public Integer getAppId() {
		return appId;
	}



	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getInstruct() {
		return instruct;
	}

	public void setInstruct(String instruct) {
		this.instruct = instruct;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getFounder() {
		return founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAgentCount() {
		return agentCount;
	}

	public void setAgentCount(int agentCount) {
		this.agentCount = agentCount;
	}

	public String getBindUrl() {
		return bindUrl;
	}

	public void setBindUrl(String bindUrl) {
		this.bindUrl = bindUrl;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof App){
			int id=((App)obj).getAppId();
			return getAppId()==id;
		}
		return false;
	}


}
