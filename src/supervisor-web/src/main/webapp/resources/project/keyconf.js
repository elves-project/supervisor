//全选功能
$("#keysAll").click(function(){
  $(".keyids").each(function(){
	  this.checked=!this.checked;
  });
});

function test(){
	selectall();
}
var source = [{"title": "Animalia", "expanded": true, "folder": true, "children": [
		{"title": "Chordate", "folder": true, "children": [
			{"title": "Mammal", "children": [
				{"title": "Primate", "children": [
					{"title": "Primate", "children": [
					]},
					{"title": "Carnivora", "children": [
					]}
				]},
				{"title": "Carnivora", "children": [
					{"title": "Felidae", "lazy": true}
				]}
			]}
		]},
		{"title": "Arthropoda", "expanded": true, "folder": true, "children": [
			{"title": "Insect", "children": [
				{"title": "Diptera", "lazy": true}
			]}
		]}
	]}
];
var glyph_opts = {
	map: {
		doc: "glyphicon glyphicon-file",
		docOpen: "glyphicon glyphicon-file",
		checkbox: "glyphicon glyphicon-unchecked",
		checkboxSelected: "glyphicon glyphicon-check",
		checkboxUnknown: "glyphicon glyphicon-share",
		dragHelper: "glyphicon glyphicon-play",
		dropMarker: "glyphicon glyphicon-arrow-right",
		error: "glyphicon glyphicon-warning-sign",
		expanderClosed: "glyphicon glyphicon-menu-right",
		expanderLazy: "glyphicon glyphicon-menu-right",  // glyphicon-plus-sign
		expanderOpen: "glyphicon glyphicon-menu-down",  // glyphicon-collapse-down
		folder: "glyphicon glyphicon-folder-close",
		folderOpen: "glyphicon glyphicon-folder-open",
		loading: "glyphicon glyphicon-refresh glyphicon-spin"
	}
};
$(function(){
	// Initialize Fancytree
	$("#treetableinfo").fancytree({
		extensions: ["dnd", "edit", "glyph", "table"],
		checkbox: false,
		glyph: glyph_opts,
		source: source,
		table: {
			//checkboxColumnIdx: 1,
			nodeColumnIdx: 0
		},
		renderColumns: function(event, data) {
			var node = data.node,
				$tdList = $(node.tr).find(">td");
			//$tdList.eq(0).text(node.getIndexHier());
		}
	});
	$("#treetableconfirm").fancytree({
		extensions: ["dnd", "edit", "glyph", "table"],
		checkbox: false,
		glyph: glyph_opts,
		source: source,
		table: {
			//checkboxColumnIdx: 1,
			nodeColumnIdx: 0
		},
		renderColumns: function(event, data) {
			var node = data.node,
				$tdList = $(node.tr).find(">td");
			//$tdList.eq(0).text(node.getIndexHier());
		}
	});
});




//增加单个节点
function addsinglenode(){
	var rootNode = $("#treetableinfo").fancytree("getRootNode");
	var childNode = rootNode.addChildren({
	  title: "Programatically addded nodes",
	  tooltip: "This folder and all child nodes were added programmatically.",
	  folder: true
	});
	childNode.addChildren({
	  title: "Document using a custom icon",
	});
}

//展开所有节点
function treetoogle(){
	$("#treetableinfo").fancytree("getRootNode").visit(function(node){
        node.toggleExpanded();
      });

}

//树结构排序
function sorttree(){
	var node = $("#treetableinfo").fancytree("getRootNode");
	node.sortChildren(null, true);
}






//显示信息窗口
function displayinfodialog(id){
	//通过ajax获取数据：需改动
	
	$(".infodialog").modal('toggle');
}

//显示编辑窗口
function displayeditdialog(id){
	//通过ajax获取数据：需改动
	$(".editdialog").modal('toggle');
}

//删除表格信息
function delinfo(id){
	var r=confirm("确认删除这些用户？");
	if (r==true){
		//ajax删除，确认后移除表格中的内容
		$(".d_"+id).remove();
	}
}

function addconfirm(){
	var keyname = $('#addkeyname').val();
	if(keyname){
		$.ajax({
		    url:$("#addform").attr("action"),			//补充新增的url地址：需改动
		    type:'POST', 				  				//GET或者POST
		    async:true,    			  					//或false,是否异步
		    data:{
		    	keyname:keyname
		    },
		    dataType:'json',
		    success:function(data){
		    	//填充数据到表格内:需改动
		    	alert("数据新增成功!!!");
		    	var datastr = '<tr class="d_2">'+
						    	'<td style="text-align:center;">2</td>'+
						    	'<td style="text-align:center;">管理员</td>'+
						    	'<td style="text-align:center;">A0985CC1D6E48F0D</td>'+
						    	'<td style="text-align:center;">AE17BEEE5E8D4D6A</td>'+
						    	'<td style="text-align:center;">2016-10-01 11:22:48</td>'+
						    	'<td style="text-align:center;">'+
						    	'<button class="btn btn-info"   ><span class="glyphicon glyphicon-list-alt"></span></button>'+
						    	'<button class="btn btn-warning"><span class="glyphicon glyphicon-edit"></span></button>'+
						    	'<button class="btn btn-danger" ><span class="glyphicon glyphicon-remove"></span></button>'+
						    	'</td>'+
						    	'</tr>';
			     $("#tableinfo").append(datastr);
		    	//displayadddialog();      
		    }
		});
		displayadddialog();
	}else{
		alert("名称不能为空");
	}
}

$(".poinfo").click(function(){
	  $(".poinfo").popover('hide');
	  $(this).popover('toggle');
});