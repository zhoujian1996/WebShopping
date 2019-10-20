package tmall.filter;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.Request;
import tmall.bean.Category;
import tmall.bean.OrderItem;
import tmall.bean.User;
import tmall.dao.CategoryDAO;
import tmall.dao.OrderItemDAO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/15 0015 17:07
 */
public class ForeServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest)req;
        HttpServletResponse response =(HttpServletResponse) resp;
        String contextPath = request.getServletContext().getContextPath();
        request.getServletContext().setAttribute("contextPath",contextPath);


        User user = (User)request.getSession().getAttribute("user");
        int cartTotalNumber = 0;//购物车总数量
        if(null!=user){
            List<OrderItem> ois = new OrderItemDAO().listByUser(user.getId());
            for(OrderItem oi:ois){
                cartTotalNumber+=oi.getNumber();
            }
        }
        request.setAttribute("cartTotalItemNumber",cartTotalNumber);//根据用户获取购物车数量

        List<Category> cs = (List<Category>)request.getAttribute("cs");
        if(null==cs){
            cs = new CategoryDAO().list();
            request.setAttribute("cs",cs);
        }

        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri,contextPath);
        if(uri.startsWith("/fore")&&!uri.startsWith("/foreSevlet")){
            String method = StringUtils.substringAfterLast(uri,"/fore");
            request.setAttribute("method",method);
            req.getRequestDispatcher("/foreServlet").forward(request,response);
            return;
        }

        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
