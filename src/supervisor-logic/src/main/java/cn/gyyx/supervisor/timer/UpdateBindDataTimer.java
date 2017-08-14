package cn.gyyx.supervisor.timer;

import cn.gyyx.supervisor.dao.AppDao;
import cn.gyyx.supervisor.model.App;
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
    private AppDao appDao;

    public void update(){
        new Thread(){
            @Override
            public void run(){
                List<App> apps=appDao.getAllApp();
                for(App app:apps){
                    if(StringUtils.isNotBlank(app.getBindUrl())){
                        //http 请求url  获取agent数据

                        //清空 agent表

                        //插入agent数据

                    }
                }
            }
        }.start();
    }

}
