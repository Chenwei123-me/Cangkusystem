package com.login;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.mail.Flags.Flag;

import com.jdbc.JdbcUtils;

public class LoginDao implements LoginService {

    private JdbcUtils jdbcUtils;
    public LoginDao() {
        // TODO Auto-generated constructor stub
        jdbcUtils = new JdbcUtils();
    }

//    @Override
//    public int login(List<Object> params) {
//        // TODO Auto-generated method stub
//        int flag = 0;
//
//        try {
//            jdbcUtils.getConnection();
//            String sql = "select * from userinfo where username = ? and pswd = ? and power";
//            Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
//            int a=map.size()-1;
//           if(map.get(a).equals("admin")){
//               return 1;
//           }else if(map.get(a).equals("user")){
//               return 2;
//           }else{
//
//               return 0;
//           }
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }finally{
//
//            //关闭数据库
//            jdbcUtils.releaseConn();
//
//        }
//
//        return flag;
//    }
    @Override
    public boolean login(List<Object> params) {
        // TODO Auto-generated method stub
        boolean flag = false;

        try {
            jdbcUtils.getConnection();
            String sql = "select * from userinfo where username = ? and pswd = ? ";
            Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
            flag = !map.isEmpty()?true:false;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{

            //关闭数据库
            jdbcUtils.releaseConn();

        }

        return flag;
    }

}