<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading">
		<form class="form-inline" role="form">
			<div class="form-group" style="margin-left:15px;">
				<label class="sr-only" for="exampleInputPassword2" id="ip">IP</label>
				<input id="ipes" type="text" class="form-control" placeholder="IP"/>
			</div>
			<div class="form-group" style="margin-left:15px;">
				<label class="sr-only" for="exampleInputEmail2">标识</label>
				<input id="assetIdes" type="text" class="form-control" placeholder="请输入标识"/>
			</div>
			<button type="button" class="btn btn-info" onClick="searchAsset()"style="margin-left:15px;">
				<span class="glyphicon glyphicon-search"></span>
			</button>
		</form>
	</div>
	<div style="overflow-y:auto;height:90%;">
		<table class="table table-striped table-bordered" style="border:0px" id="assetinfo" >
			<thead>
				<tr>
					<th style="text-align: center;">序号</th>
					<th style="text-align: center;">IP</th>
					<th style="text-align: center;">标识</th>
					<th style="text-align: center;">心跳时间</th>
					<th style="text-align: center;">APP</th>
					<th style="text-align: center;">CMDB详情</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div >
</div>

<div id="detail" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" style="top:150px">
	<div class="modal-dialog modal-sm">
    	<div class="modal-content"style="width:550px;height: 150px;display:inline-block;">
		   <div class="modal-body" id="ipContent" >
    	</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		initTable();
	});
	
	function searchAsset(){
		$('#assetinfo').DataTable().ajax.reload();
	}
	
	function initTable(){
		$("#assetinfo").dataTable({
			"sProcessing" : true,
			"processing" : true,
			"serverSide" : true,
			"paging" : true,
			"lengthChange" : true,
			"searching" : true,
			"ordering" : false,
			"scrollX": false,
			"scrollY": "530px",
			"dom" :"trip",
			"ajax" : {
				"url" : '<%=basePath%>asset/data',
				"type" : "POST",
				"data":function(d){
					d.assetId=$("#assetIdes").val(),
					d.ip=$("#ipes").val()
				}
			},
			"autoWidth" : false,
			"aaSorting" : [ [ 0, "asc" ] ],
			"pageLength" : 20,
			"serverSide" : true,
			"LoadingRecords":true,
			"bFilter" : false,
			"bRetrieve" : true,
			"sPaginationType" : "full_numbers",
			"bPaginate" : true,
			"bInfo" : true,
			"Info": true,
			"b<span style='color:#ff0000;'>Processing" : true,
			"columns" : [
					{
						"sClass": "dt-center",
						"data" : null,
						"sWidth" : "6%",
						"targets": 0
					},
					{
						"sClass" : "text-center",
						"data" : "ip",
						"sWidth" : "8%",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "asset",
						"sWidth" : "8%",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "last_hb_time",
						"sWidth" : "15%",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "apps",
						"defaultContent": "",
						"render" : function(data, type,row, meta) {
							return JSON.stringify(data);
						}
					},
					{
						"sClass" : "text-center",
						"data" : "ip",
						"sWidth" : "10%",
						"defaultContent": "",
						"render" : function(data, type,row, meta) {
							return "<button class='btn btn-info' id='"+row.ip+"' title='Popover title' data-container='body' data-toggle='popover' data-placement='left'  onclick='searchFromCMDB(\""+row.ip+"\");'><span class='glyphicon glyphicon-list'></span></button>";
						}
					}],
			"language" : {
				"sLengthMenu": "显示 _MENU_ 记录",
                "sZeroRecords": "对不起，查询不到任何相关数据",
                "sEmptyTable": "未有相关数据",
                "sLoadingRecords": "正在加载数据-请等待...",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录。",
                "sInfoEmpty": "当前显示0到0条，共0条记录",
				"Info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
				"sProcessing" : '<i class="fa fa-coffee"></i> 正在加载数据...',
				"sLoadingRecords": "载入中...",
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "前一页",
					"sNext" : "后一页",
					"sLast" : "尾页"
				}
			},
			"fnDrawCallback": function(){
				this.api().column(0).nodes().each(function(cell, i) {
					cell.innerHTML =  i + 1;
				});
			}
		});
	}
	
	function searchFromCMDB(ip){
		if(ip==""||ip == "undefined"){
			alert("ip地址为空!");
			return;
		}
		$("#ipContent").empty();
		$("#ipContent").append("查询中...");
		$('#detail').modal('toggle');
		$.ajax({
			url:"<%=basePath%>asset/searchCmdb",
			data:{"ip":ip},
			success:function(data){
				$("#ipContent").empty();
				if(data==""){
					$("#ipContent").append("无查询结果！");
				}else{
					$("#ipContent").append("CMDB信息：<br/>"+data);
				}
			},
			error:function(){
				$("#ipContent").empty();
				$("#ipContent").append("查询失败！");
			}
		});
	}
	
</script>

