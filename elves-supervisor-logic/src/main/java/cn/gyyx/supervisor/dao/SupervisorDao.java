package cn.gyyx.supervisor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.supervisor.model.AppAgent;

public interface SupervisorDao {
	
	public List<AppAgent> query(@Param("appId")int appId,@Param("ip")String ip,@Param("assetId")String assetId);
	
	public List<String> queryAppAgentIp(int appId);
	
	public int addAppAgent(AppAgent appAgent);
	
	public int delAppAgent(int id);
	
}
