package com.inputcangku;

import java.util.List;
import java.util.Map;

public interface InputService {

    //入库列表
   public  List<Map<String,Object>> ListIncangku(String inname, int start, int end);
    //获取总的记录数
    public int getItemCount(String inname);
}
