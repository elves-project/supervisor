package cn.gyyx.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.supervisor.model.Page;
import cn.gyyx.supervisor.model.User;
import cn.gyyx.supervisor.service.ManageService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/manage")
public class ManageController {
     
	@Autowired
	private ManageService manageService;
	
	@RequestMapping(value="/page")
	public String getPageList() throws Exception{
		return "manage/buth";
	}
	@RequestMapping(value="/list")
	public void getDataList(HttpServletRequest request,HttpServletResponse response,Page page) throws Exception{
	    Map<String,Object> map=new HashMap<String,Object>();
	    if(page.getUsername()!=null){
	        map.put("username", page.getUsername());
	    }
	    if(page.getEmail()!=null){
	      map.put("email", page.getEmail());
	    }
	    map.put("startNum", page.getStart());
	    map.put("recordNum", page.getLength());
		int count=manageService.getCountNum(map);
		List<User> list=manageService.getDataList(map);
		
		for(User us:list){
			us.setCreateTime(us.getCreateTime().substring(0, us.getCreateTime().length()-2));
			us.setUpdateTime(us.getUpdateTime().substring(0, us.getUpdateTime().length()-2));
			if(us.getLastLoginTime()==null){
				us.setLastLoginTime("");
			}else{
				us.setLastLoginTime(us.getLastLoginTime().substring(0, us.getLastLoginTime().length()-2));
			}
			if(us.getLastLoginIp()==null){
				us.setLastLoginIp("");;
			}
		}
		map.put("draw", page.getDraw());
		map.put("recordsTotal", count);
		map.put("recordsFiltered", count);
		map.put("data", list);
		response.getWriter().print(JSON.toJSONString(map));
	}
	@RequestMapping(value="/add")
	public void AddUser(HttpServletRequest request,HttpServletResponse response,User user) throws Exception{
        User login=(User)request.getSession().getAttribute("curUser");
		user.setFounder(login.getUsername());
		manageService.addUser(user);
		response.getWriter().print("suc");
	}
	@RequestMapping(value="/update")
    public void UpdateUser(HttpServletRequest request,HttpServletResponse response,User user) throws Exception{
		manageService.updateUser(user);
		response.getWriter().print("suc");
	}
	
	@RequestMapping(value="/delete")
    public void deleteUser(HttpServletRequest request,HttpServletResponse response,String id) throws Exception{
		String[] ids=id.split(",");
		for(String str:ids){
			manageService.deleteUser(str);
		}
		response.getWriter().print("suc");
	}
	
	@RequestMapping(value="/valemail")
    public void ValidationEmail(HttpServletRequest request,HttpServletResponse response,String email,String id) throws Exception{
		boolean result=true;
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		User user=null;
		if(id!=null && !id.equals("")){
			user=manageService.updateValEmail(email, Integer.parseInt(id));
		}else{
			user=manageService.valEmail(email);
		}
        if(user!=null){
        	result=false;
        }
		map.put("valid", result);
        response.getWriter().print(JSON.toJSONString(map));
	}
	
	@RequestMapping(value="/user")
    public void getUser(HttpServletRequest request,HttpServletResponse response,String id) throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        User user=manageService.getUsers(id);
        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        map.put("founder", user.getFounder());
		response.getWriter().print(JSON.toJSONString(map));
	}
	
}
