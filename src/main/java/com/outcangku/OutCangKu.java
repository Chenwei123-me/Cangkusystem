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

      //???????????????????????????
      String  path = request.getContextPath();
      DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
      ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
      servletFileUpload.setFileSizeMax(3*1024*1024);//????????????????????????3M
      servletFileUpload.setSizeMax(6*1024*1024);//?????????????????????
      List<FileItem> list = null;
      List<Object> params = new ArrayList<   >();
      List<Object> incangkuparams = new ArrayList<Object>();
      params.add(UUIDTools.getUUID()); // ????????? product????????????
      incangkuparams.add(UUIDTools.getUUID()); // ????????? product????????????
      try {
          //??????request?????????
          list = servletFileUpload.parseRequest(request);
          //???????????????????????????????????????????????????????????????
          for(FileItem fileItem : list){
              if (fileItem.isFormField()) {//???????????????
                  String fileItemName = fileItem.getFieldName(); //?????? <input>????????? ??????
                  String fileItemValue = fileItem.getString("utf-8");//??????<input>????????????
                  if (fileItemName.equals("proname")) {
                      params.add(fileItemValue); //???????????? proname
                      incangkuparams.add(fileItemValue);;
                  } else if (fileItemName.equals("probrand")) {
                      params.add(fileItemValue);//???????????? probrand
                      incangkuparams.add(fileItemValue);
                  } else if (fileItemName.equals("protype")) {
                      params.add(fileItemValue);//???????????? protype
                      incangkuparams.add(fileItemValue);
                  }else if (fileItemName.equals("prounit")) {
                      params.add(fileItemValue);//???????????? prounit
                      incangkuparams.add(fileItemValue);
                  }else if (fileItemName.equals("pronum")) {
                      params.add(fileItemValue);//???????????? pronum
                      incangkuparams.add(fileItemValue);
                  }else if (fileItemName.equals("outtime")) {
                      params.add(fileItemValue);//???????????? protime
                      incangkuparams.add(fileItemValue);
                  }else if (fileItemName.equals("proprice")) {
                      params.add(fileItemValue);//???????????? proprice
                      incangkuparams.add(fileItemValue);
                  }else if (fileItemName.equals("outaddress")) {
                      System.out.println("?????????="+fileItemName+"???"+fileItemValue);
                      params.add(fileItemValue);//???????????? proaddress
                      incangkuparams.add(fileItemValue);
                  }
              }else { //???????????????
                  incangkuparams.add(username);
                  String imageName = fileItem.getName(); //??????????????????
                  System.out.println("name="+imageName);
                  params.add(imageName);//????????????  proimage
                  //String path = request.getRealPath("/upload");
                  String upload_dir = request.getServletContext().getRealPath("/upload");//?????????????????? /upload ??????
//                  File uploadFile = new File(upload_dir+"/"+imageName);
//                  System.out.println("---upload_dir--->>"+uploadFile);
//                  fileItem.write(uploadFile);
              }
          }
          // ????????????????????????
//          service.addProduct( params);
//          // ????????????????????????
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
