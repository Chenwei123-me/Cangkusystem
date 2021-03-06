package com.product;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdbc.JdbcUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.util.DividePage;
import com.util.UUIDTools;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class ProductAction extends HttpServlet {

    private ProductService service;
    /**
     * Constructor of the object.
     */
    public ProductAction() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
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
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
       // PrintWriter out = response.getWriter();

        String action_flag = request.getParameter("action_flag");
        if (action_flag.equals("add")) {
            addProduct(request,response);
        }else if (action_flag.equals("search")) {
            listProduct(request,response);
        }else if (action_flag.equals("search2")) {
            listProduction(request,response);
        }else if (action_flag.equals("del")) {
            delProduct(request,response);
        }else if (action_flag.equals("view")) {
            viewProduct(request,response);
        }else if (action_flag.equals("outexcel")) {
            outExcel(request,response);
        }else if (action_flag.equals("search1")) {
            listKuCunProduct(request, response);
        }

     //   out.flush();
      //  out.close();
    }

    private void viewProduct(HttpServletRequest request,
                             HttpServletResponse response) {
        // TODO Auto-generated method stub
        String proid = request.getParameter("proid");
        Map<String, Object> map = service.viewProduct(proid);
        request.setAttribute("productMap", map);
        try {
            request.getRequestDispatcher("/viewProduct.jsp").forward(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**??????????????????
     * @param request
     * @param response
     */
    private void delProduct(HttpServletRequest request,
                            HttpServletResponse response) {
        // TODO Auto-generated method stub

        System.out.println("??????del");
        //?????????????????????
        String[] ids = request.getParameterValues("ids");
        for (int i = 0; i < ids.length; i++) {
            System.out.println("ids["+i+"]="+ids[i]);
        }
        boolean flag = service.delProduct(ids);
        System.out.println("??????flag:"+flag);
        if (flag) {
            try {
                request.getRequestDispatcher("/main.jsp").forward(request, response);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


//????????????
    private void listKuCunProduct(HttpServletRequest request,
                             HttpServletResponse response) {
        // TODO Auto-generated method stub
        String  path1 = request.getContextPath();
        String KuCunProductName = request.getParameter("proname");
        String KuCunProductBrand = request.getParameter("probrand");
        String kuCunPageNum = request.getParameter("kuCunPageNum");
        System.out.println("?????? kuCunPageNum :"+kuCunPageNum);
        if (KuCunProductName == null) {
            KuCunProductName = "";
        }

        if (KuCunProductBrand == null) {
            KuCunProductBrand = "";
        }

        int totalRecord = service.getKuCunItemCount(KuCunProductName,KuCunProductBrand); //?????????????????????
        int currentPage = 1;
        DividePage dividePage = new DividePage(6, totalRecord);//?????????????????????
        if (kuCunPageNum != null) {


            currentPage = Integer.parseInt(kuCunPageNum);

            dividePage.setCurrentPage(currentPage);
        }

        //????????????????????????
        int start = dividePage.fromIndex();
        //??????????????????
        int end = dividePage.toIndex();

        System.out.println("currentPageNum :"+ dividePage.getCurrentPage() +", start = "+start +", end = "+end);

        List<Map<String, Object>> list = null;
        try {
            list = (List<Map<String, Object>>) service.listKuCunProduct(KuCunProductName ,KuCunProductBrand, start , end);
            System.out.println(list.toString());
            request.setAttribute("listKuCunProduct", list);
            request.setAttribute("dividePage", dividePage);
            request.setAttribute("KuCunProductBrand",KuCunProductBrand );
            request.getRequestDispatcher("/kucun.jsp").forward(request, response);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
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
                    }else if (fileItemName.equals("protime")) {
                        params.add(fileItemValue);//???????????? protime
                        incangkuparams.add(fileItemValue);
                    }else if (fileItemName.equals("proprice")) {
                        params.add(fileItemValue);//???????????? proprice
                        incangkuparams.add(fileItemValue);
                    }else if (fileItemName.equals("proaddress")) {
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
                    File uploadFile = new File(upload_dir+"/"+imageName);
                    System.out.println("---upload_dir--->>"+uploadFile);
                    fileItem.write(uploadFile);
                }
            }
            // ????????????????????????
            service.addProduct( params);
            // ????????????????????????
            boolean flag = service.addInputProduct( incangkuparams);
            System.out.println(flag);
          //  boolean flag2=service.addProduct(params);
            if (flag) {

                response.sendRedirect(path+"" + "/input.jsp");
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }



    }

    private void outExcel (HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        System.out.println("chenggong");

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //1.???????????????
        HSSFWorkbook wb=new HSSFWorkbook();
        //2.?????????
        HSSFSheet sheet= wb.createSheet("?????????");
        //3.sheet?????????0???
        HSSFRow row=sheet.createRow(0);

        //4.???sheet????????????????????????????????????
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFCell cell =row.createCell(0);
        cell.setCellValue("??????");
        cell.setCellStyle(style);

        cell =row.createCell(1);
        cell.setCellValue("??????");
        cell.setCellStyle(style);

        cell =row.createCell(2);
        cell.setCellValue("??????");
        cell.setCellStyle(style);

        cell =row.createCell(3);
        cell.setCellValue("??????");
        cell.setCellStyle(style);

        cell =row.createCell(4);
        cell.setCellValue("??????");
        cell.setCellStyle(style);

        cell =row.createCell(5);
        cell.setCellValue("??????");
        cell.setCellStyle(style);

        cell =row.createCell(6);
        cell.setCellValue("??????");
        cell.setCellStyle(style);




        //5.??????????????????

        //?????????????????????????????????;
        String proname=request.getParameter("proname");
        if(proname==null){
            proname="";
        }
        String sql = "select * from product ";
        List<Object> params  = new ArrayList<Object>();
        params.add(proname);
        System.out.println(proname+'C');
        try {
            List<Map<String, Object>> list = jdbcUtils.findMoreResults(sql);
            int i=0;
            for(Map<String,Object> map :list){

                map.get("proname");

                row=sheet.createRow(i+1);
                row.createCell(0).setCellValue( (String)map.get("proid"));
                row.createCell(1).setCellValue( (String)map.get("proname"));
                row.createCell(2).setCellValue( (String)map.get("probrand"));
                row.createCell(3).setCellValue( (String)map.get("protype"));
                row.createCell(4).setCellValue( (String)map.get("prounit"));
                row.createCell(5).setCellValue( map.get("proprice").toString().replace(".0",""));
                row.createCell(6).setCellValue( map.get("pronum").toString());

                row.createCell(7).setCellValue( (String)map.get("proaddress"));
                row.createCell(8).setCellValue( map.get("protime").toString());
i++;
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }

//6.??????excel
        OutputStream outputStream=response.getOutputStream();
        String filename="??????.xls";//????????????
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(filename,"UTF-8"));
        wb.write(outputStream);


    }
    private void exportProduct(HttpServletRequest request, HttpServletResponse response){}

    private void listProduct(HttpServletRequest request,
                             HttpServletResponse response) {
        // TODO Auto-generated method stub
        String  path = request.getContextPath();
        String productName = request.getParameter("proname");
        String pageNum = request.getParameter("pageNum");
        System.out.println("?????? pageNum :"+pageNum);
        if (productName == null) {
            productName = "";
        }



        int totalRecord = service.getItemCount(productName); //?????????????????????
        int currentPage = 1;
        DividePage dividePage= new DividePage(8, totalRecord);//?????????????????????
        if (pageNum != null) {


            currentPage = Integer.parseInt(pageNum);

            dividePage.setCurrentPage(currentPage);
        }

        //????????????????????????
        int start = dividePage.fromIndex();
        //??????????????????
        int end = dividePage.toIndex();

        System.out.println("currentPageNum :"+ dividePage.getCurrentPage() +", start = "+start +", end = "+end);

        List<Map<String, Object>> list = null;
        try {
            list = service.listProduct(productName , start , end);
            System.out.println(list.toString());
            request.setAttribute("listAllProduct", list);
            request.setAttribute("dividePage", dividePage);
            request.setAttribute("productName",productName );
            request.getRequestDispatcher("/articlesinf.jsp").forward(request, response);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }







    public void listProduction(HttpServletRequest request,
                               HttpServletResponse response){
        String path=request.getContextPath();
        String productionName=request.getParameter("inname");
        String pageNum=request.getParameter("pageNum1");
        System.out.println("pageNum"+pageNum);
        if(productionName==null){
            productionName="";
        }
        int totalRecord=service.getItemCount1(productionName);
        int currentPage=1;
        DividePage dividePage=new DividePage(4,totalRecord);
        if(pageNum!=null){
            currentPage=Integer.parseInt(pageNum);
            dividePage.setCurrentPage(currentPage);
        }
        int start=dividePage.fromIndex();
        int end=dividePage.toIndex();
        System.out.println("currentPageNum :"+ dividePage.getCurrentPage() +", start = "+start +", end = "+end);
        List<Map<String, Object>> list = null;
        try {
            list = service.ListIncangku(productionName , start , end);
            System.out.println(list.toString());
            request.setAttribute("listProduct", list);
            request.setAttribute("dividePage1", dividePage);
            request.setAttribute("inname",productionName );
            request.getRequestDispatcher("/input.jsp").forward(request, response);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }



    }


    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
        service = new ProductDao();
    }

}
