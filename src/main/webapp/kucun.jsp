<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.util.*" %>
<%@ page import="com.product.*" %>
<%@include file="common/head.jsp"%>

<meta http-equiv=”Content-Type” content=”text/html; charset=gb2312”>
<%

    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//获取 session 中的 username;

//获取从 servlet ProductActiion 中 传递的参数(数据库查询的结果)
    List<Map<String,Object>> list =(List<Map<String,Object>>) request.getAttribute("listKuCunProduct");
// 获取 分页对象
    DividePage dividePage = (DividePage) request.getAttribute("dividePage");
// 获取查询的关键词
    String productName = (String) request.getAttribute("KuCunProductName");
    if(list==null){
        //第一次进 main.jsp页面，默认加载所有的产品
        ProductService service = new ProductDao();
        int totalRecord = service.getKuCunItemCount("","");
        dividePage = new DividePage(6,totalRecord,1);
        int start = dividePage.fromIndex();
        int end = dividePage.toIndex();
        list = (List<Map<String, Object>>) service.listKuCunProduct("","", start, end);
    }

%>
<link type="text/css"  rel="stylesheet" href="/layui/css/modules/laydate/default/laydate.css"  media="all">
<script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript">
    function searchProduct(){
        var th = document.form2;
        th.action="<%=path%>/servlet/ProductAction?action_flag=search";
        th.submit();
    }

    function first(){

        window.location.href = "<%=path%>/servlet/ProductAction?action_flag=search1&kuCunPageNum=1";

    }
    function next(){

        window.location.href = "<%=path%>/servlet/ProductAction?action_flag=search1&kuCunPageNum=<%=dividePage.getCurrentPage()+1%>";

    }
    function forward(){

        window.location.href = "<%=path%>/servlet/ProductAction?action_flag=search1&kuCunPageNum=<%=dividePage.getCurrentPage()-1%>";

    }
    function end(){

        window.location.href = "<%=path%>/servlet/ProductAction?action_flag=search1&kuCunPageNum=<%=dividePage.getPageCount() %>";

    }

    function changePage(currentPage){

        window.location.href = "<%=path%>/servlet/ProductAction?action_flag=search1&kuCunPageNum="+currentPage;

    }

    function selectAll(flag){

        var ids = document.getElementsByName("ids");
        for(var i = 0 ; i < ids.length ; i++){
            ids[i].checked = flag;
        }

    }

    function getSelectedCount(){

        var ids = document.getElementsByName("ids");
        var count = 0;
        for(var i = 0 ; i < ids.length ; i++)
        {

            ids[i].checked==true?count++:0;
        }
        return count;

    }

    function download() {

    }
    function del(){

        if(getSelectedCount()==0){

            alert("至少选择一个删除项！");
            return;

        }
        var th = document.form1;
        th.action="<%=path%>/servlet/ProductAction?action_flag=del";
        th.submit();

    }

    function getSelectedValue(){
        var ids = document.getElementsByName("ids");

        for(var i = 0 ; i < ids.length ; i++)
        {

            if(ids[i].checked){
                return ids[i].value;
            }
        }

    }

    function view(){

        if(getSelectedCount()<1){

            alert("至少选择一个查看项！");
            return;

        }else if(getSelectedCount()>1){
            alert("只能选择一个查看项！");
            return;
        }

        var th = document.form1;
        th.action="<%=path%>/servlet/ProductAction?action_flag=view&proid="+getSelectedValue();
        th.submit();

    }
    function outexcel(){
        var th=document.form1;

        th.action="<%=path%>/servlet/ProductAction?action_flag=outexcel&proname="+"";
        th.submit();

    }


    function logout(){

        window.location.href="<%=path %>/servlet/LogoutAction?action_flag=logout";

    }


</script>


<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>库存管理页面</span>
    </div>
    <div class="search">
        <form  name = "form2" action="" method="post">
            <span>物品信息查询</span>
             <span>物品名称</span>
            <input type="text" name="proname" value="<%= productName!=null?productName:"" %>"/>

            <input value="查 询" type="button" id="searchbutton" onclick="searchProduct()">

        </form>
    </div>
    <table class="articlesInfTable"  align="center" cellpadding="0" cellspacing="0">
        <form name="form1" class="articlesInf" action="" method="post" cellpadding="0" cellspacing="0">
            <table border=1 width=100%  class= "articlesInfTable">
                <tr  class="firstTr">

                    <td width=12%>物品名称</td>
                    <td width=12%>品牌</td>
                    <td width=12%>数量（套、个）</td>
                    <td width=12%>金额（圆）</td>

                </tr>
                <%
                    if(list!=null && !list.isEmpty()){

                        for(Map<String,Object> map :list){%>

                <tr align="center">

                    <td width=12%><%=map.get("proname") %></td>
                    <td width=12%><%=map.get("probrand") %></td>
                    <td width=12%><%=map.get("SUM(pronum)") %></td>
                    <td width=12%><%=map.get("SUM(proprice)") %></td>  <%}

                    }else{%>

                <tr align="center">

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

    </select>

    </td>
    </tr>
    <tr>
        <td>

            <button type="button" class="button3" onclick="javascript:outexcel();" >导出</button>

        </td>
    </tr>


</div>
</section>



<%@include file="/common/foot.jsp" %>