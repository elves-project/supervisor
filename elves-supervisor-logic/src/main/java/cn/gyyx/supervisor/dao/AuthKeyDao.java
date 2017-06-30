package cn.gyyx.supervisor.dao;

import java.util.List;

import cn.gyyx.supervisor.model.App;
import cn.gyyx.supervisor.model.AuthKey;

public interface AuthKeyDao {

	public List<AuthKey> getAllAuthKey();
	
	public AuthKey query(String authId);
	
	public List<AuthKey> queryByAppId(int appId);
	
	public int add(AuthKey authKey);
	
	public int del(String authKeyId);
	
	public App queryAppByAuthId(String authId);
	
}
