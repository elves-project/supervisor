package cn.gyyx.supervisor.service;

import java.util.List;
import java.util.Map;

import cn.gyyx.supervisor.model.Page;

public interface AssetService {
	
	public Map<String,Object> getAssetInfo(String ip,String assetId,Page page);

	public Map<String,Object> searchCmdbInfo(String ip);
	
	public List<Map<String,Object>> searchAgent(String ip,String assetId,Integer appId);
	
}
