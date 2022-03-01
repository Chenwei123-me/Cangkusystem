<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
    <base href="<%=basePath%>">


    <title>大广机电部备品备件管理系统</title>
    <link rel="icon" href="images/favicon.ico">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/demo.css" />
    <!--必要样式-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/component.css" />
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->



    <script type="text/javascript">
        function login(){
            var th = document.form1;
            if(th.username.value==""){
                alert("用户名不能为空！");
                th.username.focus();
                return;
            }
            if(th.pswd.value==""){
                alert("密码不能为空！");
                th.pswd.focus();
                return;
            }

            th.action = "<%=path%>/servlet/LoginAction";
            th.submit();


        }

    </script>

</head>

<body>
<%--style="text-align:center"--%>
<div class="container demo-1">
    	<div class="content">
             <div id="large-header" class="large-header">
             <canvas id="demo-canvas"></canvas>
        <div class="logo_box">
            <h3>大广机电部备品备件管理系统</h3>
            <h3>欢迎你</h3>
            <form name="form1" action="" method="post">
                <div class="input_outer">
                    <span class="u_user"></span>
                    <input name="username" class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入账户">
                </div>
                <div class="input_outer">
                    <span class="us_uer"></span>
                    <input name="pswd" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
                </div>
                <div style="align-content: center"  >



            &nbsp &nbsp&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp&nbsp <button type="button" name="" value="" onclick="login()">登录</button> &nbsp&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp&nbsp  &nbsp&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp&nbsp
                   <button type="button" name="" value="" onclick="javascript:location.href='register.jsp'">注册</button>
                </div>

            </form>
        </div>
     </div>
</div>
</div>

<script src="js/TweenLite.min.js"></script>
<script src="js/EasePack.min.js"></script>
<script src="js/rAF.js"></script>
<script src="js/demo-1.js"></script>
</body>
</html>