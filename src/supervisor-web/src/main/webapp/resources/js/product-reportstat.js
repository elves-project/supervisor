// 设置年份
function setTime()
{
    var now  = new Date();
    var year = now.getFullYear();
    var date_str = "";
    while (year >= 2011){
        date_str += '<option value="'+year+'" >'+year+'年</option>';
        year--;
    }
    $('.vbox select[name=department]').html(date_str);
}
setTime();

// 设置echarts 配置
var option = {
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data:['月总故障率','月故障次数','月平均故障率'],
        selected: {
            '月故障次数' : false,
            '月平均故障率' : false
        }
    },
    toolbox: {
        show : true,
        orient: 'vertical',
        x: 'right',
        y: 'center',
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'月总故障率',
            type:'bar',
            show: false,
            itemStyle: {
                normal: {                   // 系列级个性化，横向渐变填充
                    borderRadius: 5,
                    label : {
                        show : true,
                        textStyle : {
                            fontSize : '10',
                            fontFamily : '微软雅黑',
                        }
                    }
                }
            },
            data:""
        },
        {
            name:'月故障次数',
            type:'bar',
            itemStyle: {
                normal: {                   // 系列级个性化，横向渐变填充
                    borderRadius: 5,
                    label : {
                        show : true,
                        textStyle : {
                            fontSize : '10',
                            fontFamily : '微软雅黑',
                        }
                    }
                }
            },
            data:""
        },
        {
            name:'月平均故障率',
            type:'bar',
            itemStyle: {
                normal: {                   // 系列级个性化，横向渐变填充
                    borderRadius: 5,
                    label : {
                        show : true,
                        textStyle : {
                            fontSize : '10',
                            fontFamily : '微软雅黑',
                            // fontWeight : 'bold'
                        }
                    }
                }
            },
            data:""
        },
    ]
};

function addTr(oMsg){
	console.log(oMsg);
    var allrate   = "<th>月总故障率</th>"
    var count     = "<th>月故障次数</th>";
    var rate      = '<th>月平均故障率</th>';
    var undone    = '<th>未填故障</th>';
    var lossless  = '<th>无损故障</th>';
    var allcount  = '<th>故障总数</th>';
    for(var i in oMsg.count)
    {
        allrate      += '<td style="background-color:#FF7F50">'+oMsg.allrate[i]+'</td>';
        count     += '<td style="background-color:#87CEFA">'+oMsg.count[i]+'</td>';
        rate      += '<td style="background-color:#DA70D6">'+oMsg.rate[i]+'</td>';
        undone    += '<td>'+oMsg.undone[i]+'</td>';
        lossless  += '<td>'+oMsg.lossless[i]+'</td>';
        allcount  += '<td>'+oMsg.allcount[i]+'</td>';
    }
    $('#list table tbody tr:first-child').html(allrate);
    $('#list table tbody tr:nth-child(2)').html(count);
    $('#list table tbody tr:nth-child(3)').html(rate);
    $('#list table tbody tr:nth-child(4)').html(undone);
    $('#list table tbody tr:nth-child(5)').html(lossless);
    $('#list table tbody tr:last-child').html(allcount);
}