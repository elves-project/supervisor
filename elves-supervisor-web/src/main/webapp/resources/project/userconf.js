//全选功能
$("#antiAll").click(function(){
  $(".ids").each(function(){
	  this.checked=!this.checked;
  });
});

//删除信息
function delinfo(){
	//获取要删除的信息的id
	var ids = "";
	$(".ids").each(function(){
	  if(this.checked){
		  ids +=$(this).val()+",";
	  }
  	});
	
	if(ids!=""){
		var r=confirm("确认删除这些用户？");
		if (r==true){
			//ajax提交要删除的信息
			$.ajax({
			    url:'',						  //补充新增的url地址：需改动
			    type:'POST', 				  //GET
			    async:true,    			      //或false,是否异步
			    data:{
			        ids:ids
			    },
			    dataType:'json',
			    success:function(data){
			    	//页面中删除已经成功的信息
			    	$(".ids").each(function(){
					  if(this.checked){
						  $(this).remove();
					  }
				  	});
			    },
			    error:function(data){
			        console.log(data);
			        alert("出现错误，请联系系统负责人");
			    },
			});
	  	}
	}else{
		alert("删除需要选择用户！！！");
	}
	
}

//新增窗口打开
function adddialog(){
	$(".modal-title").empty().append("<strong>新增用户</strong>");
	$("#forminfo").attr("action",""); //补充新增的action指向：需改动
	dialogdisplay();
}


//编辑窗口打开
function editdialog(id){
	$("#did").val(id);
	$(".modal-title").empty().append("<strong>编辑用户</strong>");
	$("#forminfo").attr("action",""); //补充新增的action指向：需改动
	$.ajax({
	    url:'',						  //补充新增的url地址：需改动
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

//保存数据
function savedata(){
	var id = $("#did").val();
	$.ajax({
	    url:$('#forminfo').attr("action"),
	    type:'POST', 				  //GET
	    async:true,    				  //或false,是否异步
	    data:$('#forminfo').serialize(), 
	    dataType:'json',
	    success:function(data){
	    	//alert("修改成功，刷新后查看最新数据");
	    	//根据data进行编辑行内容
	    	var datastr = '<td style="text-align:center;"><input type="checkbox" class="ids" name="ids[]" value="1"/></td>'+
				    	'<td style="text-align:center;">2</td>'+
				    	'<td style="text-align:center;">100</td>'+
				    	'<td style="text-align:center;">admin</td>'+
				    	'<td style="text-align:center;">admin@gyyx.cn</td>'+
				    	'<td style="text-align:center;">2015-9-25 10:04</td>'+
				    	'<td style="text-align:center;">center_ap</td>'+
				    	'<td style="text-align:center;">2015-9-25 10:04</td>'+
				    	'<td style="text-align:center;">2015-9-25 10:04</td>'+
				    	'<td style="text-align:center;">192.168.1.1</td>'+
				    	'<td style="text-align:center;">3</td>'+
				    	'<td style="text-align:center;"><button type="button" class="btn btn-warning" onclick="editdialog(2);"><span class="glyphicon glyphicon-edit"></span></button></td>'
			if(id){
			//如果id不为空则为修改
				$("d_"+id).empty().append(datastr);
			}else{
			//如果id为空则为新增
				id = ""; //将获取数据data中的id赋值;
				$("tableinfo").append('<tr class="d_'+id+'">'+datastr+'</tr>');
			}
	    },
	    error:function(data){
	        console.log(data);
	        alert("出现错误，请联系系统负责人");
	    },
	});
}


//显示弹窗
function dialogdisplay(){
	$('#addeditmodal').modal('toggle');
}