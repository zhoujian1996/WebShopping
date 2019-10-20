package tmall.servlet;

import tmall.bean.User;
import tmall.util.Page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/15 0015 14:47
 */
@WebServlet(value = "/userServlet")
public class UserServlet extends  BaseBackServlet {

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

        List<User> users = userDAO.list(page.getStart(),page.getCount());
        int totalPage = userDAO.getTotal();
        page.setTotal(totalPage);
        request.setAttribute("us",users);
        request.setAttribute("page",page);

        return "admin/listUser.jsp";

    }
}
