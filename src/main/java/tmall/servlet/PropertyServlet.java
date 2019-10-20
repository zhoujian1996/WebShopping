package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.dao.PropertyDAO;
import tmall.util.Page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/14 0014 15:13
 */
@WebServlet(value="/propertyServlet")
public class PropertyServlet extends  BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        System.out.println(cid);
        Category category = new Category();
        category.setId(cid);
        String name = (String)request.getParameter("name");
        Property property = new Property();
        property.setName(name);
        property.setCategory(category);
        propertyDAO.add(property);

        return "@admin_property_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        String id1 = (String)request.getParameter("id");
        int id = Integer.parseInt(id1);
        Property p = propertyDAO.get(id);
        propertyDAO.delete(id);
        return "@admin_property_list?cid="+p.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt((String)request.getParameter("id"));
        Property property = propertyDAO.get(id);
        Category category = property.getCategory();

        request.setAttribute("p",property);


        return  "admin/editProperty.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {

        String name = (String)request.getParameter("name");
        String id1 = (String)request.getParameter("id");
        int id = Integer.parseInt(id1);
        Property property = new Property();
        System.out.println(id);
        System.out.println(name);
        property.setName(name);
        property.setId(id);
        String cid1 = request.getParameter("cid");
        int cid = Integer.parseInt(cid1);
        Category category = new Category();
        category.setId(cid); //这里需要修改
        property.setCategory(category);
        propertyDAO.update(property);
        return "@admin_property_list?cid="+cid;

    }

    /**
     *
     * @param request
     * @param response
     * @param page
     * @return
     * 根据种类的id号获取属性分类
     */
    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {

        String cid1 = request.getParameter("cid");
        int cid = Integer.parseInt(cid1);
        Category category = new Category();
        category.setId(cid);
        category.setName(categoryDAO.get(cid).getName());
       List<Property> propertys = propertyDAO.list(cid,page.getStart(),page.getCount());
       int total = propertyDAO.getTotal();
        page.setTotal(total);
        page.setParam("&cid="+cid);

       request.setAttribute("ps",propertys);
       request.setAttribute("page",page);
       request.setAttribute("c",category);

        return "admin/listProperty.jsp";
    }
}
