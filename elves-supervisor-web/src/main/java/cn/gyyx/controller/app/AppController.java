package cn.gyyx.controller.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.gyyx.elves.util.mq.PropertyLoader;
import cn.gyyx.supervisor.model.App;
import cn.gyyx.supervisor.model.AppAgent;
import cn.gyyx.supervisor.model.AppVersion;
import cn.gyyx.supervisor.model.User;
import cn.gyyx.supervisor.service.AppService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/app")
public class AppController {

	@Autowired
	private AppService appService;
	
	@RequestMapping(value = "/page")
	public String getPage(HttpServletRequest request, HttpServletResponse response) {
		return "app/appPage";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public String  getAppList() throws Exception {
		List<App> list = appService.getAllApp();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "/instruct")
	public void valInstruct(HttpServletRequest request, HttpServletResponse response, String instruct)
			throws Exception {
		boolean result = true;
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		App app = appService.valInstruct(instruct);
		if (app != null) {
			result = false;
		}
		map.put("valid", result);
		response.getWriter().print(JSON.toJSONString(map));
	}

	@RequestMapping(value = "/add")
	public void addApp(HttpServletRequest request, HttpServletResponse response, String centerid, String appname,String processorIp,String processorPort)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		User login = (User) request.getSession().getAttribute("curUser");
		map.put("centerid", centerid);
		map.put("appname", appname);
		map.put("founder", login.getUsername());
		if(processorIp!=null && !processorIp.equals("")){
			map.put("processorIp", processorIp);
		}else{
			map.put("processorIp", "");
		}
		if(processorPort!=null && !processorPort.equals("")){
			map.put("processorPort", processorPort);	
		}else{
			map.put("processorPort", "0");
		}
		appService.addApp(map);
		response.getWriter().print("suc");
	}

	@RequestMapping(value = "/delete")
	public void deleteApp(HttpServletRequest request, HttpServletResponse response, String id) throws Exception {
		String[] ids = id.split(",");
		for (String str : ids) {
			appService.deleteApp(str);
		}
		response.getWriter().print("suc");
	}

	@RequestMapping(value = "/versionList")
	@ResponseBody
	public String getVsersionList( Integer appId){
		List<AppVersion> list = appService.getVsersionList(appId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		return JSON.toJSONString(map);
	}

	
	@RequestMapping(value = "/startAppVer")
	public void startAppVersion(HttpServletRequest request, HttpServletResponse response, String appid, String id)
			throws Exception {
        int para=appService.startAppVersion(Integer.parseInt(appid), Integer.parseInt(id));
        if(para>0){
        	response.getWriter().print("suc");
        }else{
        	response.getWriter().print("fails");	
        }
		
	}

	@RequestMapping(value = "/appbyid")
	public void getAppById(HttpServletRequest request, HttpServletResponse response, String id) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		App app = appService.getAppById(Integer.parseInt(id));
		map.put("instruct", app.getInstruct());
		map.put("appname", app.getAppName());
		map.put("founder", app.getFounder());
		map.put("createTime", app.getCreateTime());
		response.getWriter().print(JSON.toJSONString(map));

	}

	@RequestMapping(value = "/verexist")
	public void valAppVerExist(HttpServletRequest request, HttpServletResponse response, String appversion,
			String appid) throws Exception {
		AppVersion appvers = new AppVersion();
		appvers.setAppId(Integer.parseInt(appid));
		appvers.setVersion(appversion);
		AppVersion appvl = appService.getAppVersionById(appvers);
		if (appvl != null) {
			response.getWriter().print("exist");
		} else {
			response.getWriter().print("noexist");
		}

	}

	@RequestMapping(value = "/upload")
	public void uploadAppFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("appFile") MultipartFile appFile, String appversion, String appid) throws Exception {
		User login = (User) request.getSession().getAttribute("curUser");
		AppVersion appver = new AppVersion();
		appver.setAppId(Integer.parseInt(appid));
		appver.setVersion(appversion);
		appver.setOperator(login.getUsername());
		// 获取zookeeper的key
		 String address=PropertyLoader.FTP_RES_IP;
		 String userName=PropertyLoader.FTP_RES_USER;
		 String password=PropertyLoader.FTP_RES_PASS;
		String flag = "success";
		if (appFile != null) {
			FTPClient ftpClient = new FTPClient();
			try {
				ftpClient.connect(address);
				ftpClient.login(userName, password);
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				// 设置上传目录
				// ftpClient.changeWorkingDirectory("/app");
				String fileName = appFile.getOriginalFilename();
				FTPFile[] fs = ftpClient.listFiles();
				if (fs != null && fs.length > 0) {
					for (int i = 0; i < fs.length; i++) {
						if (fs[i].getName().equals(fileName)) {
							flag = "repeat";
							// 获取app旧版本id
							AppVersion getappver = appService.getAppVersionById(appver);
							appver.setId(getappver.getId());
						}	
					}
				}
				OutputStream os = ftpClient.appendFileStream(fileName);
				byte[] bytes = new byte[1024];
				InputStream is = appFile.getInputStream();
				// 开始复制 其实net已经提供了上传的函数，但是我想可能是我这个版本有点问题
				// ftpClient.storeFile("", fis);
				// 于是我自己write出去了，其实我想都是一样的效果，在这里告诉大家这两种方式都能实现
				int c;
				// 暂未考虑中途终止的情况
				while ((c = is.read(bytes)) != -1) {
					os.write(bytes, 0, c);
				}
				os.flush();
				is.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().println(JSON.toJSONString(flag, true));
			} finally {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (flag.equals("repeat")) {// 更新旧版本
				appService.updateAppVersion(appver);
			} else if (flag.equals("success")) {// 增加新版本
				appService.insertAppVersion(appver);
			}
			Map<String,Object> maps=new HashMap<String,Object>();
			maps.put("flag", flag);
			response.getWriter().println(JSON.toJSONString(maps));
		}

	}

	@RequestMapping(value="searchAgent")
	@ResponseBody
	public String searchAgent(Integer appId,String ip,String assetId){
		List<Map<String, Object>> list =appService.getAgent(appId, ip, assetId);
		Map<String,Object> rs=new HashMap<String,Object>();
		rs.put("data",list);
		return JSON.toJSONString(rs);
	}
	
	@RequestMapping(value="searchAppAgent")
	@ResponseBody
	public String searchAppAgent(Integer appId,String ip,String assetId){
		List<AppAgent> list =appService.getAppAgent(appId, ip, assetId);
		Map<String,Object> rs=new HashMap<String,Object>();
		rs.put("data",list);
		return JSON.toJSONString(rs);
	}
	
	
	@RequestMapping(value="bind")
	@ResponseBody
	public String bind(Integer appId,String appAgentList){
		boolean flag=appService.bindAgent(appId, appAgentList);
		return flag?"success":"fail";
	}
	
	@RequestMapping(value="unBind")
	@ResponseBody
	public String unBind(String ids){
		if(StringUtils.isBlank(ids)){
			return "fail";
		}
		boolean flag=appService.unBindAgent(ids);
		return flag?"success":"fail";
	}
}
