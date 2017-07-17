package cn.gyyx.supervisor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.elves.util.ExceptionUtil;
import cn.gyyx.elves.util.PageRealUtils;
import cn.gyyx.elves.util.mq.MessageProducer;
import cn.gyyx.supervisor.dao.SupervisorDao;
import cn.gyyx.supervisor.model.AppAgent;
import cn.gyyx.supervisor.model.Page;
import cn.gyyx.supervisor.service.AssetService;

import com.alibaba.fastjson.JSON;

@Service
public class AssetServiceImpl implements AssetService{
	
	private static final Logger LOG =Logger.getLogger(AssetServiceImpl.class);
	
	
	@Autowired
	private SupervisorDao supervisorDao;
	
	@Autowired
	private MessageProducer messageProducer;
	
	@Override
	public Map<String,Object> getAssetInfo(String ip,String assetId,Page page) {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(ip)){
				param.put("ip", ip);
			}
			if(StringUtils.isNotBlank(assetId)){
				param.put("id", assetId);
			}
			
			Map<String, Object> back = messageProducer.call("supervisor.heartbeat", "agentList",param,5000);
			LOG.info("search asset from heart back:"+JSON.toJSONString(back));
			List<Map<String, Object>> agentData =null;
			if(null!=back&&null!=back.get("result")){
				agentData = (List<Map<String, Object>>) back.get("result");
			}
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			//对数据进行分页显示 
			if( page.getStart()>-1 && page.getLength()>0){
				result=PageRealUtils.getPageDate(page.getStart(), page.getLength(), agentData);
			}
			map.put("recordsTotal",agentData==null?0:agentData.size());
			map.put("recordsFiltered", agentData==null?0:agentData.size());
			map.put("data", result);
			return map;
		} catch (Exception e) {
			LOG.error(ExceptionUtil.getStackTraceAsString(e));
			map.put("recordsTotal",0);
			map.put("recordsFiltered", 0);
			map.put("data", new ArrayList<Map<String,Object>>());
		}
		map.put("draw", page.getDraw());
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> searchCmdbInfo(String ip) {
		try {
			if(StringUtils.isBlank(ip)){
				return null;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ips", ip);
			Map<String, Object> back;
			back = messageProducer.call("supervisor.cmdbproxy","getIpInfo", param, 5000);
			
			LOG.info("search from cmdbproxy return data :"+JSON.toJSONString(back));
			if(null!=back&&null!=back.get("data")){
				List<Map<String, Object>> ipInfo = (List<Map<String, Object>>) back.get("data");
				if(ipInfo!=null&&ipInfo.size()==1){
					return ipInfo.get(0);
				}else{
					return null;
				}
			}
		} catch (Exception e) {
			LOG.info(ExceptionUtil.getStackTraceAsString(e));
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> searchAgent(String ip, String assetId,Integer appId) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(ip)){
				param.put("ip", ip);
			}
			if(StringUtils.isNotBlank(assetId)){
				param.put("id", assetId);
			}
			
			Map<String, Object> back = messageProducer.call("supervisor.heartbeat","agentList",param,5000);
			LOG.info("search asset from heart back:"+JSON.toJSONString(back));
			
			if(null!=back&&null!=back.get("result")){
				List<Map<String, Object>> agentData = (List<Map<String, Object>>) back.get("result");
				List<AppAgent> list = supervisorDao.query(0, "", "");
				if(null==list||list.size()==0){
					return agentData;
				}
				List<Map<String, Object>> backData = new ArrayList<Map<String, Object>>();
				for(Map<String, Object> agent : agentData){
					if(null==agent.get("ip")){
						continue;
					}
					for(int i=0;i< list.size();i++){
						AppAgent appAgent =list.get(i);
						if(appAgent.getIp().equals(agent.get("ip").toString())){
							break;
						}
						if(i==list.size()-1){
							backData.add(agent);
						}
					}
				}
				return backData;
			}
		} catch (Exception e) {
			LOG.error(ExceptionUtil.getStackTraceAsString(e));
		}
		return new ArrayList<Map<String, Object>>();
	}
	
}
