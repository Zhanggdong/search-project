$(function() {
    zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes); //初始化zTree，三个参数一次分别是容器(zTree 的容器 className 别忘了设置为 "ztree")、参数配置、数据源
});

var zTreeObj;
var setting = {
    view: {
        selectedMulti: true, //设置是否能够同时选中多个节点
        showIcon: true,      //设置是否显示节点图标
        showLine: true,      //设置是否显示节点与节点之间的连线
        showTitle: true,     //设置是否显示节点的title提示信息
    },
    data: {
        simpleData: {
            enable: false,   //设置是否启用简单数据格式（zTree支持标准数据格式跟简单数据格式，上面例子中是标准数据格式）
            idKey: "id",     //设置启用简单数据格式时id对应的属性名称
            pidKey: "pId"    //设置启用简单数据格式时parentId对应的属性名称,ztree根据id及pid层级关系构建树结构
        }
    },
    check:{
        enable: true         //设置是否显示checkbox复选框
    },
    callback: {
        onClick: onClick,             //定义节点单击事件回调函数
        onRightClick: OnRightClick,   //定义节点右键单击事件回调函数
        beforeRename: beforeRename,   //定义节点重新编辑成功前回调函数，一般用于节点编辑时判断输入的节点名称是否合法
        onDblClick: onDblClick,       //定义节点双击事件回调函数
        onCheck: onCheck              //定义节点复选框选中或取消选中事件的回调函数
    },
    async: {
        enable: true,                      //设置启用异步加载
        type: "get",                       //异步加载类型:post和get
        contentType: "application/json",   //定义ajax提交参数的参数类型，一般为json格式
        url: "/Design/Get",                //定义数据请求路径
        autoParam: ["id=id", "name=name"]  //定义提交时参数的名称，=号前面标识节点属性，后面标识提交时json数据中参数的名称
    }
};

var zNodes=[
    {
        "id":"{00000000-0000-0000-0000-000000000000}",
        "name":"坪山区",
        "open":true,
        "isParent":true,
        "children":[
            {
                "id":"{BFA7FFFC-0000-0000-76E5-E1E100000001}",
                "name":"区领导",
                "open":true,
                "isParent":true,
                "children":[
                    {
                        "id":"{AC103D7D-FFFF-FFFF-817D-A468000000FE}",
                        "name":"区委领导",
                        "open":true,
                        "isParent":true,
                        "children":[
                            {
                                "id":"{AC10059A-FFFF-FFFF-A403-31AE00000001}",
                                "name":"吕玉印",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{AC100665-0000-0000-1561-D68B00000005}",
                                "name":"陶永欣",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{AC103D7D-FFFF-FFFF-F887-2DC4FFFFFF84}",
                                "name":"李映中",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{AC103D7D-0000-0000-6496-1C1400000D05}",
                                "name":"吴筠",
                                "open":false,
                                "isParent":false
                            }
                        ]
                    },
                    {
                        "id":"{AC103D7D-FFFF-FFFF-817E-679E000000FA}",
                        "name":"区人大领导",
                        "open":true,
                        "isParent":true,
                        "children":[
                            {
                                "id":"{AC103D7D-0000-0000-64AE-DB1500000D19}",
                                "name":"林杰三",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{AC103D7D-0000-0000-64B0-224000000D1D}",
                                "name":"蒋道超",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{BFA7FFFC-0000-0000-1B76-0DC700000052}",
                                "name":"杨勇",
                                "open":false,
                                "isParent":false
                            }
                        ]
                    },
                    {
                        "id":"{AC103D7D-FFFF-FFFF-817F-2E5900000202}",
                        "name":"区政府领导",
                        "open":true,
                        "isParent":true,
                        "children":[
                            {
                                "id":"{AC100665-FFFF-FFFF-BDDE-A89700000008}",
                                "name":"王德明",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{BFA7FFFC-0000-0000-1B7A-A49700000061}",
                                "name":"张宗武",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{AC103D7D-FFFF-FFFF-AC9E-6A6B00000077}",
                                "name":"陈华平",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{AC103D7D-FFFF-FFFF-8EC9-B4C600000036}",
                                "name":"韩云",
                                "open":false,
                                "isParent":false
                            }
                        ]
                    },
                    {
                        "id":"{AC103D7D-FFFF-FFFF-817F-5F2700000206}",
                        "name":"区政协领导",
                        "open":true,
                        "isParent":true,
                        "children":[
                            {
                                "id":"{AC103D7D-0000-0000-6494-A96B00000D00}",
                                "name":"陈主",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{AC103D7D-0000-0000-64B3-485700000D29}",
                                "name":"彭尧",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{BFA7FFFC-0000-0000-1AC4-DB0AFFFFFFB5}",
                                "name":"杨辉填",
                                "open":false,
                                "isParent":false
                            }
                        ]
                    },
                    {
                        "id":"{AC100665-FFFF-FFFF-94E6-2EC600000001}",
                        "name":"政务专员",
                        "open":true,
                        "isParent":true,
                        "children":[
                            {
                                "id":"{BFA7FFFC-0000-0000-1B22-6F1A0000001D}",
                                "name":"罗金辉",
                                "open":false,
                                "isParent":false
                            },
                            {
                                "id":"{BFA80201-0000-0000-6F9D-57C600000003}",
                                "name":"曹建新",
                                "open":false,
                                "isParent":false
                            }
                        ]
                    }
                ]
            },
            {
                "id":"{BFA7FFFC-0000-0000-6C10-1F9200000012}",
                "name":"区纪委（区监察局）",
                "open":true,
                "isParent":true,
                "children":[

                ]
            },
            {
                "id":"{BFA7FFFC-0000-0000-6AFE-665700000001}",
                "name":"区委（区政府）办公室",
                "open":true,
                "isParent":true,
                "children":[

                ]
            },
            {
                "id":"{BFA7FFFC-0000-0000-6C14-2FB600000013}",
                "name":"区委组织部",
                "open":true,
                "isParent":true,
                "children":[

                ]
            },
            {
                "id":"{BFA80201-0000-0000-6F9B-923A00000001}",
                "name":"坪山街道办事处",
                "open":true,
                "isParent":true,
                "children":[

                ]
            }
        ]
    }
];

function onClick(e, treeId, treeNode){
    if (treeNode.isParent)  //如果不是叶子结点，结束
        return;
    alert(treeNode.name);   //获取当前结点上的相关属性数据，执行相关逻辑
    getPermissionList(treeId);
}
function OnRightClick(event, treeId, treeNode){

}

function beforeRename(){

}
function onDblClick(){

}
function onCheck(){

}

function getPermissionList(employeeGUID) {
    $.ajax({
        url:"/api/core/permission/list?employeeGUID="+employeeGUID+"&pageNumber=1&pageSize=10",
        async: true,
        cache: false,
        type: "get",
        dataType: "json",
        success: function(json) {
            //console.log(json);
            $("#pagination").empty();
            $("#list_container").empty();
            $("#body").empty();
            var dataArray= json.data.data;
            for(var k=0;k<dataArray.length;k++){
                $("#body").append("<tr><td>"+dataArray[k].permissionGUID+"</td><td>"+dataArray[k].permissionName+"</td><td>"+dataArray[k].code+"</td></tr>");
            }
            var total = json.data.total;
            $("#pagination").pagination({
                callback: pageselectCallback,
                pageCount:2,
                totalData:total,
                jump:true,
                coping:true,
                count:2,
                jumpBtn:'确定',
                prevContent:"上一页",
                nextContent:"下一页",
                showData:10,
                isHide:true
            });
        }
    });

    var pageselectCallback = function (api) {
        var pageindex = api.getCurrent();
        getPermissionData(employeeGUID,pageindex);
    }
}

function getPermissionData(employeeGUID,pageindex){
    var url = "/api/core/permission/list?employeeGUID="+employeeGUID+"&pageNumber="+pageindex+"&pageSize=10";
    $.ajax({
        url:url,
        async: true,
        cache: false,
        type: "get",
        dataType: "json",
        success:function(json){
            if(json.data){
                $("#body").empty();
                var dataArray= json.data.data;
                for(var k=0;k<dataArray.length;k++){
                    $("#body").append("<tr><td>"+dataArray[k].permissionGUID+"</td><td>"+dataArray[k].permissionName+"</td><td>"+dataArray[k].code+"</td></tr>");
                }
            }
        }
    });
}