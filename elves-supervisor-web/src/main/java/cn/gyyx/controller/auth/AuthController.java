package cn.gyyx.controller.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.elves.util.mq.MessageProducer;
import cn.gyyx.supervisor.model.App;
import cn.gyyx.supervisor.model.AuthKey;
import cn.gyyx.supervisor.service.AppService;
import cn.gyyx.supervisor.service.AuthService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authServiceImpl;
	@Autowired
	private AppService appServiceImpl;
	
	@Autowired
	private MessageProducer messageProducer;
	
	@RequestMapping(value="view")
	public String home(Model model){
		List<App> appList=appServiceImpl.getAllApp();
		model.addAttribute("appList", appList==null?"":appList);
		return "auth/auth_page";
	}
	
	@RequestMapping(value="data")
	@ResponseBody
	public String data(Model model){
		List<AuthKey> list=authServiceImpl.getAllAuthKey();
		Map<String,Object> rs=new HashMap<String,Object>();
		rs.put("data",list);
		return JSON.toJSONString(rs);
	}
	
	@RequestMapping(value="add")
	@ResponseBody
	public String add(String authName,int appId){
		String flag=authServiceImpl.addAuthKey(authName,appId);
		return flag;
	}
	
	@RequestMapping(value="del")
	@ResponseBody
	public String del(String authIds){
		boolean flag=authServiceImpl.delAuthKey(authIds);
		if(flag){
			return "success";
		}
		return "fail";
	}
}
