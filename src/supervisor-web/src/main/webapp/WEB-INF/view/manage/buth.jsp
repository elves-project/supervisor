<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="panel panel-default">
	<!-- Default panel contents -->
<div class="panel-heading">
	<form class="form-inline" role="form" id="searchform">
		<div class="form-group">
			<label class="sr-only" for="exampleInputPassword2">用户名</label> <input
				type="text" class="form-control" name="username" id="shechusername"
				placeholder="请输入用户名">
		</div>
		<div class="form-group" style="margin-left:10px;">
			<div class="input-group">
				<input class="form-control" type="text" name="email" id="sharchemail"
					placeholder="请输入邮箱名">
			</div>
		</div>
		<button type="button" class="btn btn-info" onclick="searchLoad();"style="margin-left:10px;">
			<span class="glyphicon glyphicon-search"></span>
		</button>
		<div class="form-group" style="float: right">
			<button type="button" class="btn btn-success"
				onclick="adddialog(1,'');">
				<span class="glyphicon glyphicon-plus"></span>
			</button>
			<button type="button" class="btn btn-danger" onclick="deleteUser();">
				<span class="glyphicon glyphicon-trash"></span>
			</button>
		</div>
	</form>
</div>
<table
	class="table table-striped table-bordered" style="border:0px"
	id="tableinfo" width="100%">
	<thead>
		<tr>
			<th style="text-align: center;"><input type="checkbox"
				id="checkAll"/></th>
			<th style="text-align: center;">序号</th>
			<!-- 					<th style="text-align: center;">用户ID</th> -->
			<th style="text-align: center;">用户名</th>
			<th style="text-align: center;">邮箱</th>
			<th style="text-align: center;">创建人</th>
			<th style="text-align: center;">创建时间</th>
			<th style="text-align: center;">更新时间</th>
			<th style="text-align: center;">上次登录时间</th>
			<th style="text-align: center;">上次登录IP</th>
			<th style="text-align: center;">登录次数</th>
			<th style="text-align: center;">操作</th>
		</tr>
	</thead>
      
	<tbody>
	</tbody>
</table>


<div id="manageaddeditmodal" class="modal fade bs-example-modal-sm"
	tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel"
	aria-hidden="true" style="top:120px;">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body">
				<form id="buthforminfo" action="" class="form-horizontal"
					role="form">
					<input type="hidden" id="did" name="id">
					<input type="hidden" id="param" name="param">
					<div class="form-group text-center">
						<label class="col-md-6 control-label">用户名:</label>
						<div class="col-md-15">
							<input type="text" class="form-control" id="duname"
								name="username" placeholder="请输入用户名" value="">
						</div>
					</div>
					<div class="form-group text-center">
						<label class="col-md-6 control-label">邮箱:</label>
						<div class="col-md-15">
							<input type="text" class="form-control" id="demail"
								name="email" placeholder="请输入邮箱">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal"
					onclick="authdialogdisplay()">
					<span class="glyphicon glyphicon-remove"></span>
				</button>
				<button type="button" class="btn btn-primary" onclick="addUser();" id="editOnclick"> 
					<span class="glyphicon glyphicon-floppy-disk"></span>
				</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	var table;
	var editFlag = false;
	$(function(){init();
		$('#tableinfo').dataTable({
			"processing" : true,
			"serverSide" : true,
			"scrollX": false,
	         "paging": true,  
	        "lengthChange": true,  
	        "searching": true,  
	        "ordering": false, 
	        "dom" :"tip",
			//"dom":"t"  +"<'row'<'col-xs-12'i><'col-xs-12'p>>",
			"ajax" : {
				"url" : _basePath + '/manage/list',
				"type" : "POST",
			    "data": function ( d ) {
			        d.username = $('#shechusername').val(),
			        d.email=$("#sharchemail").val()
			    }	
					
			},
			"autoWidth": false,  
			"aaSorting" : [[2, "asc"]],
			"pageLength" : 10,
			"bFilter" : false,
			"bRetrieve" : true,
			"sPaginationType" : "full_numbers",
			"bPaginate" : true,
			"bProcessing": true,
			"scrollY": "540px",
			"bInfo" : true,
			"Info": true,
			"columns" : [
					{
						"sClass" : "text-center",
						"orderable":false,
						"data" : "id",
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
							return startIndex
									+ meta.row + 1;
						}
					},
					{
						"sClass" : "text-center",
						"data" : "username"
					},
					{
						"sClass" : "text-center",
						"data" : "email"
					},
					{
						"sClass" : "text-center",
						"data" : "founder"
					},
					{
						"sClass" : "text-center",
						"data" : "createTime"
					},
					{
						"sClass" : "text-center",
						"data" : "updateTime",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "lastLoginTime",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "lastLoginIp",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "loginTimes",
						"defaultContent": ""
					},
					{
						"sClass" : "text-center",
						"data" : "id",
						"render" : function(data, type,
								row, meta) {
							return "<button type='button' class='btn btn-warning' onclick='adddialog(2,"
									+ data
									+ ");'><span class='glyphicon glyphicon-edit'></span></button>";
						}
					} ],
			language : {
				"sLengthMenu": "显示 _MENU_ 记录",
                "sZeroRecords": "对不起，查询不到任何相关数据",
                "sEmptyTable": "未有相关数据",
                "sLoadingRecords": "正在加载数据-请等待...",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录。",
                "sInfoEmpty": "当前显示0到0条，共0条记录",
				"Info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
				"sProcessing" : '<i class="fa fa-coffee"></i> 正在加载数据...',
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "前一页",
					"sNext" : "后一页",
					"sLast" : "尾页"
				}
			}
		});

		 $("#checkAll").on("click", function () {
               if ($(this).prop("checked") === true) {
                   $("input[name='checkList']").prop("checked", $(this).prop("checked"));
                   $('#example tbody tr').addClass('selected');
               } else {
                   $("input[name='checkList']").prop("checked", false);
                   $('#example tbody tr').removeClass('selected');
               }
          }); 
	});
	
	function searchLoad(){
		$('#tableinfo').DataTable().ajax.reload();
	}
	
	//编辑窗口打开
	function adddialog(id, data) {
		
		$("#buthforminfo").data('bootstrapValidator').destroy();			
		init();
		
		if (id == 1) {
			//$("#did").val(id);
			$(".modal-title").empty().append("<strong>新增用户</strong>");
			$("#forminfo").attr("action", ""); //补充新增的action指向：需改动
			
			authdialogdisplay(); //补充完毕后把该行注释，把ajax中的注释打开
		} else {
			$(".modal-title").empty().append("<strong>修改用户</strong>");
			$("#forminfo").attr("action", ""); //补充新增的action指向：需改动
			$("#did").val(data);
			
			$.post(_basePath + '/manage/user',{id:data}, function(result) {
				 data=eval('('+result+')');
				 $("#duname").val(data.username);
				 $("#demail").val(data.email);
				 $("#param").val(data.email+","+data.username);
			}, 'text');
			
			authdialogdisplay(); //补充完毕后把该行注释，把ajax中的注释打开
		}
	}

	//显示弹窗
	function authdialogdisplay() {
		$('#buthforminfo')[0].reset();
		$('#manageaddeditmodal').modal('toggle');
	}

	//删除用户
	function deleteUser() {
		var ids=$("input:checkbox[name='checkList']:checked").map(function(index,elem){
			return $(elem).val();
		}).get().join(',');
		
		if(ids.length<=0){
			layer.alert("请至少选择一行！", {
				icon : 6
			});
			return;
		}else{
		 	$.post(_basePath + '/manage/delete', {
			id : ids
		}, function(result) {
			if (result == "suc") {
				pageLoad();
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
	
	function pageLoad(){
		$('#tableinfo').DataTable().ajax.reload();
	}

	//增加用户
	function addUser() {
		var edit = $("#did").val();
		if (edit == "" || edit==null) {
			$('#buthforminfo').data('bootstrapValidator').validate();
			if (!$('#buthforminfo').data('bootstrapValidator').isValid()) {
				return;
			}
			$.post(_basePath + '/manage/add', $("#buthforminfo")
					.serialize(), function(result) {
				if (result == "suc") {
					pageLoad();
					$('#buthforminfo')[0].reset();
					authdialogdisplay();
					layer.alert('增加成功!', {
						icon : 6
					});
				} else {
					layer.alert('增加失败', {
						icon : 6
					});
				}
			}, 'text');

		} else {
			 var username=$("#duname").val();
			 var email=$("#demail").val();
			 var param=$("#param").val();
			 var strs= new Array(); //定义一数组
			 strs=param.split(",");
			 if(strs[0]!=email || username==""){
			    $('#buthforminfo').data('bootstrapValidator').validate();
			    if (!$('#buthforminfo').data('bootstrapValidator').isValid()) {
				  return;
			    }
			 }

			$.post(_basePath + '/manage/update', $("#buthforminfo")
					.serialize(), function(result) {
				if (result == "suc") {
					$("#did").val('');
					$('#tableinfo').DataTable().ajax.reload();
					//$("#right").load(_basePath+"/manage/page");
					$('#buthforminfo')[0].reset();
					authdialogdisplay();
					layer.alert('更新成功!', {
						icon : 6
					});
				} else {
					layer.alert('更新失败', {
						icon : 6
					});
				}
			}, 'text');
		}
		$("#right").load();
	}

	//初始化验证表单
	function init() {
		$('#buthforminfo').bootstrapValidator({
			message : 'This value is not valid',
			//live: 'submitted',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				username : {
					validators : {
						notEmpty : {
							message : '用户名不能为空'
						}
					}
				},
				email : {
					validators : {
						notEmpty : {
							message : '邮箱不能为空'
						},
						emailAddress : {
							message : '邮箱格式不正确'
						},
						remote : {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
							url : _basePath + '/manage/valemail',//验证地址
							message : '邮箱已存在，请重新录入',//提示消息
							type : 'POST',//请求方式
							data : function(validator) {
								return {
									email : $('#demail').val(),
									id:$('#did').val()
								};
							}
						}

					}
				}
			}
		});
	}
</script>
