package cn.gyyx.supervisor.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.gyyx.supervisor.model.User;

public class LoginFilter extends OncePerRequestFilter{
	private static final Logger LOG = Logger.getLogger(LoginFilter.class);
	private String basePath;
	
	@Override
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		LOG.debug("filt uri : " + requestURI);
		
		requestURI = requestURI.replaceAll("/+", "/");
		if (requestURI.equals("/")||requestURI.startsWith("/resources")|| requestURI.equals("/home/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		String path = request.getContextPath();
		this.basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		
		HttpSession session = request.getSession();
		
		User user=(User) session.getAttribute("curUser");
		if(null==user){
			java.io.PrintWriter out = response.getWriter();
		    out.println("<html>");
		    out.println("<script>");
		    out.println("window.open ('"+basePath+"','_top')");
		    out.println("</script>");
		    out.println("</html>");
		}else{
			filterChain.doFilter(request, response);
			return;
		}
	}
}