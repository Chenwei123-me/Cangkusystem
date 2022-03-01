package com.product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jdbc.JdbcUtils;

public class ProductDao implements ProductService {

    private JdbcUtils jdbcUtils;
    public ProductDao() {
        // TODO Auto-generated constructor stub
        jdbcUtils = new JdbcUtils();
    }

    @Override
    public boolean addInputProduct(List<Object> incangkuparams) {

        boolean flag = false;
        try {
            jdbcUtils.getConnection();

            String incangkusql="insert into incangku(inid,inname,inbrand,intype,inunit,inprice,innum,intime,inaddress,inpeople) values(?,?,?,?,?,?,?,?,?,?)";
            flag =jdbcUtils.updateByPreparedStatement(incangkusql, incangkuparams);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{

            // 关闭数据库连接
            jdbcUtils.releaseConn();

        }


        return flag;
    }


    @Override
    public boolean addProduct(List<Object> params) {

        boolean flag = false;
        try {
            jdbcUtils.getConnection();
            String sql = "insert into product(proid,proname,probrand,protype,prounit,proprice,pronum,protime,proaddress,proimage) values(?,?,?,?,?,?,?,?,?,?)";

            flag = jdbcUtils.updateByPreparedStatement(sql, params) ;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{

            // 关闭数据库连接
            jdbcUtils.releaseConn();

        }


        return flag;
    }


    //查询总记录数
    @Override
    public int getItemCount(String proname) {
        // TODO Auto-generated method stub
        int count = 0;
        Map<String, Object> map = null;
        List<Object> params = null;
        try {
            jdbcUtils.getConnection();
            String sql = "select count(*) totalCount from product where 1=1 and proname like ?";
                 if(proname.equals("")){
                sql = "select count(*) totalCount from product";

            }else{
                params = new ArrayList<Object>();
                params.add("%"+proname+"%");
            }
            map = jdbcUtils.findSimpleResult(sql, params);
            String []p=new String[5];
            p[0]="probrand";p[1]="protype";p[2]="prounit";p[3]="proprice";p[4]="proaddress";

            int i=0;
            while(map.size()==0||map.isEmpty()){
                String sql1 ="select count(*) totalCount from product where 1=1 and "+p[i]+" like ?";
                map = jdbcUtils.findSimpleResult(sql, params);
                i++;
                if(i>4){
                    break;
                }
            }


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
    //查询总记录数
    @Override
    public int getItemCount1(String inname) {
        // TODO Auto-generated method stub
        int count = 0;
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

    @Override
    public boolean delProduct(String[] ids) {
        boolean flag = false;
        try {
            jdbcUtils.getConnection();
            if (ids!=null) {
                String[] sql = new String[ids.length];
                for(int i = 0 ; i< ids.length; i++){
                    sql[i] = "delete from product where proid = '"+ids[i]+"'";
                    System.out.println(sql[i]);
                }
                flag = jdbcUtils.deleteByBatch(sql);
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{
            // 关闭数据库连接
            jdbcUtils.releaseConn();
        }

        return flag;
    }

    @Override
    public Map<String, Object> viewProduct(String proid) {
        // TODO Auto-generated method stub
        Map<String, Object> map = null;
        try {
            jdbcUtils.getConnection();
            List<Object> params = new ArrayList<Object>();
            params.add(proid);
            String sql = "select * from product where proid = ?";
            map = jdbcUtils.findSimpleResult(sql, params);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{
            // 关闭数据库连接
            jdbcUtils.releaseConn();
        }


        return map;
    }
    @Override
    public List<Map<String, Object>> listProduct(String proname ,int start ,int end) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        List<Object> params  = new ArrayList<Object>();
        try {
            jdbcUtils.getConnection();
            String sql = "select * from product where 1=1 and proname like ? limit ? ,?";
               if(proname.equals("")){
                sql = "select * from product limit ? ,?";
                params.add(start);
                params.add(end);

            }else{
                params.add("%"+proname+"%");
                params.add(start);
                params.add(end);
            }
        String []p=new String[5];
               p[0]="probrand";p[1]="protype";p[2]="prounit";p[3]="proprice";p[4]="proaddress";
            list = jdbcUtils.findMoreResult(sql, params);
            int i=0;
               while(list.size()==0||list.isEmpty()){
                   String sql1 ="select * from product where 1=1 and "+p[i]+ " like ? limit ? ,?";
                   list = jdbcUtils.findMoreResult(sql1, params);
                   i++;
                   if(i>4){
                       break;
                   }
               }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{


            jdbcUtils.releaseConn();

        }


        return list;
    }
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

    //查询每种物品的品牌、总额、数量
    public List<Map<String, Object>> listKuCunProduct(String proname,String probrand, int start , int end){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

        try {
            jdbcUtils.getConnection();
            List<Object> params =new ArrayList<Object>();


          String sql = "SELECT proname,probrand,SUM(pronum),SUM(proprice) FROM product GROUP BY proname ,probrand;";
            if(proname.equals("")){
                sql = "SELECT proname,probrand,SUM(pronum),SUM(proprice) FROM product GROUP BY proname ,probrand limit ? ,?;";

                params.add(start);
                params.add(end);

            }else{
             sql="SELECT * FROM( SELECT proname,probrand,SUM(pronum)  SUM(proprice) FROM product GROUP BY proname ,probrand)AS t WHERE  proname LIKE '?'AND probrand LIKE '?' limit ? ,?";


                params.add("%"+proname+"%");
                params.add("%"+probrand+"%");
                params.add(start);
                params.add(end);
            }

            list = jdbcUtils.findMoreKuCunResult(sql, params);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{
            // 关闭数据库连接
            jdbcUtils.releaseConn();
        }
        return list;
    }


    @Override
    public int getKuCunItemCount(String proname,String probrand) {
        // TODO Auto-generated method stub
        int count = 0;
        Map<String, Object> map = null;
        List<Object> params = null;
        try {
            jdbcUtils.getConnection();
            String sql = "SELECT COUNT(*) totalCount FROM( SELECT proname,probrand FROM product GROUP BY proname ,probrand)AS t WHERE  proname LIKE '?'AND probrand LIKE '?'";
            if(proname.equals("")){
                sql = "SELECT COUNT(*) totalCount FROM( SELECT proname ,probrand FROM product GROUP BY proname ,probrand) temp";

            }else{
                params = new ArrayList<Object>();
                params.add("%"+proname+"%");
                params.add("%"+probrand+"%");
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