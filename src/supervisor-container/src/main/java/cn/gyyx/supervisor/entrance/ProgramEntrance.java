package cn.gyyx.supervisor.entrance;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
public class ProgramEntrance {

    /**
     * 添加系统环境变量，供server.war 寻找配置文件路径
     */
    public static void  addSystemProperties(String path){
        System.setProperty("SUPERVISOR_PATH",path);
    }

    /**
     * 启动jetty服务，加载server.war
     */
    public static void startJettyServer(String path) throws  Exception{
        String configPath=path+ File.separator+"conf"+File.separator+"conf.properties";
        InputStream is = new FileInputStream(configPath);;
        Properties properties =new Properties();
        properties.load(is);
        is.close();
        int serverPort = Integer.parseInt(properties.getProperty("server.port"));
        Server server = new Server(serverPort);
        WebAppContext context = new WebAppContext();
        context.setWar(path+"/bin/ROOT.war");
        server.setHandler(context);
        server.start();
        server.join();
    }

    public static void main(String[] args) {
        try {
            addSystemProperties(args[0]);
            startJettyServer(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
