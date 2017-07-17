package cn.gyyx.supervisor.model;

public class Page {
    private String draw;
    private String order_column;
    private String order_dir;
    private Integer start;
    private Integer length;
    private String username;
    private String  email;
	public Page(String draw, String order_column, String order_dir, Integer start, Integer length, String username,
			String email) {
		super();
		this.draw = draw;
		this.order_column = order_column;
		this.order_dir = order_dir;
		this.start = start;
		this.length = length;
		this.username = username;
		this.email = email;
	}
	public Page() {
		super();
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public String getOrder_column() {
		return order_column;
	}
	public void setOrder_column(String order_column) {
		this.order_column = order_column;
	}
	public String getOrder_dir() {
		return order_dir;
	}
	public void setOrder_dir(String order_dir) {
		this.order_dir = order_dir;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
    
    
    
}
