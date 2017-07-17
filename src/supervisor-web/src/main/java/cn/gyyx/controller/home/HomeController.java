package cn.gyyx.controller.home;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gyyx.elves.util.MD5Utils;
import cn.gyyx.supervisor.model.User;
import cn.gyyx.supervisor.service.ManageService;

@Controller
@RequestMapping("home")
public class HomeController {
	
	private static final Logger LOG = Logger.getLogger(HomeController.class);
	
	@Autowired
	private ManageService manageServiceImpl;

	public static final int COOKIE_TIME = 60*60*24;
	
	
	@RequestMapping(value="view")
	public String home(){
		return "home/login";
	}
	
	
	@RequestMapping(value="logout")
	public void logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		session.setAttribute("curUser", null);
		LOG.info("quit suc.");
		response.getWriter().print("suc");
	}
	
	@RequestMapping(value="auth")
	public void authUser(HttpServletRequest request,HttpServletResponse response,String email,String password) throws Exception{
		User usercent=new User();
		usercent.setEmail(email);
		usercent.setPassword(MD5Utils.MD5(password));
		User user=manageServiceImpl.valUserCenter(usercent);
        
		if (null != user) {
			LOG.info("login suc.");
			user.setLoginTimes(user.getLoginTimes()+1);
			user.setLastLoginIp(request.getRemoteAddr());
			manageServiceImpl.reCordUserLogin(user);
			HttpSession session = request.getSession();
			session.setAttribute("curUser", user);
			session.setAttribute("username", user.getUsername());
			
			setCookie(response, "name", user.getUsername(),"/", COOKIE_TIME);
			setCookie(response, "email", user.getEmail() + "","/", COOKIE_TIME);
			response.getWriter().print("suc");
		}else{
			response.getWriter().print("false");
		}
		
	}
	@RequestMapping(value="accNuber")
	public void veriFication(HttpServletRequest request,HttpServletResponse response,String username)throws Exception{
		response.getWriter().print("true");
	}
	@RequestMapping(value="")
	public String login() throws Exception{
		return "authfound/framework";
	}
	
	@RequestMapping(value="passWordVal")
	public void authPassWord(HttpServletRequest request,HttpServletResponse response,String rowpassword)throws Exception{
		User user=(User) request.getSession().getAttribute("curUser");
		if(MD5Utils.MD5(rowpassword).equals(user.getPassword())){
			response.getWriter().print("true");
		}else{
			response.getWriter().print("false");
		}
	}
	@RequestMapping(value="updatePw")
	public void updatePassWord(HttpServletRequest request,HttpServletResponse response,String password) throws Exception{
		//获取用户信息
		HttpSession session = request.getSession();
		User user=(User) request.getSession().getAttribute("curUser");
		user.setPassword(MD5Utils.MD5(password));
		manageServiceImpl.updatePassWord(user);
		session.setAttribute("curUser", null);
		response.getWriter().print("success");
	}
	
	/**
	 * 设置 Cookie
	 * @param name 名称
	 * @param value 值
	 * @param maxAge 生存时间（单位秒）
	 * @param uri 路径
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		try {
			cookie.setValue(URLEncoder.encode(value, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.addCookie(cookie);
	}
	
}
