package cn.gyyx.elves.util.zk;

import java.io.UnsupportedEncodingException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.listen.ListenerContainer;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;

import cn.gyyx.elves.util.ExceptionUtil;

/**
 * @ClassName: ZookeeperExcutor
 * @Description: zookeeper连接处理器
 * @author East.F
 * @date 2016年11月7日 上午9:33:48
 */
public class ZookeeperExcutor {

	private static final Logger LOG=Logger.getLogger(ZookeeperExcutor.class);
	
	private CuratorFramework client;
	
	public ZookeeperExcutor(String zklist,int sessionTimeout,int connectTimeout){
		client = CuratorFrameworkFactory.builder()
				.connectString(zklist).sessionTimeoutMs(sessionTimeout)
				.connectionTimeoutMs(connectTimeout)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();
	}
	
	public CuratorFramework getClient() {
		return client;
	}

	/**
	 * @Title: createNodeAddListener
	 * @Description: 添加node节点
	 * @param nodePath
	 * @param nodeData 设定文件
	 * @return void    返回类型
	 */
	public String createNode(String nodePath,String nodeData){
		if(client!=null){
			try {
				String nodeName=client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
				.forPath(nodePath, nodeData.getBytes("UTF-8"));
				return nodeName;
			} catch (UnsupportedEncodingException e) {
				LOG.error(ExceptionUtil.getStackTraceAsString(e));
				return null;
			} catch (Exception e) {
				LOG.error(ExceptionUtil.getStackTraceAsString(e));
				return null;
			}
		}
		return null;
	}
	/**
	 * @Title: getListener
	 * @Description: 为节点添加 connectState 监听器，实现断线重连，然后添加上节点
	 * @param nodePath	节点路径
	 * @param nodeData 节点数据
	 * @return void    返回类型
	 */
	public ConnectionStateListener getListener(final String nodePath,final String nodeData){
		if(null!=client){
			ConnectionStateListener connectListener = new ConnectionStateListener() {
				@Override
				public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
					LOG.error("connectionState change,state:"+connectionState);
					if (connectionState == ConnectionState.LOST) {
						while (true) {
							try {
								//手动重连
								boolean flag=curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut();
								if (flag){
									//重新添加节点
									clearListener();
									createNode(nodePath, nodeData);
									client.getConnectionStateListenable().addListener(getListener(nodePath, nodeData));
									break;
								}
							} catch (InterruptedException e) {
								LOG.error(ExceptionUtil.getStackTraceAsString(e));
							} catch (Exception e) {
								LOG.error(ExceptionUtil.getStackTraceAsString(e));
							}
						}
					}else if(connectionState==ConnectionState.RECONNECTED){
						//重新连接成功
					}else if(connectionState==ConnectionState.SUSPENDED){
						//自动重连,自动新建 schedular的临时节点
					}
				}
				
			};
			return connectListener;
		}
		return null;
	}
	
	public void clearListener(){
		ListenerContainer<ConnectionStateListener> list=(ListenerContainer<ConnectionStateListener>) client.getConnectionStateListenable();
		list.clear();
	}
	
	public void addListener(String nodePath,String nodeData){
		client.getConnectionStateListenable().addListener(getListener(nodePath, nodeData));
	}
	
}
