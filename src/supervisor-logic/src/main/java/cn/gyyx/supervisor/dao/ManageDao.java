package cn.gyyx.supervisor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.supervisor.model.User;

public interface ManageDao {

	public int getCountNum(Map<String,Object> map)throws Exception; 
	
    public List<User> getDataList(Map<String,Object> map)throws Exception;  

    public void AddUser(User user) throws Exception;

    public void updateUser(User user) throws Exception;

    public User valEmail(String email) throws Exception;
    
    public User updateValEmail(@Param("email")String email,@Param("id")int id)throws Exception;
    
    public void deleteUser(String id)throws Exception;

    public User getUsers(String id)throws Exception;
    
    public User valUserCenter(User user) throws Exception;

	public void updatePassWord(User user) throws Exception;

	public void reCordUserLogin(User user) throws Exception;
    
}
