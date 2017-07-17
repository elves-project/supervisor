package cn.gyyx.supervisor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.elves.util.MD5Utils;
import cn.gyyx.supervisor.dao.ManageDao;
import cn.gyyx.supervisor.model.User;
import cn.gyyx.supervisor.service.ManageService;

@Service
public class ManageServiceImpl implements ManageService{

	@Autowired
	private ManageDao manageDao;
	
	@Override
	public int getCountNum(Map<String,Object> map) throws Exception {
		return manageDao.getCountNum(map);
	}

	@Override
	public List<User> getDataList(Map<String,Object> map) throws Exception {
		return manageDao.getDataList(map);
	}

	@Override
	public void addUser(User user) throws Exception {
		user.setPassword(MD5Utils.MD5("123456"));
		manageDao.AddUser(user);
	}

	@Override
	public User valEmail(String email) throws Exception {
		return manageDao.valEmail(email);
	}
	
	@Override
	public User updateValEmail(String email,int id)throws Exception{
		return manageDao.updateValEmail(email, id);
	}

	@Override
	public void updateUser(User user) throws Exception {
		manageDao.updateUser(user);
	}

	@Override
	public void deleteUser(String id) throws Exception {
		manageDao.deleteUser(id);
	}

	@Override
	public User getUsers(String id) throws Exception {
		return manageDao.getUsers(id);
	}
	
	@Override
	public User valUserCenter(User user) throws Exception {
		return manageDao.valUserCenter(user);
	}

	@Override
	public void updatePassWord(User user) throws Exception {
		manageDao.updatePassWord(user);
	}

	@Override
	public void reCordUserLogin(User user) throws Exception {
		manageDao.reCordUserLogin(user);
	}

}
