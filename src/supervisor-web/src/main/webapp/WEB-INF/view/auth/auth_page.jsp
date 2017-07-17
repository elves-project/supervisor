<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading">
		<div style="text-align: right;">
			<button type="button" class="btn btn-success"
				onclick="showAddDiv();">
				<span class="glyphicon glyphicon-plus"></span>
			</button>
			<button class="btn btn-danger" onclick="delAuth();">
				<span class="glyphicon glyphicon-trash"></span>
			</button>
		</div>
	</div>
	<table class="table table-striped table-bordered" style="border:0px" id="authInfo">
		<thead>
			<tr>
				<th style="text-align:center;">
					<input type="checkbox" id="checkAll"/>
				</th>
				<th style="text-align: center;">App</th>
				<th style="text-align: center;">AuthID</th>
				<th style="text-align: center;">AuthKey</th>
				<th style="text-align: center;">创建时间</th>
				<th style="text-align: center;">备注</th>
			</tr>
		</thead>
	</table>
</div>

<div class="modal fade bs-example-modal-sm adddialog" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加密钥</h4>
			</div>
			<div class="modal-body">
				<form id="addform" action="" class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-md-4 control-label">名称:</label>
						<div class="col-md-18">
							<input type="text" class="form-control" id="authName" name="keyname" placeholder="请输入密钥名称">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">App:</label>
						<div class="col-md-18">
							<select class="form-control" id="appSelect">
								<option value="">请选择APP</option>
								<c:forEach var="item" items="${appList}">
									<option value="${item.appId}">${item.appName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal" id="close">
					<span class="glyphicon glyphicon-remove"></span>
				</button>
				<button type="button" class="btn btn-primary"
					onclick="addAuth();">
					<span class="glyphicon glyphicon-floppy-disk"></span>
				</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function() {
	$('#agentButhWindow').on('hide.bs.modal', function () {
		$("#dialoginfo").DataTable().destroy();
	});
	
	$("#authInfo").dataTable({
		"sProcessing" : true,
		"processing" : true,
		"serverSide" : true,
		"paging" : false,
		"lengthChange" : true,
		"searching" : true,
		"ordering" : false,
		"dom" :"trip",
		"ajax" : {
			"url" : '<%=basePath%>auth/data',
			"type" : "POST"
		},
		"autoWidth" : false,
		"aaSorting" : [ [ 2, "asc" ] ],
		"serverSide" : true,
		"b<span style='color:#ff0000;'>Processing" : true,
		"bFilter" : false,
		"bRetrieve" : true,
		"sPaginationType" : "full_numbers",
		"scrollY": "550px",
		"scrollCollapse": true,
		"bPaginate" : true,
		"bInfo" : false,
		"columns" : [
				{
					"sClass" : "text-center",
					"orderable" : false,
					"data" : "authId",
					"render" : function(data, type,
							row, meta) {
						return "<input type='checkbox'  value='"+data+"'name='ckbox'/>";
					}
				},
				{
					"sClass" : "text-center",
					"data" : "authName"
				},{
					"sClass" : "text-center",
					"data" : "authId"
				},{
					"sClass" : "text-center",
					"data" : "authKey"
				},{
					"sClass" : "text-center",
					"data" : "createTime",
					"defaultContent": ""
				},{
					"sClass" : "text-center",
					"data" : "appName",
					"defaultContent": ""
				}],
			"language" : {
				"sProcessing" :"正在加载中......"
			}
	});
	$("#checkAll").on("click",function() {
		if ($(this).prop("checked")) {
			$("input[name='ckbox']").prop("checked", $(this).prop("checked"));
		} else {
			$("input[name='ckbox']").prop("checked", false);
		}
	});
	
});

function addAuth(){
	var authName=$("#authName").val();
	if(authName==''){
		alert("请输入密钥名称！");
		return;
	}
	var appId =$("#appSelect").val();
	if(appId==''){
		alert("请选择要绑定的App！");
		return;
	}
	$.ajax({
		url:"<%=basePath%>auth/add",
		data:{'authName':authName,"appId":appId},
		type:'POST',
		success:function(data){
			if(data=="0"){
				alert("新增密钥成功！");
				reloadDataTables("authInfo");
				$("#close").click();
			}else if(data=="1"){
				alert("新增密钥失败！");
			}else{
				alert("该App已经被绑定，请重新选择App！");
			}
		},
		error:function(){
			alert("新增密钥失败！");
		}
	});
}

function delAuth(){
	var delAuthIds="";
	$("input[name='ckbox']").each(function(){
		if($(this).prop("checked")){
			delAuthIds+=$(this).val();
			delAuthIds+=",";
		}
	});
	if(delAuthIds==""){
		alert("请选择要删除的密钥!");
		return;
	}else{
		delAuthIds=delAuthIds.substring(0,delAuthIds.length-1);
	}
	$.ajax({
		url:"<%=basePath%>auth/del",
		data:{'authIds':delAuthIds},
		success:function(data){
			if(data="success"){
				alert("删除密钥成功！");
				reloadDataTables("authInfo");
				$("#close").click();
			}else{
				alert("删除密钥失败！");
			}
		},
		error:function(){
			alert("删除密钥失败！");
		}
	});
}
//显示新增窗口
function showAddDiv(){
	$("#authName").val("");
	$(".adddialog").modal('toggle');
}
</script>
