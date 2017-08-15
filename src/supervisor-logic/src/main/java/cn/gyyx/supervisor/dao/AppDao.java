package cn.gyyx.supervisor.dao;

import cn.gyyx.supervisor.model.App;
import cn.gyyx.supervisor.model.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AppDao {

	public List<App> getAllApp();
	
	public App valInstruct(String valInstruct) throws Exception;
	
	public void addApp(Map<String,Object> map) throws Exception;
	
	public void deleteApp(String id)throws Exception;
	
	public List<AppVersion> getVsersionList(int appId);
	
	public App query(String instruct);

	public App getAppById(int id) throws Exception;

	public void insertAppVersion(AppVersion appVersion) throws Exception;

	public void updateAppVersion(AppVersion appVersion) throws Exception;

	public AppVersion getAppVersionById(AppVersion appVersion) throws Exception;
	
	public int startAppVersion(@Param("appid")int appid,@Param("versionId")int versionId)throws Exception;

    public int removeAllAgent(int appId);
}
