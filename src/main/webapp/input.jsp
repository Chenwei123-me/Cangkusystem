<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.product.*" %>
<%@ page import="com.util.*" %>
<%@include file="common/head.jsp"%>
<link rel="stylesheet" type="text/css" href="../css/404.css">
<meta http-equiv=”Content-Type” content=”text/html; charset=gb2312”>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取servlet inputAction中传递的参数（数据库查询结果）
    List<Map<String,Object>> list=(List<Map<String,Object>>)request.getAttribute("listProduct");
    //获取分页对象
    DividePage dividePage=(DividePage) request.getAttribute("dividePage1");
    String productionName=(String )request.getAttribute("inname");
    if(list==null){
        //第一次进来input.jsp默认加载所有物品
        ProductService service = new ProductDao();
        int totalRecord = service.getItemCount1("");
        dividePage = new DividePage(4,totalRecord,1);
        int start = dividePage.fromIndex();
        int end = dividePage.toIndex();
        list=service.ListIncangku("",start,end);
    }
%>
<script type="text/javascript">

    function first(){

        window.location.href = "<%=path%>/servlet/ProductAction?action_flag=search2&pageNum1=1";

    }
    function next(){

        window.location.href = "<%=path%>/servlet/ProductAction?action_flag=search2&pageNum1=<%=dividePage.getCurrentPage()+1%>";

    }
    function forward(){

        window.location.href = "<%=path%>/servlet/ProductAction?action_flag=search2&pageNum1=<%=dividePage.getCurrentPage()-1%>";

    }
    function end(){

        window.location.href = "<%=path%>/servlet/ProductAction?action_flag=search2&pageNum1=<%=dividePage.getPageCount() %>";

    }

    function changePage(currentPage){

        window.location.href = "<%=path%>/servlet/ProductAction?action_flag=search2&pageNum1="+currentPage;

    }

</script>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>入库管理页面</span>
    </div>

    <div>
    <span class="btn">
    <a href="addProduct.jsp"class="goindex">物品入库</a>
    </span>
    </div>
    <table class="articlesInfTable"  align="center" cellpadding="0" cellspacing="0">
        <form name="form1" action="" method="post" class="articlesInfTable"cellpadding="0" cellspacing="0">

            <table border=1 width=100%  class= "articlesInfT
            able">


                <tr align="center" class="firstTr">
                    <td ><input type="checkbox" name="checkall" onclick="javascript:selectAll(this.checked);" /></td>

                    <td width=10%>物品名称</td>
                    <td width=10%>品牌</td>
                    <td width=10%>规格型号</td>
                    <td width=10%>单位</td>
                    <td width=10%>入库单价</td>
                    <td width=10%>入库数量</td>
                    <td width=16%>入库日期</td>
                    <td width="10%">入库人</td>
                    <td width="10%">入库地点</td>
                </tr>
                <%
                    if(list!=null && !list.isEmpty()){

                        for(Map<String,Object> map :list){%>

                <tr align="center">
                    <td width=2%><input type="checkbox" name="ids" value="<%=map.get("inid") %>"/></td>

                    <td width=10%><%=map.get("inname") %></td>
                    <td width=10%><%=map.get("inbrand") %></td>
                    <td width=10%><%=map.get("intype") %></td>
                    <td width=10%><%=map.get("inunit") %></td>
                    <td width=8%><%=map.get("inprice") %></td>
                    <td width=8%><%=map.get("innum") %></td>
                    <td width=16%><%=map.get("intime") %></td>
                    <td width=10%><%=map.get("inpeople") %></td>
                    <td width=10%><%=map.get("inaddress") %></td>
                        <%}
   			}else{%>

                <tr align="center">
                    <td width=4%><input type="checkbox" name="" /></td>
                    <td width=12%></td>
                    <td width=12%></td>
                    <td width=12%></td>
                    <td width=12%></td>
                    <td width=12%></td>
                    <td width=12%></td>
                    <td width=12%></td>
                    <td width=12%></td>
                    <td></td>

                </tr><%

                }
            %>


            </table>

        </form>

    </table>



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





    </span>
</div>
</section>
<%@include file="/common/foot.jsp" %>

