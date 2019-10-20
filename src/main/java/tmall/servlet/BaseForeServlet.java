package tmall.servlet;

import tmall.dao.*;
import tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author:zhoujian
 * @date:2019/10/15 0015 17:43
 */
public class BaseForeServlet extends HttpServlet {

    protected CategoryDAO categoryDAO= new CategoryDAO();
    protected OrderDAO orderDAO = new OrderDAO();
    protected OrderItemDAO orderItemDAO = new OrderItemDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ProductImageDAO productImageDAO = new ProductImageDAO();
    protected  PropertyDAO propertyDAO = new PropertyDAO();
    protected  PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected  ReviewDAO reviewDAO = new ReviewDAO();
    protected  UserDAO userDAO = new UserDAO();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{

                int start = 0;
                int count = 10;

                try{
                    start = Integer.parseInt(req.getParameter("page.start"));
                }catch (Exception e){

                }

                try{
                    count = Integer.parseInt(req.getParameter("page.count"));
                }catch(Exception e){

                }

               Page page = new Page(start,count);

                String method = (String)req.getAttribute("method");
                Method m = this.getClass().getMethod(method,HttpServletRequest.class,HttpServletResponse.class,Page.class);
                String redirect = m.invoke(this,req,resp,page).toString();

                if(redirect.startsWith("@")){

                    resp.sendRedirect(redirect.substring(1));
                    return;
                }else if(redirect.startsWith("%")){
                     resp.getWriter().print(redirect.substring(1));
                     return;
                }else{
                    req.getRequestDispatcher(redirect).forward(req,resp);
                    return;
                }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
