package cn.gyyx.supervisor.timer;

import cn.gyyx.elves.util.ExceptionUtil;
import cn.gyyx.elves.util.HttpUtil;
import cn.gyyx.supervisor.model.App;
import cn.gyyx.supervisor.service.AppService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
                    if(StringUtils.isNotBlank(data)){
                        appServiceImpl.reBindAgent(app.getAppId(),null);
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
