<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Customer List</title>
<%-- 	<%@ include file="/resources/common/commonJsAndCss.jsp" %> --%>
  </head>
  
  <body>
  <script type="text/javascript">
		$(document).ready(function (){
			var _url = "<%=basePath %>login.jsp";
			window.location.href = _url;
		});
	</script>
    Seesion Out 用户未登录或登录超时<br/>
    </body>
</html>
