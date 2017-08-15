package cn.gyyx.supervisor.timer;

import cn.gyyx.elves.util.ExceptionUtil;
import cn.gyyx.elves.util.HttpUtil;
import cn.gyyx.supervisor.model.App;
import cn.gyyx.supervisor.service.AppService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * east.Fu
 *
 *  更新app绑定的Agent数据 定时器
 */
@Component("updateBindDataTimer")
public class UpdateBindDataTimer {

    private static final Logger LOG = Logger.getLogger(UpdateBindDataTimer.class);

    @Autowired
    private AppService appServiceImpl;

    public void update(){
        List<App> apps=appServiceImpl.getAllApp();
        boolean flag=false;
        for(App app:apps){
            if(StringUtils.isNotBlank(app.getBindUrl())){
                try {
                    String data = HttpUtil.sendGet(app.getBindUrl(),null);
                    Map<String,String> rs = JSON.parseObject(data,new TypeReference<Map<String,String>>(){});
                    List<Map<String,String>> dt =JSON.parseObject(rs.get("data"),new TypeReference<List<Map<String,String>>>(){});

                    List<String> ips=new ArrayList<String>();
                    for(Map m:dt){
                        if(null!=m.get("ip")&&StringUtils.isNotBlank(m.get("ip").toString())){
                            ips.add(m.get("ip").toString());
                        }
                    }
                    LOG.info("get agent from url:"+JSON.toJSONString(ips));
                    if(StringUtils.isNotBlank(data)){
                        appServiceImpl.reBindAgent(app.getAppId(),ips);
                        if(!flag){
                            flag=true;
                        }
                    }else{
                        LOG.error("url return data is empty,app:"+app.getInstruct()+",url:"+app.getBindUrl());
                    }
                }catch (Exception e){
                    LOG.error("update app bind agent exception,app:"+app.getInstruct()+",url:"+app.getBindUrl()+",msg:"+ ExceptionUtil.getStackTraceAsString(e));
                }
            }
        }
        //如果重新绑定数据成功，则通知heartbeat
        if(flag){
            appServiceImpl.noticeHeartbeat();
        }
    }

}
