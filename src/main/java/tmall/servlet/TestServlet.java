package tmall.servlet;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import tmall.bean.Category;
import tmall.dao.CategoryDAO;
import tmall.dao.ProductDAO;
import tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/11 0011 15:37
 */
@WebServlet(value= "/testServlet")
public class TestServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("调用home方法");
        List<Category> categories = new CategoryDAO().list();
        ProductDAO productDAO = new ProductDAO();
        productDAO.fill(categories); //为分类 填充对应的产品
        productDAO.fillByRow(categories);
         req.setAttribute("cs",categories);
//        return "home.jsp";
       req.getRequestDispatcher("include/home/productsAsideCategorys.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
