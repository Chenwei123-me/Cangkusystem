   <%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 2021/8/20
  Time: 8:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css" />





    <base href="<%=basePath%>">

    <title>新增产品</title>
    <link rel="shortcut icon" href="images/favicon.ico">



    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link type="text/css" rel="stylesheet"  href="/layui/css/modules/laydate/default/laydate.css"  media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js" charset="utf-8"></script>
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <script type="text/javascript">
        function dosubmit(){
            var th = document.form1;
            th.action="<%= path%>/servlet/ProductAction?action_flag=add";
            th.submit();

        }

    </script>


</head>

<body>
<script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述 JS 路径需要改成你本地的 -->

<script>
    //执行一个laydate实例
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        laydate.render({
        elem: '#test5'
        ,type: 'datetime'//指定元素
    });});
</script>
<div align="center">
    <table  width=70% style="margin:auto;">
        <tr>
            <td align="center" height=150 valign="bottom">
                <h1>产品信息添加</h1>
            </td>
        </tr>
        <tr>
            <td>
                <form id="form1" name="form1" action="" method="post" enctype="multipart/form-data">
                    <table class="articlesInfTable" border=1 style="margin:auto">
                        <tr>
                            <td>物品名称</td>
                            <td><input type="text" name="proname" id="proname"/></td>
                            <td>规格型号</td>
                            <td><input type="text" name="protype" id="protype"/></td>
                            <td>品牌</td>
                            <td><input type="text" name="probrand" id="probrand"/></td>
                        </tr>
                        <tr>
                            <td>单位</td>
                            <td><input type="text" name="prounit" id="prounit"/></td>
                            <td>单价</td>
                            <td><input type="text" name="proprice" id="proprice"/></td>
                            <td>数量</td>
                            <td><input type="text" name="pronum" id="pronum"/></td>
                        </tr>
                        <tr>
                          <td>入库时间(YYYY-MM-DD HH:MM:SS)</td>
<%--                            protime   name="protime"--%>
                            <td> <input type="text" name="protime" class="layui-input" id="test5" placeholder="yyyy-MM-dd HH:mm:ss"></td>

                            <td>入库地点</td>
                            <%--                                               protime   name="protime"--%>
                            <td>
                                <input id="proaddress" name="proaddress" type="text" list="typelist" placeholder="请选择">
                                <datalist id="typelist">
                                    <option value="mibu">米埗</option>
                                    <option value="lvtian">吕田</option>
                                    <option value="lianpingnan">连平南</option>
                                </datalist>
                            </td>
                        </tr>
                        <tr>
                            <td>物品图片</td>
                            <td colspan="3"><input type="file" name="proimage" id="proimage"  size=35/></td>
                        </tr>

                    </table>
                </form>

            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <button type="button" onclick="javascript:dosubmit();">确定</button>
                <button type="button" onclick="javascript:location.href='articlesinf.jsp'">返回</button>

            </td>
        </tr>


    </table>



</div>


</body>
</html>
