	<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body>
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<div style="text-align: right;">
				<button type="button" class="btn btn-success"
					onclick="addApptoggle();">
					<span class="glyphicon glyphicon-plus"></span>
				</button>
				<button class="btn btn-danger" onclick="deleteApp();">
					<span class="glyphicon glyphicon-trash"></span>
				</button>
				<button class="btn btn-info" onclick="reBindData();">
                    手动刷新
				</button>
			</div>
		</div>
		<table class="table table-striped table-bordered" style="border:0px" id="apptableinfo" width="100%">
			<thead>
				<tr>
					<th style="text-align: center;"><input type="checkbox" id="checkAll" /></th>
					<th style="text-align: center;">序号</th>
					<th style="text-align: center;">APP名</th>
					<th style="text-align: center;">APP指令</th>
					<th style="text-align: center;">创建人</th>
				    <th style="text-align: center;">创建时间</th>
					<th style="text-align: center;">授权Agent数</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	
	<!-- 新增弹窗 -->
<div id="addAppModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" style="top:100px;">
  <div class="modal-dialog modal-sm" style="width:400px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">新增APP</h4>
      </div>
      <div class="modal-body">
      	<form id="addAppForm" action="" class="form-horizontal" role="form">
      		 <input type="hidden" id="did" name="id">
      		 <div class="form-group text-center">
      		 	<label class="col-md-6 control-label">APP指令:</label>
			    <div class="col-md-15">
			      <input type="text" class="form-control" id="centerid" name="centerid" placeholder="APP指令">
			    </div>
			  </div>
			  <div class="form-group text-center">
			    <label  class="col-md-6 control-label">APP名:</label>
			    <div class="col-md-15">
			      <input type="text" class="form-control" id="appname" name="appname" placeholder="APP名">
			    </div>
			  </div>
      	</form>
      </div>
	 <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="addApptoggle();"><span class="glyphicon glyphicon-remove"></span></button>
        <button type="button" class="btn btn-primary" onclick="savaAppdata();"><span class="glyphicon glyphicon-floppy-disk"></span></button>
      </div>
    </div>
  </div>
</div>

<!-- infodialog:start versionstate-->
<div id="versionstate" class="modal fade bs-example-modal-lg infodialog" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="top:100px;">
	<div class="modal-dialog modal-lg">
  		<div class="modal-content">
     		<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">App版本操作</h4>
			</div>
			<div class="modal-body">
	      		<div class="panel panel-default">
	      			<input type="hidden" id="currentVersionId" value="">
	      			<table id="tableVersion" class="table table-striped table-bordered" style="border:0px"> 
	      				<thead>
						    <tr>
						     <th style="text-align:center;">操作人</th>
						     <th style="text-align:center;">上传时间</th>
						     <th style="text-align:center;">版本号</th>
						     <th style="text-align:center;">线上版本</th>
						    </tr>
					   </thead>
					   <tbody>
					   </tbody>
			  		</table>
				</div>
	      	</div>
	 	</div>
  	</div>
</div>

<div id="editAppModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" style="top:150px;">
  <div class="modal-dialog modal-sm" style="width:400px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">APP版本上传</h4>
      </div>
      <div class="modal-body">
      	<form id="editAppforminfo" action="" class="form-horizontal" role="form">
      		 <input type="hidden" id="appidUp" name="appidUp">
      		 <div class="form-group text-center">
      		 	<label class="col-md-6 control-label">APP指令:</label>
			    <div class="col-md-15">
			      <input type="text" class="form-control" id="uploadtrun" name="centerid"  value="APP指令" readonly="readonly">
			    </div>
			  </div>
			  <div class="form-group text-center">
			    <label  class="col-md-6 control-label">APP名称:</label>
			    <div class="col-md-15">
			      <input type="text" class="form-control" id="uploadappname" name="username" value="APP名称" readonly="readonly">
			    </div>
			  </div>
			  <div class="form-group text-center">
      		 	<label  class="col-md-6 control-label">APP包上传:</label>
			    <div class="col-md-15">
			      <input type="file" id="appFile" name="appFile">
			    </div>
			  </div>
      	</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span></button>
        <button type="button" class="btn btn-primary" onclick="uploadFileForm();"><span class="glyphicon glyphicon-floppy-disk"></span></button>
      </div>
    </div>
  </div>
</div>


<!-- 获取agent列表  -->
<div  id="agentModeWindow" class="modal fade  editdialog" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">授权机器</h4>
			</div>
			<div class="modal-body" style="height:550px;">
				<div class="panel panel-default" style="height:520px;">
			      <div class="panel-heading">
			      		<form class="form-inline" role="form" id="agentInfoForm">
							  <div class="form-group" style="margin-left: 20px;">
							    <input type="text" class="form-control"  placeholder="请输入IP" id="agentIp">
							  </div>
							  <div class="form-group" style="margin-left: 20px;">
							    <input type="text" class="form-control"  placeholder="请输入标识" id="agentAssetId">
							  </div>
							  <button type="button" class="btn btn-info" onclick="agentSearch();" style="margin-left: 20px;">
							  	<span class="glyphicon glyphicon-search"></span>
							  </button>
						</form>
			       </div>
			       <div id="tableDiv" style="min-height:400px;overflow-y:auto;max-height:460px;">
						<input type="hidden" value="" id="appId"/>
						<table class="table table-striped table-bordered" style="border:0px" id="agentTable">
							<thead>
								<tr>
									<th style="text-align:center;">
										<input type="checkbox" id="ipsCheck"/>
									</th>
									<th style="text-align:center;">IP</th>
							     	<th style="text-align:center;">标识</th>
									<th style="text-align: center;">心跳时间</th>
								</tr>
						   </thead>
						</table>
					</div>
      			</div>
    		</div>
    		<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">
					<span class="glyphicon glyphicon-remove"></span>
				</button>
				<button type="button" class="btn btn-primary" onclick="bindAppAgent();">
					<span class="glyphicon glyphicon-floppy-disk"></span>
				</button>
			</div>
    	</div>
  </div>
</div>


<!-- 获取agent列表  -->
<div id="appAgentDiv" class="modal fade  editdialog" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">取消Agent授权</h4>
			</div>
			<div class="modal-body" style="height:550px;">
				<div class="panel panel-default" style="height:520px;">
			      <div class="panel-heading">
			      		<form class="form-inline" role="form" id="agentInfoForm">
							  <div class="form-group" style="margin-left: 20px;">
							    <input type="text" class="form-control"  placeholder="请输入IP" id="agentIp2">
							  </div>
							  <div class="form-group" style="margin-left: 20px;">
							    <input type="text" class="form-control"  placeholder="请输入标识" id="agentAssetId2">
							  </div>
							  <button type="button" class="btn btn-info" onclick="loadAppAgent();" style="margin-left: 20px;">
									<span class="glyphicon glyphicon-search"></span>
							  </button>
						</form>
			       </div>
			       <div id="appAgentTableDiv" style="min-height:400px;overflow-y:auto;max-height:460px;">
						<input type="hidden" value="" id="appId2"/>
						<table class="table table-striped table-bordered" style="border:0px" id="appAgentTable">
							<thead>
								<tr>
									<th style="text-align:center;">
										<input type="checkbox" id="agentCheck"/>
									</th>
									<th style="text-align:center;">IP</th>
							     	<th style="text-align:center;">标识</th>
								</tr>
						   </thead>
						</table>
					</div>
      			</div>
    		</div>
    		<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">
					<span class="glyphicon glyphicon-remove"></span>
				</button>
				<button type="button" class="btn btn-primary" onclick="unBindAppAgent();">
					<span class="glyphicon glyphicon-floppy-disk"></span>
				</button>
			</div>
    	</div>
  </div>
</div>
	
<script type="text/javascript">
	$(function() {
		$('#agentModeWindow').on('hide.bs.modal', function () {
			$("#agentTable").DataTable().destroy();
		});
		
		$('#versionstate').on('hide.bs.modal', function () {
			$("#tableVersion").DataTable().destroy();
		});
		
		init();
		
		$('#apptableinfo').dataTable({
			"bProcessing":false,
			"bScrollInfinite" : true, //是否启动初始化滚动条
			"processing" : true,
			"bProcessing": true,
			"serverSide" : true,
			"paging" : false,
			"lengthChange" : true,
			"searching" : true,
			"ordering" : false,
			"bDeferRender":true,
			"sProcessing" : true,
			"dom" :"trip",
			"destroy": true,
			"ajax" : {
				"url" : _basePath + '/app/list',
				"type" : "POST"

			},
			"autoWidth" : false,
			"serverSide" : true,
			"bFilter" : false,
			"bRetrieve" : true,
			"sPaginationType" : "full_numbers",
			"bPaginate" : true,
			"bInfo" : false,
			"columns" : [
					{
						"sClass" : "text-center",
						"orderable" : false,
						"data" : "appId",
						"defaultContent": "",
						"render" : function(data, type,
								row, meta) {
							return "<input type='checkbox' name='checkList' class='checkList' value='"+data+"'/>";
						}
					},
					{
						"sClass" : "text-center",
						"data" : null,
						"targets" : 0,
						"render" : function(data, type,
								row, meta) {
							// 显示行号  
							var startIndex = meta.settings._iDisplayStart;
							return startIndex + meta.row
									+ 1;
						}
					},
					{
						"sClass" : "text-center",
						"data" : "appName",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "instruct",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "founder",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "createTime",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "agentCount",
						"defaultContent": "",
						"render" : function(data, type,row, meta) {			
							return "<a  href='#' style='cursor:pointer;color:red;' onclick='showAppAgentDiv("+row.appId+")'>"+data+"台</a>";
						}
					},
					{
						"sClass" : "text-center",
						"data" : "appId",
						"defaultContent": "",
						"render" : function(data, type,row, meta) {			
								return "<button class='btn btn-warning' onclick='updateAppCenter("+data+")'><span class='glyphicon glyphicon-edit'></span></button> "+
										   "<button class='btn btn-info'    onclick='versionstuta("+data+","+row.versionId+");'><span class='glyphicon glyphicon-list'></span></button> "+
										    "<button class='btn btn-primary' onclick='agentModeToggle("+data+");'><span class='glyphicon glyphicon-stats'></span></button>";
						}
					}]
		});

		$("#checkAll").on("click",function() {
			if ($(this).prop("checked") === true) {
				$("input[name='checkList']").prop("checked", $(this).prop("checked"));
				$('#example tbody tr').addClass('selected');
			} else {
				$("input[name='checkList']").prop("checked", false);
				$('#example tbody tr').removeClass('selected');
			}
		});
	});
	
	function versionstuta(appId,currentVersionId){
		$("#currentVersionId").val(currentVersionId);
		$('#tableVersion').dataTable({
			"bProcessing":false,
			"bScrollInfinite" : true, //是否启动初始化滚动条
			"processing" : true,
			"bProcessing": true,
			"serverSide" : true,
			"paging" : false,
			"lengthChange" : true,
			"searching" : true,
			"ordering" : false,
			"bDeferRender":true,
			"sProcessing" : true,
			"dom" :"trip",
			"ajax" : {
				"url" : _basePath + '/app/versionList',
				"type" : "POST",
				"data" : function(d) {
					d.appId = appId
				}
			},
			"autoWidth" : false,
			"serverSide" : true,
			"bFilter" : false,
			"bRetrieve" : true,
			"sPaginationType" : "full_numbers",
			"bPaginate" : true,
			"bInfo" : false,
			"columns" : [
					{
						"sClass" : "text-center",
						"data" : "operator"
					},
					{
						"sClass" : "text-center",
						"data" : "createTime",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "version"
					},
					{
						"sClass" : "text-center",
						"data" : "id",
						"render" : function(data, type,row, meta) {				
							if(data==$("#currentVersionId").val()){
								 return " <input type='radio' checked='checked' name='nowversion' value='"+appId+"'>";
							}else{
								 return " <input type='radio' id='appIdVar' name='nowversion' onclick='startAppVersion("+row.id+");' value='"+appId+"'>";
							}      

						}
					}]
				});
        $('#versionstate').modal('toggle');
	}
	
	
	function uploadFileForm(){
		var  trun=$('#uploadtrun').val();
		var  appname=$('#uploadappname').val();
		var app_Id=$('#appidUp').val();
		
		var file = document.getElementById("appFile").value;//获取文件路径
		var sp = file.lastIndexOf(".");//获取文件路径中.的位置  
	    var sufix = file.substring(sp+1);//获取文件后缀
	   	var filepath = file.lastIndexOf("\\");
	    var fileName=file.substring(filepath+1,sp);//获取文件名
	    
	    var appfilename=fileName.split("_");
	    
	    if(file==null || file==''){
	    	layer.alert('请选择上传的文件!', {
				icon : 6
			}); 		    	
	    }else if(sufix==""||sufix!="zip"){
	    	layer.alert('请上传以zip后缀结尾的应用包!', {
				icon : 6
			}); 	
	    }else if(fileName.indexOf("_") < 0 ){
	    	layer.alert('上传文件名格式错误，正确格式如：cpuCenters_3.0.zip', {
				icon : 6
			}); 	
		
	    }else if(appfilename[0]!=trun){
			layer.alert('上传文件APP名前缀必须和指令一样!', {
				icon : 6
			}); 		    	
	    }else{
	    	$.post(_basePath + 'app/verexist', {
               	appid:app_Id,
               	appversion:appfilename[1]
			}, function(result) {
				if (result == "exist") {//app版本是否存在
											
					layer.confirm('已经存在该版本？', {
			    		  btn: ['覆盖','取消'] //按钮
			    		}, function(){//更新app版本
			    			layer.closeAll('dialog');
			    			publicMeth(app_Id,appfilename[1]);
			    			
			    		}, function(){
	                          
			    		});								
					
				} else {//增加app版本
					publicMeth(app_Id,appfilename[1]);
				}
			}, 'text');  
	    }
	}
	
	//上传文件
	function publicMeth(app_id,appversio){
		$.ajaxFileUpload({
               url: _basePath + 'app/upload', 
               type: 'post',
               data : {
               	appid:app_id,
               	appversion:appversio
               },
               secureuri: false, //一般设置为false
               fileElementId: 'appFile', // 上传文件的id、name属性名
               dataType: 'JSON', //返回值类型，一般设置为json、application/json  这里要用大写  不然会取不到返回的数据
               success: function(data){  
               	 data= jQuery.parseJSON(jQuery(data).text());
                    if(data.flag == "success"){
                   	 $('#editAppforminfo')[0].reset();
            			$('#editAppModal').modal('toggle');
                   	 layer.alert('上传成功!', {
        					icon : 6
        				}); 	
                    }else if(data.flag == "repeat"){
                   	 $('#editAppforminfo')[0].reset();
             			$('#editAppModal').modal('toggle');
                   	 layer.alert('上传成功!', {
        					icon : 6
        				}); 	
                    }else{
                   	 layer.alert("上传失败!", {
         					icon : 6
         				}); 	
                    }
               }
           }); 
	}
	
	function startAppVersion(id){
		var appId=$('#appIdVar').val();
		layer.confirm('是否启用该版本？', {
    		  btn: ['是','否'] //按钮
    		}, function(){//更新app版本
    			layer.closeAll('dialog');
    			$.post(_basePath + '/app/startAppVer', {
    				appid:appId,
    				id : id
    			}, function(result) {
    				if (result == "suc") {
    					layer.alert('启用成功!', {
    						icon : 6
    					});
    					$("#currentVersionId").val(id);
    					$('#apptableinfo').DataTable().ajax.reload();
    				} else {
    					layer.alert('启用失败!', {
    						icon : 6
    					});
    				}
    				$('#tableVersion').DataTable().ajax.reload();
    			}, 'text');  
    		}, function(){
    		});							
	}
	
	function savaAppdata(){
		$('#addAppForm').data('bootstrapValidator').validate();
		if (!$('#addAppForm').data('bootstrapValidator').isValid()) {
			return;
		}
		$.post(_basePath + '/app/add', $("#addAppForm")
				.serialize(), function(result) {
			if (result == "suc") {
				$('#apptableinfo').DataTable().ajax.reload();
				$('#addAppForm')[0].reset();
				addApptoggle();
				layer.alert('增加成功!', {
					icon : 6
				});
			} else {
				layer.alert('增加失败', {
					icon : 6
				});
			}
		}, 'text');
		
	}
	
	//模块删除
	function deleteApp(){
		var ids=$("input:checkbox[name='checkList']:checked").map(function(index,elem){
			return $(elem).val();
		}).get().join(',');
		
		if(ids.length<=0){
			layer.alert("请至少选择一行！", {
				icon : 6
			});
			return;
		}else{
		 	$.post(_basePath + '/app/delete', {
			id : ids
		}, function(result) {
			if (result == "suc") {
				
				$('#apptableinfo').DataTable().ajax.reload();
				
				layer.alert('删除成功!', {
					icon : 6
				});
			} else {
				layer.alert('删除失败', {
					icon : 6
				});
			}
		}, 'text');  
		 	
		}
	}
		
	//显示弹窗
	function addApptoggle() {
		//$(".modal-title").empty().append("<strong>新增模块</strong>");
		$('#addAppForm')[0].reset();
		$('#addAppModal').modal('toggle');
	}
	
	
	function updateAppCenter(row) {
		$('#appidUp').val(row);
		
		$.post(_basePath + '/app/appbyid',{id:row}, function(result) {
			 data=eval('('+result+')');
				$('#uploadtrun').val(data.instruct);
				$('#uploadappname').val(data.appname); 
		}, 'text');
		
		$('#editAppforminfo')[0].reset();
		$('#editAppModal').modal('toggle');
	}
	
	function agentModeToggle(id) {
		agentListinit(id);
		$('#agentModeWindow').modal('toggle');
	}
	
	
	function showAppAgentDiv(appId){
		loadAppAgent(appId);
		$('#appAgentDiv').modal('toggle');
	}
	
	function loadAppAgent(appId){
		if(appId!=""&&appId!=undefined){
			$("#appId2").val(appId);
		}
		if($("#appId2").val()==''){
			return;
		}
		$('#appAgentTable').dataTable({
			"bProcessing":false,
			"bScrollInfinite" : true, //是否启动初始化滚动条
			"processing" : true,
			"bProcessing": true,
			"serverSide" : true,
			"paging" : false,
			"lengthChange" : true,
			"searching" : true,
			"ordering" : false,
			"bDeferRender":true,
			"sProcessing" : true,
			"dom" :"trip",
			"destroy": true,
			 "ajax" : {
		            "url" : '<%=basePath%>app/searchAppAgent',
		            "type" : "POST",
		            "data":function(d){
		            	d.ip=$("#agentIp2").val(),
		                d.assetId=$("#agentAssetId2").val(),
		                d.appId=$("#appId2").val()
		            }
		        },
			"autoWidth" : false,
			"serverSide" : true,
			"bFilter" : false,
			"bRetrieve" : true,
			"sPaginationType" : "full_numbers",
			"bPaginate" : true,
			"bInfo" : false,
			"columns" : [
				  {
					"sClass" : "text-center",
					"orderable" : false,
					"data" : "id",
					"render" : function(data, type,row, meta) {
						return "<input type='checkbox'  value='"+row.id+"'name='appAgentBox'/>";
					}
				  },
			      {
	                  "sClass" : "text-center",
	                  "data" : "ip",
	                  "defaultContent": ""
	              },{
	                  "sClass" : "text-center",
	                  "data" : "assetId",
	                  "defaultContent": ""
	         	  }],
			"language" : {
				"sProcessing" :"正在加载中......",
				"sEmptyTable" : "表中数据为空",  
			    "sLoadingRecords" : "载入中..."
			}
		});
		$("#agentCheck").on("click",function() {
			if ($(this).prop("checked")) {
				$("input[name='appAgentBox']").prop("checked", $(this).prop("checked"));
			} else {
				$("input[name='appAgentBox']").prop("checked", false);
			}
		});
		$('#appAgentTable').DataTable().ajax.reload();
	}
	
	function agentListinit(id){
		$("#appId").val(id);
		$('#agentTable').dataTable({
			"bProcessing":false,
			"bScrollInfinite" : true, //是否启动初始化滚动条
			"processing" : true,
			"bProcessing": true,
			"serverSide" : true,
			"paging" : false,
			"lengthChange" : true,
			"searching" : true,
			"ordering" : false,
			"bDeferRender":true,
			"sProcessing" : true,
			"dom" :"trip",
			"destroy": true,
			 "ajax" : {
		            "url" : '<%=basePath%>app/searchAgent',
		            "type" : "POST",
		            "data":function(d){
		            	d.ip=$("#agentIp").val(),
		                d.assetId=$("#agentAssetId").val(),
		                d.appId=id
		            }
		        },
			"autoWidth" : false,
			"serverSide" : true,
			"bFilter" : false,
			"bRetrieve" : true,
			"sPaginationType" : "full_numbers",
			"bPaginate" : true,
			"bInfo" : false,
			"columns" : [
				  {
					"sClass" : "text-center",
					"orderable" : false,
					"data" : "ip",
					"render" : function(data, type,row, meta) {
						return "<input type='checkbox'  value='"+row.ip+"_"+row.asset+"'name='ipBox'/>";
					}
				  },
			      {
	                  "sClass" : "text-center",
	                  "data" : "ip",
	                  "defaultContent": ""
	              },{
	                  "sClass" : "text-center",
	                  "data" : "asset",
	                  "defaultContent": ""
	         	  },{
	                  "sClass" : "text-center",
	                  "data" : "last_hb_time",
	                  "defaultContent": ""
	         	  }],
			"language" : {
				"sProcessing" :"正在加载中......",
				"sEmptyTable" : "表中数据为空",  
			    "sLoadingRecords" : "载入中..."
			}
		});
		$("#ipsCheck").on("click",function() {
			if ($(this).prop("checked")) {
				$("input[name='ipBox']").prop("checked", $(this).prop("checked"));
			} else {
				$("input[name='ipBox']").prop("checked", false);
			}
		});
		reloadDataTables('agentTable');
	}
	
	//初始化验证表单
	function init() {
		$('#addAppForm').bootstrapValidator({
			message : 'This value is not valid',
			//live: 'submitted',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				centerid : {
					validators : {   
						notEmpty : {
							message : '指令不能为空'
						},
						remote : {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
							url : _basePath + '/app/instruct',//验证指令是否存在
							message : '指令已存在，请重新录入',//提示消息
							delay : 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
							type : 'POST',//请求方式
							data : function(validator) {
								return {
									instruct : $('#centerid').val(),
								};
							}

						}
					}
				},
				appname : {
					validators : {
						notEmpty : {
							message : '模块名不能为空'
						}

					}
				},
				processorIp : {
					validators : {
						regexp: {
	                            regexp: /^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])$/,
	                            message: 'ip格式不正确'
	                        }   

					}
				},
				processorPort : {
					validators : {
	                    numeric: {
	                    	message: '端口只能是数字'
	                    	} 

					}
				}
			}
		});
	}
		
	
	function agentSearch(){			
		//var table=$('#agentTable').DataTable({ajax:_basePath + 'app/agentList'}); 
		$('#agentTable').DataTable().ajax.reload();
	}
	
    
    function destroy(){
    	$("#agentTable").DataTable().destroy();
    }
    
    function destroyes(){
    	$("#tableVersion").DataTable().destroy();
    }
    
    
    function bindAppAgent(){
    	var appId=$("#appId").val();
    	if(appId==""){
    		alert("保存失败");
    		return;
    	}
    	var appAgentList="";
    	$("input[name='ipBox']").each(function(){
    		if($(this).prop("checked")){
    			appAgentList+=$(this).val();
    			appAgentList+=",";
    		}
    	});
    	if(appAgentList==""){
    		alert("请选择要授权的Agent!");
    		return;
    	}else{
    		appAgentList=appAgentList.substring(0,appAgentList.length-1);
    	}
    	$.ajax({
    		url:"<%=basePath%>app/bind",
    		type:'POST',
    		data:{'appId':appId,"appAgentList":appAgentList},
    		success:function(data){
    			if(data=="success"){
    				alert("操作成功！");
    				agentListinit();
    				$('#apptableinfo').DataTable().ajax.reload();
    			}else{
    				alert("操作失败！");
    			}
    		},
    		error:function(){
    			alert("操作失败！");
    		}
    	 });
    }
    
    function unBindAppAgent(){
    	var ids="";
    	$("input[name='appAgentBox']").each(function(){
    		if($(this).prop("checked")){
    			ids+=$(this).val();
    			ids+=",";
    		}
    	});
    	if(ids==""){
    		alert("请选择取消限制的Agent!");
    		return;
    	}else{
    		ids=ids.substring(0,ids.length-1);
    	}
    	$.ajax({
    		url:"<%=basePath%>app/unBind",
    		type:'POST',
    		data:{'ids':ids},
    		success:function(data){
    			if(data=="success"){
    				alert("操作成功！");
    				loadAppAgent();
    				$('#apptableinfo').DataTable().ajax.reload();
    			}else{
    				alert("操作失败！");
    			}
    		},
    		error:function(){
    			alert("操作失败！");
    		}
    	 });
    }

	function reBindData(){
        layer.confirm('确定刷新APP绑定的Agent数据么？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            var index = layer.load(1, {shade: false});
            $.ajax({
                url:"<%=basePath%>app/flushData",
                type:'POST',
                success:function(data){
                    layer.close(index);
                    if(data=="success"){
                        layer.alert('操作成功！', {
                            icon : 6
                        });
                        loadAppAgent();
                        $('#apptableinfo').DataTable().ajax.reload();
                    }else{
                        layer.alert('操作失败：请联系管理员！', {
                            icon : 6
                        });
                    }
                },
                error:function(){
                    layer.close(index);
                    layer.alert('操作失败：请联系管理员！', {
                        icon : 6
                    });
                }
            });
        });
	}
</script>

</body>
</html>