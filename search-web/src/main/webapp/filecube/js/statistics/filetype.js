var filecount;
var chart;
/**
 * 
 * 获取部门分类统计数据
 * 
 */
function getBiztypenameFacetData() {
    var bureaudata;
    $.ajax({
        type : 'POST',
        url : contextPath + '/query/solrData?',
        dataType : 'JSON',
        data : {
            facet : true,
            wt : 'json',
            q : '*:*',
            'facet.field' : 'biztypename'
        },
        async : false,
        success : function(data) {
            filecount = data.response.numFound;
            bureaudata = data.facet_counts.facet_fields.biztypename;
        },
        error : function() {
            alert("服务器异常，请稍后再试！");
        }
    });
    return bureaudata;
}

/**
 * 
 * 生成统计图
 * 
 */
function drawBureaunameFacetChart() {
    var biztypenameFacetData = getBiztypenameFacetData();
    var chart = $('#container').highcharts();
    var arr = new Array();
    var c = new Array();
    for (var i = 0; i < biztypenameFacetData.length / 2; i++) {
        var d = new Object();
        d.y = biztypenameFacetData[2 * i + 1];
        arr.push(d);
        c.push(biztypenameFacetData[2 * i]);
    }
    chart.setTitle(null, {
        text : ''
    });
    chart.xAxis[0].setCategories(c);
    chart.series[0].setData(arr);
    chart.setSize(null, 500);
}

$(document).ready(function() {
    $("#container").height(500);
    chart = new Highcharts.Chart({
        chart : {
            renderTo : 'container',
            type : 'column',
        },
        title : {
            text : '文件类型统计'
        },
        subtitle : {
            text : ''
        },
        xAxis : {
            categories : [],
            title : {
                text : null
            }
        },
        yAxis : {
            min : 0,
            title : {
                text : '数量 (份)',
                align : 'high'
            },
            labels : {
                overflow : 'justify'
            }
        },
        tooltip : {
            valueSuffix : '份'
        },
        plotOptions : {
            bar : {
                dataLabels : {
                    enabled : true
                }
            },
            series : {
                cursor : 'pointer',
                events : {
                    click : function(event) {
                        alert(event.point.url);
                    }
                }
            }
        },
        legend : {
            layout : 'vertical',
            align : 'right',
            verticalAlign : 'top',
            x : -40,
            y : 100,
            floating : true,
            borderWidth : 1,
            backgroundColor : '#FFFFFF',
            shadow : true
        },
        credits : {
            enabled : false
        },
        series : [ {
            name : '',
            data : []
        } ]
    });
    drawBureaunameFacetChart();
});
