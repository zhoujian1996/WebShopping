package tmall.servlet;

import tmall.bean.Order;
import tmall.dao.OrderDAO;
import tmall.util.Page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/15 0015 14:54
 */
@WebServlet(value = "/orderServlet")
public class OrderServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {

        List<Order> orders = orderDAO.list(page.getStart(),page.getCount());

        int totalPage = orderDAO.getTotal();
        page.setTotal(totalPage);
        orderItemDAO.fill(orders);
        for(Order order:orders){
            System.out.println(order.getId()+"----->"+order.getTotalNumber()+"----->"+order.getTotal());
        }
        request.setAttribute("os",orders);
        request.setAttribute("page",page);
        return "admin/listOrder.jsp";
    }


    /**
     * 发货功能详解
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String delivery(HttpServletRequest request, HttpServletResponse response, Page page){

        int oid = Integer.parseInt(request.getParameter("id"));
        System.out.println(oid);
        Order order = orderDAO.get(oid);

        order.setStatus(OrderDAO.WAIT_CONFIRM);
        order.setDeliveryDate(new Date());
        System.out.println(order);
        orderDAO.update(order);
        return "@admin_order_list";

    }
}
