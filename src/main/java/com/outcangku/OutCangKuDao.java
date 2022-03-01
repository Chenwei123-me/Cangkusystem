package com.outcangku;
import com.jdbc.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutCangKuDao implements OutCangKuService {
    JdbcUtils jdbcUtils;
    public OutCangKuDao(){
        jdbcUtils = new JdbcUtils();
    }
    //从sql查出多个
    public List<Map<String,Object>> ListOutcangku(String outarticle, int start, int end) {   //返回结果数组
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        List<Object> params=new ArrayList<Object>();
        //设置数据库连接参数
        try {
            jdbcUtils.getConnection();
            String sql="select * from outcangku where 1=1 and outarticle like ?limit ?,?";
            if (outarticle.equals("")) {
                sql="select * from outcangku  limit ?,?";
                params.add(start);
                params.add(end);
            }else{
                params.add("%"+outarticle+"%");
                params.add(start);
                params.add(end);
            }
            list=jdbcUtils.findMoreResult(sql,params);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            jdbcUtils.releaseConn();
        }





        return list;
    }

    public   int  getItemCount(String outarticle){
        int count=0;
        Map<String, Object> map = null;
        List<Object> params = null;
        try {
            jdbcUtils.getConnection();
            String sql = "select count(*) totalCount from outcangku where 1=1 and outarticle like ?";
            if(outarticle.equals("")){
                sql = "select count(*) totalCount from outcangku";

            }else{
                params = new ArrayList<Object>();
                params.add("%"+outarticle+"%");
            }
            map = jdbcUtils.findSimpleResult(sql, params);
            count = Integer.parseInt(map.get("totalCount").toString());

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{
            // 关闭数据库连接
            jdbcUtils.releaseConn();
        }
        return count;
    }
}
