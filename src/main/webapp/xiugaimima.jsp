<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="shortcut icon" href="images/favicon.ico">
<%@include file="/common/head.jsp"%>
<div class="right" style="background: linear-gradient(to bottom, #f3f8fc, #ebf4f9, #e3f1fa, #e0f0fd, #d8e9fd)" background-size:cover="">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>修改密码</span>
        <table border="1" align="center">
            <caption>密码修改：</caption>
            <form name="form1" onsubmit="return userCheck()" action="<%=path%>/servlet/UpdateAction" method="post">
                <tr>
                    <td>新密码</td>
                    <td><input id="psw1" type="password" name="password1"/></td>
                </tr>
                <tr>
                    <td>确认密码</td>
                    <td><input id="psw1" type="password" name="password2"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="提交" ></td>
                    <td><input type="reset" value="重置"></td>
                </tr>
            </form>
        </table>
        <script  type="text/javascript">
            function userCheck()
            {
                var pass1=document.form1.password1.value;
                var pass2=document.form1.password2.value;
                if(pass1=="" || pass2=="")
                {
                    window.alert("请填写密码");
                    return false;
                }
                else if(pass1!=pass2)
                {
                    window.alert("请重新填写密码，两次得密码不正确");
                    return false;
                }
            }
        </script>

    </div>
</div>


</section>
<%@include file="/common/foot.jsp" %>