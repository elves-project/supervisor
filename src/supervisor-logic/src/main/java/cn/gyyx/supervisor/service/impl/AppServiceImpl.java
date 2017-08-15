package cn.gyyx.supervisor.service.impl;

import cn.gyyx.elves.util.ExceptionUtil;
import cn.gyyx.elves.util.SecurityUtil;
import cn.gyyx.elves.util.mq.MessageProducer;
import cn.gyyx.supervisor.dao.AppDao;
import cn.gyyx.supervisor.dao.SupervisorDao;
import cn.gyyx.supervisor.model.App;
import cn.gyyx.supervisor.model.AppAgent;
import cn.gyyx.supervisor.model.AppVersion;
import cn.gyyx.supervisor.service.AppService;
import cn.gyyx.supervisor.timer.UpdateBindDataTimer;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppServiceImpl implements AppService{
  
	private static final Logger LOG=Logger.getLogger(AppServiceImpl.class);
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private SupervisorDao supervisorDao;
	
	@Autowired
	private MessageProducer messageProducer;

	@Autowired
	private UpdateBindDataTimer timer;
	
	@Override
	public List<App> getAllApp() {
		return appDao.getAllApp();
	}

	@Override
	public App valInstruct(String valInstruct) throws Exception {
		return appDao.valInstruct(valInstruct);
	}

	@Override
	public void addApp(Map<String, Object> map) throws Exception {
		appDao.addApp(map);
		noticeHeartbeat();   //给heartbeat发送心跳
	}

	@Override
	public void deleteApp(String id) throws Exception {
		appDao.deleteApp(id);
		noticeHeartbeat();
	}

	@Override
	public List<AppVersion> getVsersionList(int appId){
		return appDao.getVsersionList(appId);
	}

	@Override
	public App getAppById(int id) throws Exception {
		return appDao.getAppById(id);
	}

	@Override
	public void insertAppVersion(AppVersion appVersion) throws Exception {
		appDao.insertAppVersion(appVersion);
		noticeHeartbeat();
	}

	@Override
	public void updateAppVersion(AppVersion appVersion) throws Exception {
		appDao.updateAppVersion(appVersion);
		noticeHeartbeat();
	}

	@Override
	public AppVersion getAppVersionById(AppVersion appVersion) throws Exception {
		return appDao.getAppVersionById(appVersion);
	}

	@Override
	public int startAppVersion(int appid, int versionId) throws Exception {
		int flag= appDao.startAppVersion(appid, versionId);
		if(flag>0){
			noticeHeartbeat();
		}
		return flag;
	}
	
	/**
	 * @Title: noticeHeartbeat
	 * @Description: 通知heartbeat更新app信息
	 * @return void    返回类型
	 */
	@Override
	public void noticeHeartbeat(){
		List<App> list=appDao.getAllApp();
		
		List<Map<String,Object>> data =new ArrayList<>();
		for(App app:list){
			if(null==app.getVersionId()){
				//当前无版本
				continue;
			}
			if (null == app.getVersionId()) {
				continue;
			}
			List<String> bind = supervisorDao.queryAppAgentIp(app.getAppId());
			Map<String, Object> m =new HashMap<>();
			m.put("app",app.getInstruct());
			m.put("version", app.getVersion());
			m.put("agentList", bind==null?new ArrayList<String>():bind);
			data.add(m);
		}
		
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("result",data);
		
		Map<String,Object> msg=new HashMap<String,Object>();
		msg.put("mqkey","supervisor.heartbeat.updateAppInfo."+SecurityUtil.getUniqueKey());
		msg.put("mqtype","cast");
		msg.put("mqbody",result);
		
		String sendData=JSON.toJSONString(msg);
		messageProducer.cast("supervisor.heartbeat",sendData);
		LOG.info("send app update message finished, msg:"+sendData);
	}
	
	@Override
	public List<Map<String, Object>> getAgent(Integer appId, String ip, String assetId) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(ip)){
				param.put("ip", ip);
			}
			if(StringUtils.isNotBlank(assetId)){
				param.put("id", assetId);
			}
			
			Map<String, Object> back = messageProducer.call("supervisor.heartbeat","agentList", param,5000);
			LOG.info("search asset from heart back:"+JSON.toJSONString(back));
			
			if(null!=back&&null!=back.get("result")){
				List<Map<String, Object>> agentData = (List<Map<String, Object>>) back.get("result");
				List<AppAgent> appAgentList = supervisorDao.query(appId, ip, assetId);
				if(null==appAgentList||appAgentList.size()==0){
					return agentData;
				}
				List<Map<String, Object>> authData = new ArrayList<Map<String, Object>>();
				for(Map<String, Object> agent : agentData){
					if(null==agent.get("ip")){
						continue;
					}
					for(int i=0;i< appAgentList.size();i++){
						AppAgent appAgent =appAgentList.get(i);
						if(appAgent.getIp().equals(agent.get("ip").toString())){
							break;
						}
						if(i==appAgentList.size()-1){
							authData.add(agent);
						}
					}
				}
				return authData;
			}
		} catch (Exception e) {
			LOG.error(ExceptionUtil.getStackTraceAsString(e));
		}
		return new ArrayList<Map<String, Object>>();
	}
	
	@Override
	public List<AppAgent> getAppAgent(Integer appId, String ip, String assetId) {
		List<AppAgent> appAgentList = supervisorDao.query(appId, ip, assetId);
		return appAgentList==null?new ArrayList<AppAgent>():appAgentList;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean bindAgent(Integer appId, String appAgentList) {
		if (null == appId) {
			return false;
		}
		String[] arr = appAgentList.split(",");
		for(String appAgent: arr){
			String[] info = appAgent.split("[_]");
			if(info==null||info.length!=2){
				continue;
			}
			AppAgent app =new AppAgent();
			app.setAppId(appId);
			app.setIp(info[0].trim());
			app.setAssetId(info[1].trim());
			supervisorDao.addAppAgent(app);
		}
		noticeHeartbeat();
		return true;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean unBindAgent(String ids) {
		String[] arr =ids.split("[,]");
		for(String id : arr){
			supervisorDao.delAppAgent(Integer.parseInt(id));
		}
		noticeHeartbeat();
		return true;
	}

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean reBindAgent(int appId,List<String> ips) {
        appDao.removeAllAgent(appId);
        for(String ip:ips){
            if(StringUtils.isNotBlank(ip)){
                AppAgent app =new AppAgent();
                app.setAppId(appId);
                app.setIp(ip.trim());
                app.setAssetId(ip.trim());
                supervisorDao.addAppAgent(app);
            }
        }
        noticeHeartbeat();
        return true;
    }

}
