package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/14 0014 16:50
 */
@WebServlet(value="/productServlet")
public class ProductServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {

        String name = (String)request.getParameter("name");
        String subTitle =(String)request.getParameter("subTitle");
        Float originalPrice = Float.parseFloat((String)request.getParameter("orignalPrice"));
        Float promotePrice = Float.parseFloat((String)request.getParameter("promotePrice"));
        int stock= Integer.parseInt((String)request.getParameter("stock"));
        int cid = Integer.parseInt((String)request.getParameter("cid"));

        Product product = new Product();
        Category category = new Category();
        category.setId(cid);

        product.setCategory(category);
        product.setName(name);
        product.setSubTitle(subTitle);
        product.setOrignalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setStock(stock);
        product.setCreateDate(new Date());

        productDAO.add(product);

        return "@admin_product_list?cid="+category.getId();
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        //注意顺序，最后删除否则报错
        int id = Integer.parseInt(request.getParameter("id"));//pid
        Product product = productDAO.get(id);
        Category category = product.getCategory();
        productDAO.delete(id);

        System.out.println(category.getId());

        return "@admin_product_list?cid="+category.getId();


    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {

        //商品的id
        int id =Integer.parseInt((String)request.getParameter("id"));
        Product product = productDAO.get(id);
        request.setAttribute("p",product);
        return "admin/editProduct.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt((String)request.getParameter("id"));
        String name = (String)request.getParameter("name");
        String subTitle =(String)request.getParameter("subTitle");
        Float originalPrice = Float.parseFloat((String)request.getParameter("orignalPrice"));
        Float promotePrice = Float.parseFloat((String)request.getParameter("promotePrice"));
        int stock= Integer.parseInt((String)request.getParameter("stock"));
        int cid = Integer.parseInt((String)request.getParameter("cid"));

        Product product = new Product();

        Category category = new Category();
        category.setId(cid);

        product.setId(id);
        product.setCategory(category);
        product.setName(name);
        product.setSubTitle(subTitle);
        product.setOrignalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setStock(stock);
        product.setCreateDate(new Date());

        productDAO.update(product);

        return "@admin_product_list?cid="+category.getId();



    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {

        int cid = Integer.parseInt(request.getParameter("cid"));
        int total = productDAO.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid="+cid);
        Category category = categoryDAO.get(cid);
        List<Product> products = productDAO.list(cid,page.getStart(),page.getCount());
        request.setAttribute("c",category);
        request.setAttribute("ps",products);
        request.setAttribute("page",page);

        return "admin/listProduct.jsp";
    }

    public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {

        int pid = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.get(pid);
        request.setAttribute("p",product);
        request.setAttribute("page",page);
        List<PropertyValue> propertyValues = propertyValueDAO.list(pid);

        propertyValueDAO.init(product);

        request.setAttribute("pvs",propertyValues);
        return "admin/editProductValue.jsp";
    }


    public String updatePropertyValue(HttpServletRequest request,HttpServletResponse response,Page page){
        int pvid = Integer.parseInt(request.getParameter("pvid"));
        String value = request.getParameter("value");

        PropertyValue propertyValue = propertyValueDAO.get(pvid);
        propertyValue.setValue(value);
        propertyValueDAO.update(propertyValue);
        return  "%success";
    }



}
