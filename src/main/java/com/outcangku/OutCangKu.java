package com.outcangku;

import com.util.DividePage;
import com.util.UUIDTools;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;




public class OutCangKu extends HttpServlet {
    private OutCangKuService service;
    public OutCangKu() {
        super();
    }

    public void destroy() {
        super.destroy();
    }
    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     *
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     *
     * @param req the request send by the client to the server
     * @param resp the response send by the server to the client
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out=resp.getWriter();

        String action_flag = req.getParameter("action_flag");
        if (action_flag.equals("search")) {
            listProduction(req,resp);
        }else if(action_flag.equals("update")){
           UpdateRemoveProduction(req,resp);
        }
    }
    private void listProduction(HttpServletRequest request,
                               HttpServletResponse response){
        String path=request.getContextPath();
        //
        String productionName=request.getParameter("outarticle");
        String pageNum=request.getParameter("pageNum");

        if(productionName==null){
            productionName="";
        }
        int totalRecord=service.getItemCount(productionName);
        int currentPage=1;
        DividePage dividePage=new DividePage(5,totalRecord);
        if(pageNum!=null){
            currentPage=Integer.parseInt(pageNum);
            dividePage.setCurrentPage(currentPage);
        }
        int start=dividePage.fromIndex();
        int end=dividePage.toIndex();
        System.out.println("currentPageNum :"+ dividePage.getCurrentPage() +", start = "+start +", end = "+end);
        List<Map<String, Object>> list = null;
        try {
            list = service.ListOutcangku(productionName , start , end);
            request.setAttribute("ListProduct", list);
            request.setAttribute("dividePage", dividePage);
            request.setAttribute("outarticle",productionName );
            request.getRequestDispatcher("/outProduct.jsp").forward(request, response);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }



    }
  private void   UpdateRemoveProduction(HttpServletRequest request,
                           HttpServletResponse response){
      String username= (String) request.getSession().getAttribute ("username");

      //表单含有文件要提交
      String  path = request.getContextPath();
      DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
      ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
      servletFileUpload.setFileSizeMax(3*1024*1024);//单个文件大小限制3M
      servletFileUpload.setSizeMax(6*1024*1024);//上传文件总大小
      List<FileItem> list = null;
      List<Object> params = new ArrayList<   >();
      List<Object> incangkuparams = new ArrayList<Object>();
      params.add(UUIDTools.getUUID()); // 参数传 product表的主键
      incangkuparams.add(UUIDTools.getUUID()); // 参数传 product表的主键
      try {
          //解析request的请求
          list = servletFileUpload.parseRequest(request);
          //取出所有表单的值，判断非文本字段和文本字段
          for(FileItem fileItem : list){
              if (fileItem.isFormField()) {//是文本字段
                  String fileItemName = fileItem.getFieldName(); //获取 <input>控件的 名称
                  String fileItemValue = fileItem.getString("utf-8");//获取<input>控件的值
                  if (fileItemName.equals("proname")) {
                      params.add(fileItemValue); //参数传入 proname
                      incangkuparams.add(fileItemValue);;
                  } else if (fileItemName.equals("probrand")) {
                      params.add(fileItemValue);//参数传入 probrand
                      incangkuparams.add(fileItemValue);
                  } else if (fileItemName.equals("protype")) {
                      params.add(fileItemValue);//参数传入 protype
                      incangkuparams.add(fileItemValue);
                  }else if (fileItemName.equals("prounit")) {
                      params.add(fileItemValue);//参数传入 prounit
                      incangkuparams.add(fileItemValue);
                  }else if (fileItemName.equals("pronum")) {
                      params.add(fileItemValue);//参数传入 pronum
                      incangkuparams.add(fileItemValue);
                  }else if (fileItemName.equals("outtime")) {
                      params.add(fileItemValue);//参数传入 protime
                      incangkuparams.add(fileItemValue);
                  }else if (fileItemName.equals("proprice")) {
                      params.add(fileItemValue);//参数传入 proprice
                      incangkuparams.add(fileItemValue);
                  }else if (fileItemName.equals("outaddress")) {
                      System.out.println("地址名="+fileItemName+"值"+fileItemValue);
                      params.add(fileItemValue);//参数传入 proaddress
                      incangkuparams.add(fileItemValue);
                  }
              }else { //非文本字段
                  incangkuparams.add(username);
                  String imageName = fileItem.getName(); //获取文件名称
                  System.out.println("name="+imageName);
                  params.add(imageName);//参数传入  proimage
                  //String path = request.getRealPath("/upload");
                  String upload_dir = request.getServletContext().getRealPath("/upload");//获取服务器端 /upload 路径
//                  File uploadFile = new File(upload_dir+"/"+imageName);
//                  System.out.println("---upload_dir--->>"+uploadFile);
//                  fileItem.write(uploadFile);
              }
          }
          // 把产品加入数据库
//          service.addProduct( params);
//          // 把产品加入数据库
//          boolean flag = service.addInputProduct( incangkuparams);
//          System.out.println(flag);
//          //  boolean flag2=service.addProduct(params);
//          if (flag) {
//
//              response.sendRedirect(path+"" + "/outProduct.jsp");
//          }


      } catch (Exception e) {
          // TODO: handle exception
          e.printStackTrace();
      }


  }

    @Override
    public void init() throws ServletException {
        service= new OutCangKuDao();
    }
}
