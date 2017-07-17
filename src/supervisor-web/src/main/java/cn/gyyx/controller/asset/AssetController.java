package cn.gyyx.controller.asset;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.elves.util.DateUtils;
import cn.gyyx.elves.util.mq.MessageProducer;
import cn.gyyx.supervisor.model.Page;
import cn.gyyx.supervisor.service.AssetService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/asset")
public class AssetController {
	
	@Autowired
	private AssetService assetServiceImpl;
	
	@Autowired
	private MessageProducer messageProducer;
	
	@RequestMapping(value="view")
	public String home(HttpServletRequest request,Model model){
		return "asset/asset_data";
	}
	
	@RequestMapping(value="data")
	@ResponseBody
	public String assetData(String ip,String assetId,Page page){
		Map<String,Object> map=assetServiceImpl.getAssetInfo(ip,assetId,page);
		return JSON.toJSONString(map);
	}
	
	
	@RequestMapping(value="searchCmdb")
	@ResponseBody
	public String searchCmdbInfo(String ip){
		Map<String, Object> back = assetServiceImpl.searchCmdbInfo(ip);
		return back==null?"":JSON.toJSONString(back);
	}
	
	@RequestMapping(value="searchAgent")
	@ResponseBody
	public String searchAppAgent(String ip,String assetId,Integer appId){
		List<Map<String, Object>> back = assetServiceImpl.searchAgent(ip, assetId, appId);
		Map<String,Object> rs=new HashMap<String,Object>();
		rs.put("data",back);
		return JSON.toJSONString(rs);
	}
	
}
