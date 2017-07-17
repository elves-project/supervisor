<!-- meta Config -->
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	//系统配置
	String staticBasePath = "http://static-dev.gyyx.cn/";
	String staticOaPath = staticBasePath + "oa/v2";
%>
<script type="text/javascript">
	var _basePath = "<%=basePath %>";
</script>
<style>
    .datagrid-header-row td{family-size:14px;font-weight:bold;background-color:#D7EBF9}
</style>


<link href="<%=basePath %>resources/css/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>resources/js/validator/css/bootstrapValidator.css" rel="stylesheet">
<link href="<%=basePath %>resources/css/datepicker.css" rel="stylesheet"> 
<link href="<%=basePath %>resources/layer/skin/default/layer.css" rel="stylesheet"> 
<link href="<%=basePath %>resources/DataTables-1.10.12/media/css/dataTables.jqueryui.css" rel="stylesheet"> 

<script src="<%=basePath %>resources/js/jquery-1.11.3.min.js"></script>
<script src="<%=basePath %>resources/DataTables-1.10.12/media/js/jquery.dataTables.min.js"></script>

<script src="<%=basePath %>resources/js/bootstrap.min.js"></script>
<script src="<%=basePath %>resources/js/validator/js/bootstrapValidator.js"></script>
<script src="<%=basePath %>resources/js/bootstrap-datepicker.js"></script> 

 <script src="<%=basePath %>resources/js/jquery.validate.min.js"></script>
<script src="<%=basePath %>resources/js/messages_zh.js"></script> 
<script src="<%=basePath %>resources/layer/layer.js"></script>

<script src="<%=basePath %>resources/js/ajaxfileupload.js"></script> 
<script type="text/javascript">
</script>