package cn.gyyx.supervisor.service;

import java.util.List;
import java.util.Map;

import cn.gyyx.supervisor.model.App;
import cn.gyyx.supervisor.model.AppAgent;
import cn.gyyx.supervisor.model.AppVersion;

public interface AppService {
	
	public List<App> getAllApp();
	
	public App valInstruct(String valInstruct) throws Exception;
	
	public void addApp(Map<String,Object> map) throws Exception;
	
	public void deleteApp(String id)throws Exception;
	
	public App getAppById(int id) throws Exception;

	public void insertAppVersion(AppVersion appVersion) throws Exception;

	public void updateAppVersion(AppVersion appVersion) throws Exception;

	public AppVersion getAppVersionById(AppVersion appVersion)throws Exception;

	
	public int startAppVersion(int appid,int versionId)throws Exception;

	public List<AppVersion> getVsersionList(int appId);
	
	public List<Map<String,Object>> getAgent(Integer appId, String ip, String assetId);
	
	public List<AppAgent> getAppAgent(Integer appId, String ip, String assetId);
	
	public boolean bindAgent(Integer appId, String appAgentList);
	
	public boolean unBindAgent(String ids);
	
}
