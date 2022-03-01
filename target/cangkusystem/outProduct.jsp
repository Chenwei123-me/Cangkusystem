
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.util.DividePage" %>
<%@ page import="com.outcangku.OutCangKuService" %>
<%@ page import="com.outcangku.OutCangKuDao" %><%--
  Created by IntelliJ IDEA.
  User: CW
  Date: 2021/9/2
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@include file="common/head.jsp"%>
<meta http-equiv=”Content-Type” content=”text/html; charset=gb2312”>
<%
    //获取servlet inputAction中传递的参数（数据库查询结果）
    List<Map<String,Object>> list=(List<Map<String,Object>>)request.getAttribute("ListProduct");
    //获取分页对象
    DividePage dividePage=(DividePage) request.getAttribute("dividePage");
    String productionName=(String )request.getAttribute("outarticle");
    if(list==null){
        //第一次进来input.jsp默认加载所有物品
        OutCangKuService service=new OutCangKuDao();
        int totalRecord=service.getItemCount("");
        dividePage=new DividePage(5,totalRecord,1);
        int start=dividePage.fromIndex();
        int end=dividePage.toIndex();
        list=service.ListOutcangku("",start,end);
    }
%>
<script type="text/javascript">

    function first(){

        window.location.href = "<%=path%>/servlet/OutCangku?action_flag=search&pageNum=1";

    }
    function next(){

        window.location.href = "<%=path%>/servlet/OutCangku?action_flag=search&pageNum=<%=dividePage.getCurrentPage()+1%>";

    }
    function forward(){

        window.location.href = "<%=path%>/servlet/OutCangku?action_flag=search&pageNum=<%=dividePage.getCurrentPage()-1%>";

    }
    function end(){

        window.location.href = "<%=path%>/servlet/OutCangku?action_flag=search&pageNum=<%=dividePage.getPageCount() %>";

    }

    function changePage(currentPage){

        window.location.href = "<%=path%>/servlet/OutCangku?action_flag=search&pageNum="+currentPage;

    }

</script>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>出库管理页面</span>
    </div>

    <div>
    <span class="btn">
    <a href="updateremove.jsp"class="goindex">物品出库</a>
    </span>
    </div>
    <form name="form1" action="" method="post">

        <table class="articlesInfTable"  align="center" cellpadding="0" cellspacing="0">
            <tr >
                <td >
                    <form name="form1" class="articlesInfTable" action="" method="post" cellpadding="0" cellspacing="0">
                        <table border=1 width=100%  class= "articlesInfTable">
                            <tr align="center" class="firstTr">
                                <td ><input type="checkbox" name="checkall" onclick="javascript:selectAll(this.checked);" /></td>
                                <th width="20%">货物名称</th>
                                <th width="10%">品牌</th>
                                <th width="10%">规格型号</th>
                                <th width="10%">单价</th>
                                <th width="10%">数量</th>
                                <th width="30%">操作人</th>
                                <th width="15%">时间</th>

                            </tr>
                            <%
                                if(list!=null && !list.isEmpty()){

                                    for(Map<String,Object> map :list){%>

                            <tr align="center">
                                <td width=10%><input type="checkbox" name="ids" value="<%=map.get("outid") %>"/></td>
                                <td width=20%><%=map.get("outarticle") %></td>
                                <td width=30%><%=map.get("outbrand") %></td>
                                <td width=10%><%=map.get("outsype") %></td>
                                <td width=10%><%=map.get("outnum") %></td>
                                <td width=10%><%=map.get("outprice") %></td>
                                <td width=30%><%=map.get("outpeople") %></td>
                                <td width=10%><%=map.get("outtizme") %></td>


                                    <%}
   			}else{%>

                            <tr align="center">
                                <td width=4%><input type="checkbox" name="" /></td>
                                <td width=10%></td>
                                <td width=10%></td>
                                <td width=10%></td>
                                <td width=10%></td>
                                <td width=10%></td>
                                <td width=10%></td>
                                <td width=10%></td>
                            <td width=10%></td>
                            <td width=10%></td>
                            <td width=10%></td>
                                <td></td>

                            </tr><%

                            }
                        %>




                        </table>
                    </form>
                </td>

            </tr>
        </table>
    </form>
            <span>共<%=dividePage.getPageCount() %>页 </span>  <span><a href="javascript:first();">首页</a></span>
            <span><a href="javascript:forward();">上一页</a></span>
            <span><a href="javascript:next();">下一页</a></span>
            <span><a href="javascript:end();">尾页</a> </span>

    <span>跳转到<select name="select" onchange="changePage(this.value)">


                    <%
                        int pageCount = dividePage.getPageCount();
                        if(pageCount>0){
                            for(int i = 1 ; i<=pageCount;i++){%>

                    <option value="<%=i %>" <%= (i==dividePage.getCurrentPage()?"selected":"")%>>  <%=i %>
                    </option>

                    <%
                        }

                    }else{// 无记录
                    %>
                    <option value="1">1</option>
                    <%}

                    %>
    </span>
                </select>
    <p>
        <a href= "<%=path%>/servlet/ProductAction?action_flag=outexcel">导出</a>>
    </p>
    </section>
</div>
</section>
<%@include file="/common/foot.jsp" %>

