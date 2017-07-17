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
import cn.gyyx.elves.util.mq.MessageProducer;
import cn.gyyx.supervisor.dao.AppDao;
import cn.gyyx.supervisor.dao.AuthKeyDao;
import cn.gyyx.supervisor.dao.SupervisorDao;
import cn.gyyx.supervisor.model.App;
import cn.gyyx.supervisor.model.AppAgent;
import cn.gyyx.supervisor.model.AuthKey;
import cn.gyyx.supervisor.service.SupervisorService;

/**
 * @ClassName: SupervisorServiceImpl
 * @Description: supervisor mq接口服务实现类
 * @author East.F
 * @date 2016年12月9日 下午4:00:00
 */
@Service("elvesConsumerService")
public class SupervisorServiceImpl implements SupervisorService {

	private static final Logger LOG =Logger.getLogger(SupervisorServiceImpl.class);

	@Autowired
	private AppDao appDao;

	@Autowired
	private AuthKeyDao authKeyDao;

	@Autowired
	private SupervisorDao supervisorDao;

	@Autowired
	private MessageProducer messageProducer;

	@Override
	public Map<String, Object> appAuthInfo(Map<String, Object> params){
		Map<String, Object> rs = new HashMap<String, Object>();
		List<App> list = appDao.getAllApp();
		List<Map<String, Object>> result = new ArrayList<>();
		for (App app : list) {
			if (null == app.getVersionId()) {
				continue;
			}
			List<String> bind = supervisorDao.queryAppAgentIp(app.getAppId());
			Map<String, Object> m =new HashMap<>();
			m.put("app",app.getInstruct());
			m.put("version", app.getVersion());
			m.put("agentList", bind==null?new ArrayList<String>():bind);
			result.add(m);
		}
		rs.put("result", result);
		return rs;
	}

	@Override
	public Map<String, Object> getAuthKey(Map<String, Object> params){
		String authId=params.get("auth_id")==null?"":params.get("auth_id").toString().trim();
		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			AuthKey key = authKeyDao.query(authId);
			if(null==key){
				rs.put("flag", "false");
				rs.put("error", "[413.1]AuthId Not Found");
			}else{
				rs.put("flag", "true");
				rs.put("error", "");
				rs.put("auth_key", key.getAuthKey());
			}
		} catch (Exception e) {
			String error = ExceptionUtil.getStackTraceAsString(e);
			LOG.error("getAuthKey error : "+error);
			rs.put("flag", "false");
			rs.put("error", "[500]"+error);
		}
		return rs;
	}

	@Override
	public Map<String, Object> validateAuth(Map<String, Object> params){
		String authId=params.get("auth_id")==null?"":params.get("auth_id").toString().trim();
		String app=params.get("app")==null?"":params.get("app").toString().trim();
		String ip=params.get("ip")==null?"":params.get("ip").toString().trim();

		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			//app是否存在
			AuthKey key = authKeyDao.query(authId);
			if(key==null){
				rs.put("flag", "false");
				rs.put("error", "[413.1]AuthId Not Found");
				return rs;
			}

			if(StringUtils.isNoneBlank(app)){
				App a =appDao.query(app);
				if(a==null){
					rs.put("flag", "false");
					rs.put("error", "[413.2]App Not Found");
					return rs;
				}

				//根据authId查询对应的app
				if(!key.getInstruct().equals(app)){
					rs.put("flag", "true");
					rs.put("error", "");
					rs.put("result", "false");
					return rs;
				}
			}

			if(StringUtils.isNotBlank(ip)){
				//查询app绑定的ip列表 是否包含这个ip
				List<AppAgent> list = supervisorDao.query(key.getAppId(), ip, "");
				if(list.size()==1){
					rs.put("flag", "true");
					rs.put("error", "");
					rs.put("result", "true");
				}else{
					rs.put("flag", "false");
					rs.put("error", "");
					rs.put("result", "false");
				}
			}else{
				rs.put("flag", "true");
				rs.put("error", "");
				rs.put("result", "true");
			}
			return rs;
		} catch (Exception e) {
			String error = ExceptionUtil.getStackTraceAsString(e);
			LOG.error("getAuthKey error : "+error);
			rs.put("flag", "false");
			rs.put("error", "[500]"+error);
			return rs;
		}
	}

	@Override
	public Map<String, Object> appInfo(Map<String, Object> params){
		String authId=params.get("auth_id")==null?"":params.get("auth_id").toString().trim();
		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			Map<String, Object> result = new HashMap<String, Object>();

			AuthKey key = authKeyDao.query(authId);
			if(key==null){
				rs.put("flag", "false");
				rs.put("error", "[413.1]AuthId Not Found");
				return rs;
			}

			App app =authKeyDao.queryAppByAuthId(authId);
			if(null==app){
				rs.put("flag","false");
				rs.put("error","[413.2]App Not Found");
			}else{
				result.put("app",app.getInstruct());
				result.put("app_name",app.getAppName());
				result.put("app_ver",app.getVersion());
				result.put("last_update_time",app.getUpdateTime()==null?"":app.getUpdateTime().substring(0,app.getUpdateTime().length()-2));

				rs.put("flag","true");
				rs.put("error","");
				rs.put("result",result);
			}
		} catch (Exception e) {
			String error = ExceptionUtil.getStackTraceAsString(e);
			LOG.error("getAuthKey error : "+error);
			rs.put("flag", "false");
			rs.put("error", "[500]"+error);
		}
		return rs;
	}

	@Override
	public Map<String, Object> agentList(Map<String, Object> params){
		String authId=params.get("auth_id")==null?"":params.get("auth_id").toString().trim();
		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			AuthKey key = authKeyDao.query(authId);
			if(null==key){
				rs.put("flag", "false");
				rs.put("error", "[413.1]AuthId Not Found");
			}else{
				List<String> bind = supervisorDao.queryAppAgentIp(key.getAppId());
				rs.put("flag", "true");
				rs.put("error", "");
				rs.put("result", bind==null?new ArrayList<String>():bind);
			}
		} catch (Exception e) {
			String error = ExceptionUtil.getStackTraceAsString(e);
			LOG.error("getAuthKey error : "+error);
			rs.put("flag", "false");
			rs.put("error", "[500]"+error);
		}
		return rs;
	}

}
