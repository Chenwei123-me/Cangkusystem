package com.outcangku;

import java.util.List;
import java.util.Map;




    public interface OutCangKuService {

        //入库列表
        public List<Map<String,Object>> ListOutcangku(String outarticle, int start, int end);
        //获取总的记录数
        public int getItemCount(String outarticle);
      //  public boolean removeProduct(List<Object> params);
      //  public boolean addOutProduct(List<Object> params);
    }


