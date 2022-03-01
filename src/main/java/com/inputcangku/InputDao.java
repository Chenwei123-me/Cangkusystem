package com.inputcangku;
import com.jdbc.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InputDao implements InputService {
    JdbcUtils jdbcUtils;
    public InputDao(){
        jdbcUtils = new JdbcUtils();
    }
    //从sql查出多个
 public List<Map<String,Object>> ListIncangku(String inname, int start, int end) {   //返回结果数组
     List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
     List<Object> params=new ArrayList<Object>();
     //设置数据库连接参数
     try {
     jdbcUtils.getConnection();
     String sql="select * from incangku where 1=1 and inname like ?limit ?,?";
     if (inname.equals("")) {
         sql="select * from incangku  limit ?,?";
       params.add(start);
       params.add(end);
     }else{
         params.add("%"+inname+"%");
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

  public   int  getItemCount(String inname){
     int count=0;
      Map<String, Object> map = null;
      List<Object> params = null;
      try {
          jdbcUtils.getConnection();
          String sql = "select count(*) totalCount from incangku where 1=1 and inname like ?";
          if(inname.equals("")){
              sql = "select count(*) totalCount from incangku";

          }else{
              params = new ArrayList<Object>();
              params.add("%"+inname+"%");
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
