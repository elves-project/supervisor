package cn.gyyx.supervisor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gyyx.elves.util.DateUtils;
import cn.gyyx.elves.util.SecurityUtil;
import cn.gyyx.supervisor.dao.AuthKeyDao;
import cn.gyyx.supervisor.model.AuthKey;
import cn.gyyx.supervisor.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthKeyDao authKeyDao;

	@Override
	public List<AuthKey> getAllAuthKey() {
		return authKeyDao.getAllAuthKey();
	}

	@Override
	public String addAuthKey(String authName,int appId) {
		List<AuthKey> list= authKeyDao.queryByAppId(appId);
		if(list.size()>0){
			//一个app绑定一个auth
			return "2";
		}
		
		AuthKey authKey = new AuthKey();
		authKey.setAuthId(SecurityUtil.getUniqueKey());
		authKey.setAuthKey(SecurityUtil.getUniqueKey());
		authKey.setCreateTime(DateUtils.currentTimestamp2String(null));
		authKey.setAppId(appId);
		authKey.setAuthName(authName);
		int flag = authKeyDao.add(authKey);
		if (flag > 0) {
			return "0";
		}
		return "1";
	}

	@Override
	@Transactional
	public boolean delAuthKey(String delAuthIds) {
		String ids[] = delAuthIds.split(",");
		for (String id : ids) {
			authKeyDao.del(id);
		}
		return true;
	}
}
