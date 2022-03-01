package com.updateUserPwd;

import com.jdbc.JdbcUtils;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

public class UpdateDao implements UpdateService {

    private JdbcUtils jdbcUtils;


    public UpdateDao() {
        // TODO Auto-generated constructor stub
        jdbcUtils = new JdbcUtils();
    }
    @Override
    public boolean updatepsw(List<Object> params) {
        // TODO Auto-generated method stub
        boolean flag = false;

        try {
            jdbcUtils.getConnection();

            int row = 0;
            String sql = "update userinfo set   pswd = ? where username = ?  ";

           flag =jdbcUtils.updateByPreparedStatement(sql,params);


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


