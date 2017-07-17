<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String userName=request.getSession().getAttribute("username").toString();
%>

	<!-- nav:start -->
   <a href="javascript:void(0);" class="list-group-item active">基础管理</a>
   <a href="javascript:void(0);" class="list-group-item" onclick="open_url('/auth/view',this)">Auth管理</a>
   <a href="javascript:void(0);" class="list-group-item" onclick="open_url('/app/page',this)">APP管理</a>
   <a href="javascript:void(0);" class="list-group-item" onclick="open_url('/asset/view',this)">Agent列表</a>
   <a href="javascript:void(0);" class="list-group-item active">后台管理</a>
   <a href="javascript:void(0);" class="list-group-item" onclick="open_url('/manage/page',this)">用户管理</a>

