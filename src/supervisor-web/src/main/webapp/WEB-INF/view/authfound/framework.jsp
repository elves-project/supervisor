<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String userName=request.getSession().getAttribute("username").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">

<title>elves-supervisor</title>

</head>
<body>
<script>
	function open_url(site,obj){
		  $(".list-group-item").removeClass("list-group-item-info");
		  obj.setAttribute("class","list-group-item list-group-item-info colorStyle");
		  $("#right").load(_basePath+site);
	}
	
	function reloadDataTables(id){
		$("#"+id).DataTable().ajax.reload();
	}
</script>

<!-- header:start -->
<header class="bg-primary" style="width:100%;height:50px;">
	<div class="container">
		<div class="row">
			<div class="col-md-20"><h3 style="margin-top: 10px;"> elves-supervisor </h3></div>
			<div class="col-md-4 text-right">
		    	<div class="btn btn-info active" style="margin-top: 10px;">欢迎您:<%=userName%> | <a href="javascript:void(0);"  onclick="logout()">注销</a>|<a href="javascript:void(0);" onclick="editdialog(1)">修改密码</a></div>	    
		    </div>
		</div>
	</div>
</header>
<br>
<!-- header:end -->

<div class="container-fluid">
	<div class="row">
	<!-- nav:start -->
		<div class="list-group col-md-3" style="margin-left: 10px;">
		  <jsp:include page="west_layout.jsp" />
		</div>
	<!-- dialog:start -->
	
	
	<!-- main:start -->
	<div class="col-md-20" id="right" style="width:86%;">
		
	<!-- main:end -->
	</div>
</div>
	
	
<!-- 新增和修改共用该弹窗 -->
<div id="addeditmodal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" style="top:150px">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body">
      	<form id="forminfo" action=""+_basePath+"/home/updatePw" class="form-horizontal" role="form">
      		 <input type="hidden" id="did" name="id">
      		  <div class="form-group text-center">
      		 	<label  class="col-md-8 control-label">原密码:</label>
			    <div class="col-md-15">
			      <input type="password" class="form-control" id="rowpassword" name="rowpassword" placeholder="请输入原密码">
			    </div>
			  </div>
			  <div class="form-group text-center">
      		 	<label  class="col-md-8 control-label">新密码:</label>
			    <div class="col-md-15">
			      <input type="password" class="form-control" id="password" name="password" placeholder="请输入新密码">
			    </div>
			  </div>
			  <div class="form-group text-center">
      		 	<label  class="col-md-8 control-label">重复密码:</label>
			    <div class="col-md-15">
			      <input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="请输入密码" >
			    </div>
			  </div>
      	</form>
      </div>
      <div class="modal-footer">
        <input class="btn btn-default" type="submit" value="确认" onclick="submits()">
        <input class="btn btn-default" type="button" value="取消" onclick="dialogdisplay()">
      </div>
    </div>
  </div>
</div>
	</div>
</div>

<script type="text/javascript">
/* $.validator.setDefaults({
    submitHandler: function() {
      alert("提交事件!");
    }
}); */
$().ready(function() {
	// 在键盘按下并释放及提交后验证提交表单
	  $("#forminfo").validate({
		
	    rules: {
	      rowpassword: {
	    	  required:true,
	    	  remote: {
	    		  url: _basePath+'/home/passWordVal',
	    		  type: "post",
	    		  //DataType: "json",
	    		  data: {
	    			   rowpassword: function(){
	    		         return $("#rowpassword").val();
	    		      }
	    		  }  
	    	  }
	      },
	      password:{
		        required: true,
		        minlength: 6
		   },
		  confirm_password: {
		        required: true,
		        minlength: 6,
		        equalTo: "#password"
		   }
	    },  
	    messages:{
	      rowpassword:{
	    	  required:"请输入原密码",
	    	  remote:"密码不正确"
	       },
	      password:{
		        required: "请输入新密码",
		        minlength: "密码长度不能小于 6 个字符"
		   },
		   confirm_password: {
		        required: "请输入密码",
		        minlength: "密码长度不能小于 6个字符",
		        equalTo: "两次密码输入不一致"
		      },
	    },
	    submitHandler: function(form) {  //通过之后回调 
	    	
	        var param = $("#forminfo").serialize(); 
	        $.ajax({ 
	           url :_basePath+'/home/updatePw', 
	           type : "post", 
	           dataType : "text", 
	           data: param, 
	           success:function(data){	
	        	   if(data=="success"){
				       top.location.href = _basePath;  
	        	   }
	        } 
	     });
	        
	    },
        invalidHandler: function(form, validator) {  //不通过回调 
            return false; 
        } 
	    
	});
});

 function submits(){
	$("#forminfo").submit();  
	
} 

//注销
function logout(){
	
	 $.ajax({
		 url:_basePath+'/home/logout',
		 type:'post',
		 async:true,
		 dataType:'text',
		 success:function(data){
				if(data == "suc"){
					top.location.href = _basePath + 'home/view';                       
				}
	    }});
}


//编辑窗口打开
function editdialog(id){
	$("#did").val(id);
	$(".modal-title").empty().append("<strong>修改密码</strong>");
	$("#forminfo").attr("action",""); //补充新增的action指向：需改动
	$.ajax({
	    url:'',	//补充新增的url地址：需改动
	    type:'POST', 				  //GET
	    async:true,    			  //或false,是否异步
	    data:{
	        id:id
	    },
	    dataType:'json',
	    success:function(data){
	    	//填充数据到弹窗内:需改动
	    	$("#dcid").val();
	    	$("#duname").val();
	    	$("#demail").val();
	    	//dialogdisplay();        
	    }
	});
	dialogdisplay();	//补充完毕后把该行注释，把ajax中的注释打开
}

//显示弹窗
function dialogdisplay(){
	$("#rowpassword").val("");
	$("#password").val("");
	$("#confirm_password").val("");
	$('#addeditmodal').modal('toggle');
}


function right(){
	$("#right").load(_basePath+'/manage/page');
}
</script>
</body>
</html>
