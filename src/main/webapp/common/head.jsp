<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>	
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>大广机电部备品备件管理系统</title>
    <link rel="shortcut icon" href="../images/favicon.ico">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css" />
  <%  String username = (String)session.getAttribute("username");
      String path = request.getContextPath();
  %>

</head>
<body>
<!--头部-->
    <header class="publicHeader">
        <h1>大广机电部备品备件管理系统</h1>
        <div class="publicHeaderR">
            <p><span>下午好！</span><span style="color: #fff21b"> ${username}</span> , 欢迎你！</p>
            <a href=" <%=path%>/servlet/LogoutAction?action_flag=logout">退出</a>
        </div>
    </header>
<!--时间-->
    <section class="publicTime">
        <span id="time">2021年10月26日 11:11  星期一</span>
        <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
    </section>
 <!--主体内容-->
 <section class="publicMian ">
     <div class="left">
         <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
         <nav>
             <ul class="list">
              <li><a href="${pageContext.request.contextPath }/articlesinf.jsp">物品管理</a></li>
              <li><a href="${pageContext.request.contextPath }/input.jsp">入库管理</a></li>
              <li><a href="${pageContext.request.contextPath }/outProduct.jsp">出库管理</a></li>
               <li ><a href="${pageContext.request.contextPath }/kucun.jsp">库存查看</a></li>
             <!-- <li><a href=" <%=path%>/servlet/LogoutAction?action_flag=logout">退出系统</a></li>-->
                 <li ><a href="${pageContext.request.contextPath }/xiugaimima.jsp">修改密码</a></li>
             </ul>
         </nav>
     </div>
     <input type="hidden" id="path" name="path" value="${pageContext.request.contextPath }"/>
     <input type="hidden" id="referer" name="referer" value="<%=request.getHeader("Referer")%>"/>
 </section>