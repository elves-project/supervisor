package cn.gyyx.supervisor.model;



public class User {
	private Integer id;
	private String email;
    private String username;
    private String password;
    private String founder;//创建人
    private String createTime;
    private String updateTime;
    private String lastLoginTime;
    private String lastLoginIp;
    private int loginTimes;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFounder() {
		return founder;
	}
	public void setFounder(String founder) {
		this.founder = founder;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public int getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}
	public User(Integer id, String email, String username, String password, String founder, String createTime,
			String updateTime, String lastLoginTime, String lastLoginIp, int loginTimes) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.founder = founder;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.lastLoginTime = lastLoginTime;
		this.lastLoginIp = lastLoginIp;
		this.loginTimes = loginTimes;
	}
	public User() {
		super();
	}
    
	
    
    
}
