import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gyyx.elves.util.SpringUtil;
import cn.gyyx.supervisor.service.SupervisorService;

public class test {

	Map<String, Object> params =new HashMap<String, Object>();
	
	SupervisorService server =null;
	
	@Before
	public void init(){
		SpringUtil.app = new ClassPathXmlApplicationContext(new String[] { "conf/spring.xml","conf/spring-mvc.xml" });
		System.out.println(SpringUtil.app );
		server=(SupervisorService) SpringUtil.app.getBean("elvesConsumerService");
		System.out.println(server);
	}
	
	@Test
	public void testAppAuthInfo(){
		System.out.println(server.appAuthInfo(params));	
	}
	
	@Test
	public void testGetAuthKey(){
		params.put("auth_id","C3F0B22256B4ED76");
//		params.put("auth_id","C3F0B22256B4ED77");
		System.out.println(server.getAuthKey(params));
	}
	
	@Test
	public void testValidateAuth(){
		params.put("auth_id","C3F0B22256B4ED76");
//		params.put("auth_id","C3F0B22256B4ED77");
		params.put("app","base");
//		params.put("app","base1");
		params.put("ip","192.168.10.104");
//		params.put("ip","192.168.10.105");
		System.out.println(server.validateAuth(params));
	}
	
	@Test
	public void testAppInfo(){
		params.put("auth_id","C3F0B22256B4ED76");
//		params.put("auth_id","C3F0B22256B4ED77");
		System.out.println(server.appInfo(params));
	}
	@Test
	public void testAgentList(){
		params.put("auth_id","C3F0B22256B4ED76");
//		params.put("auth_id","C3F0B22256B4ED77");
		System.out.println(server.agentList(params));
	}

}
