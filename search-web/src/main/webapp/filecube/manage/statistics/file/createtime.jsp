<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发文时间统计</title>
<%@include file="../../../../commons/commons.jsp"%>
<script type="text/javascript" src="${ctx}/commons/javascript/qui-v3.3/libs/js/framework.js"></script>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/commons/javascript/qui-v3.3/" />
<link rel="stylesheet" type="text/css" id="customSkin" />
<script src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<script>
  $(function() {
    var colors = Highcharts.getOptions().colors, categories = [ 'MSIE', 'Firefox', 'Chrome', 'Safari', 'Opera' ], name = '浏览器份额', data = [ {
      y : 55.11,
      color : colors[0],
      drilldown : {
        name : 'MSIE 版本',
        categories : [ 'MSIE 6.0', 'MSIE 7.0', 'MSIE 8.0', 'MSIE 9.0' ],
        data : [ 10.85, 7.35, 33.06, 2.81 ],
        color : colors[0]
      }
    }, {
      y : 21.63,
      color : colors[1],
      drilldown : {
        name : 'Firefox 版本',
        categories : [ 'Firefox 2.0', 'Firefox 3.0', 'Firefox 3.5', 'Firefox 3.6', 'Firefox 4.0' ],
        data : [ 0.20, 0.83, 1.58, 13.12, 5.43 ],
        color : colors[1]
      }
    }, {
      y : 11.94,
      color : colors[2],
      drilldown : {
        name : 'Chrome 版本',
        categories : [ 'Chrome 5.0', 'Chrome 6.0', 'Chrome 7.0', 'Chrome 8.0', 'Chrome 9.0', 'Chrome 10.0', 'Chrome 11.0', 'Chrome 12.0' ],
        data : [ 0.12, 0.19, 0.12, 0.36, 0.32, 9.91, 0.50, 0.22 ],
        color : colors[2]
      }
    }, {
      y : 7.15,
      color : colors[3],
      drilldown : {
        name : 'Safari 版本',
        categories : [ 'Safari 5.0', 'Safari 4.0', 'Safari Win 5.0', 'Safari 4.1', 'Safari/Maxthon', 'Safari 3.1', 'Safari 4.1' ],
        data : [ 4.55, 1.42, 0.23, 0.21, 0.20, 0.19, 0.14 ],
        color : colors[3]
      }
    }, {
      y : 2.14,
      color : colors[4],
      drilldown : {
        name : 'Opera 版本',
        categories : [ 'Opera 9.x', 'Opera 10.x', 'Opera 11.x' ],
        data : [ 0.12, 0.37, 1.65 ],
        color : colors[4]
      }
    } ];

    function setChart(name, categories, data, color) {
      chart.xAxis[0].setCategories(categories, false);
      chart.series[0].remove(false);
      chart.addSeries({
        name : name,
        data : data,
        color : color || 'white'
      }, false);
      chart.redraw();
    }

    var chart = $('#container').highcharts({
      chart : {
        type : 'column'
      },
      title : {
        text : '2011年8月浏览器份额'
      },
      subtitle : {
        text : '点击图表查看浏览器版本，再次点击返回.'
      },
      xAxis : {
        categories : categories
      },
      yAxis : {
        title : {
          text : '百分比'
        }
      },
      plotOptions : {
        column : {
          cursor : 'pointer',
          point : {
            events : {
              click : function() {
                var drilldown = this.drilldown;
                if (drilldown) { // drill down
                  setChart(drilldown.name, drilldown.categories, drilldown.data, drilldown.color);
                } else { // restore
                  setChart(name, categories, data);
                }
              }
            }
          },
          dataLabels : {
            enabled : true,
            color : colors[0],
            style : {
              fontWeight : 'bold'
            },
            formatter : function() {
              return this.y + '%';
            }
          }
        }
      },
      tooltip : {
        formatter : function() {
          var point = this.point, s = this.x + ':<b>' + this.y + '% 份额</b><br/>';
          if (point.drilldown) {
            s += '点击查看 ' + point.category + ' 版本';
          } else {
            s += '点击返回';
          }
          return s;
        }
      },
      series : [ {
        name : name,
        data : data,
        color : 'white'
      } ],
      exporting : {
        enabled : false
      }
    }).highcharts(); // return chart
  });
</script>
</head>
<body>
  <div class="box_tool_min padding_top2 padding_bottom2">
    <div class="center">
      <div class="left">
        <div class="right">
          <table width="100%">
            <tr>
              <td width="50%" style="padding-left: 10px;">当前位置：搜索统计&gt;&gt;热门文件</td>
              <td><div class="padding_right10" style="float: right">
                  <div class="box_tool_line"></div>
                </div></td>
            </tr>
          </table>
        </div>
      </div>
    </div>
    <div class="clear"></div>
  </div>
  <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>