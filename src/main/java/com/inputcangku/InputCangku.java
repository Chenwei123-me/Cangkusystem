package com.inputcangku;

import com.util.DividePage;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class InputCangku extends HttpServlet {
   private InputService service=null;
    /**
     * Constructor of the object.
     */
    public InputCangku() {
        super();
    }
    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     *
     * @param req the request send by the client to the server
     * @param resp the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     *
     * @param req the request send by the client to the server
     * @param resp the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        // PrintWriter out=resp.getWriter();
        String action_flag = req.getParameter("action_flag");
        if (action_flag.equals("search")) {
            listProduction(req,resp);
        }
    }




     public void listProduction(HttpServletRequest request,
                                HttpServletResponse response){
        String path=request.getContextPath();
        String productionName=request.getParameter("inname");
        String pageNum=request.getParameter("pageNum");
         System.out.println("pageNum"+pageNum);
        if(productionName==null){
            productionName="";
        }
        int totalRecord=service.getItemCount(productionName);
        int currentPage=1;
         DividePage dividePage=new DividePage(6,totalRecord);
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
             request.setAttribute("dividePage", dividePage);
             request.setAttribute("inname",productionName );
             System.out.println(productionName);
             request.getRequestDispatcher("/input.jsp").forward(request, response);
         } catch (Exception e) {
             // TODO: handle exception
             e.printStackTrace();
         }



}
    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy();
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
         service= new InputDao();
    }
}
