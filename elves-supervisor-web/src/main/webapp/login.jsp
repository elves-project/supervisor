<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<title>elves-supervisor</title>
</head>
<body>
<!-- header:start -->
<header class="bg-primary" style="width:100%;height:50px;">
	<div class="container">
		<div class="row">
			<div class="col-md-20"><h3 style="margin-top: 10px;">elves-supervisor</h3></div>
		</div>
	</div>
</header>
<!-- header:end -->

<div class="jumbotron" style="height:700px">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-8" style="padding-left: 100px;">
			</div><div class="col-md-12">
			  <div class="panel panel-info" style="height: 327px; margin-right: 200px; margin-top: 95px; padding-top: 0px;">
				  <div class="panel-heading">
				    <h3 class="panel-title">欢迎登录</h3>
				  </div>
				  <div class="panel-body">
				    <form class="form-horizontal" role="form" method="post" id="defaultForm" style="margin-top: 50px;padding-left: 60px;" >
				      <div class="alert alert-success text-center" style="display: none;"></div>
				      
					  <div class="form-group text-center">
					    <label for="inputEmail3" class="col-md-6 control-label">邮箱:</label>
					    <div class="col-md-10">
					      <input type="text" class="form-control" id="inputEmail3" name="email"  placeholder="请输入邮箱">
					    </div>
					  </div>
					  <div class="form-group text-center">
					    <label for="passwordcss" class="col-md-6 control-label" >密码:</label>
					    <div class="col-md-10">
					      <input type="password" class="form-control" id="inputPassword3" name="password" placeholder="请输入密码"> 
					    </div>
					  </div>
					  <div class="form-group text-center">
					    <div class="col-md-22">
					      <span id="usernamecss"></span>
					      <!-- <button type="submit" class="btn btn-primary" style="width: 220px;" onclick="logins()">登录</button> -->
					      <button type="submit" class="btn btn-primary" style="width: 220px;" >登录</button>
					    </div>
					  </div>
					</form>
				  </div>
			  </div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

 $(document).ready(function() {
	
	 $('#defaultForm')
     .bootstrapValidator({
         message: 'This value is not valid',
         //live: 'submitted',
         feedbackIcons: {
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         },
         fields: {
        	 email: {
					validators : {
						notEmpty : {
							message : '邮箱不能为空'
						},
						emailAddress : {
							message : '邮箱格式不正确'
						}
					}
				},
             password: {
                 validators: {
                     notEmpty: {
                         message: '密码不能为空'
                     }
                 }
             }
         }
     }) 
     .on('success.form.bv', function(e) {
         // Prevent form submission
         e.preventDefault();

         // Get the form instance
         var $form = $(e.target);

         // Get the BootstrapValidator instance
         var bv = $form.data('bootstrapValidator');

         
         logins($form);
         // Use Ajax to submit form data
/*          $.post(_basePath+'/home/auth', $form.serialize(), function(result) {
        	 if(result == "suc"){ 
					top.location.href = _basePath + 'home/login';  
				}else{				
					layer.alert('用户名和密码不正确', {icon: 6});
				}
         }, 'text'); */
     });
});



 function logins(forms){	 
   
	  $.post(_basePath+'home/auth', forms.serialize(), function(result) {
     	 if(result == "suc"){ 
					top.location.href = _basePath + 'home';  
				}else{				
					layer.alert('用户名和密码不正确', {icon: 6});
				}
      }, 'text');
		
} 
 
 document.onkeydown = function (event) {
     var e = event || window.event || arguments.callee.caller.arguments[0];
     if (e && e.keyCode == 13) {
    	 
			$('#defaultForm').data('bootstrapValidator').validate();
			if (!$('#defaultForm').data('bootstrapValidator').isValid()) {
				return;
			}
    	 
    	 logins($('#defaultForm'));
     }
 };

</script>
</body>
</html>

