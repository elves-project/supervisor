package cn.gyyx.supervisor.service;

import java.util.List;

import cn.gyyx.supervisor.model.AuthKey;

public interface AuthService {

	public List<AuthKey> getAllAuthKey();
	
	public String addAuthKey(String authName,int appId);
	
	public boolean delAuthKey(String delAuthIds);
	
}
