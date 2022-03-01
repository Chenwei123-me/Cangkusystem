package com.product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    //入库只要键入，就分别放入product表和incangku表 并返回boolean看是否成功。
    public boolean addProduct(List<Object> params);
    //添加产品
    boolean addInputProduct(List<Object> incangkuparams) ;
    //列出产品,为了分页，加上参数 start,end
    public List<Map<String, Object>> listProduct(String proname , int start , int end);
    //获取总的记录数
    public int getItemCount(String proname);
    public int getItemCount1(String inname);
    //批处理删除产品
    public boolean delProduct(String[] ids);
    //查询单个产品
    public Map<String, Object> viewProduct(String proid);
    //查询每种物品的总额 //列出库存产品,为了分页，加上参数 start,end
    public List<Map<String, Object>> listKuCunProduct(String proname,String probrand, int start , int end);
    public int getKuCunItemCount(String proname,String probrand);
    //入库列表
    public  List<Map<String,Object>> ListIncangku(String inname, int start, int end);
}