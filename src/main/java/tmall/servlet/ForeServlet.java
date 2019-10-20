package tmall.servlet;

import com.sun.corba.se.spi.orb.StringPair;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang3.StringUtils;
import tmall.bean.*;
import tmall.comparator.*;
import tmall.dao.OrderDAO;
import tmall.dao.OrderItemDAO;
import tmall.util.Page;

import javax.naming.directory.SearchResult;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/15 0015 17:03
 */
@WebServlet(value = "/foreServlet")
public class ForeServlet extends BaseForeServlet {

    public String home(HttpServletRequest request, HttpServletResponse response, Page page){
        List<Category> categories = categoryDAO.list();
        productDAO.fill(categories); //为分类 填充对应的产品
        productDAO.fillByRow(categories);
        request.setAttribute("cs",categories);
        return "home.jsp";
    }


    public String register(HttpServletRequest request,HttpServletResponse response,Page page){

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        boolean exist = userDAO.isExist(name);
        if(exist){
            request.setAttribute("msg","用户名已经存在，不能使用");
            return  "register.jsp";
        }else{
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            userDAO.add(user);
            return "@registerSuccess.jsp";

        }

    }

    public String login(HttpServletRequest request,HttpServletResponse response,Page page){

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        User user = userDAO.get(name,password);
        if(user==null){
            request.setAttribute("msg","用户名或密码错误");
            return  "login.jsp";
        }else{

            request.getSession().setAttribute("user",user);
            return  "@forehome";
        }
    }



    public String logout(HttpServletRequest request,HttpServletResponse response,Page page){

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return  "@forehome";
        }else {
            request.getSession().removeAttribute("user");
            return "@forehome";
        }

    }


    public String product(HttpServletRequest request,HttpServletResponse response,Page page){

        int  pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productDAO.get(pid);
        request.setAttribute("p",product);
        List<PropertyValue> pvs = propertyValueDAO.list(pid);
        request.setAttribute("pvs",pvs);
        return "product.jsp";


    }


    public String checkLogin(HttpServletRequest request,HttpServletResponse response,Page page){
        User user = (User)request.getSession().getAttribute("user");
        if(null!=user)
            return "%success";
        return "%fail";
    }

    public String loginAjax(HttpServletRequest request,HttpServletResponse response,Page page){

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user = userDAO.get(name,password);
        if(user==null){
            return  "%fail";
        }
        request.getSession().setAttribute("user",user);
        return "%success";
    }


    public String category(HttpServletRequest request,HttpServletResponse response,Page page){

        int cid = Integer.parseInt(request.getParameter("cid"));

        Category category = categoryDAO.get(cid);

        productDAO.fill(category);


        String sort = request.getParameter("sort");
        if(null!=sort){
        switch(sort) {

            case "saleCount":
                Collections.sort(category.getProducts(), new ProductSaleCountComparator());
                break;

            case "all":
                Collections.sort(category.getProducts(), new ProductAllComparator());
                break;


            case "date":
                Collections.sort(category.getProducts(), new ProductDateComparator());
                break;
            case "price":
                Collections.sort(category.getProducts(), new ProductPromotePrice());
                break;

            case "review":
                Collections.sort(category.getProducts(), new ProductReviewComparator());
                break;

        }






        }

        request.setAttribute("c",category);

        return "category.jsp";
    }


    public String search(HttpServletRequest request,HttpServletResponse response,Page page){
        String key = request.getParameter("keyword");
        if(key==null)
            return "@forehome";
        else{

            List<Product> products = productDAO.search(key);
            request.setAttribute("ps",products);
            return "searchResult.jsp";
        }

    }


    /**
     * 立即购买
     * @param request
     * @param response
     * @param page
     * @return
     */
        public String buyone(HttpServletRequest request,HttpServletResponse response,Page page){
            int pid = Integer.parseInt(request.getParameter("pid"));
            int num = Integer.parseInt(request.getParameter("num"));

            int oiid = 0;

            //之前已经存在该订单项只需修改数量即可
           if(orderItemDAO.isExist(pid)!=null){
               System.out.println("之前已经存在订单");
               OrderItem orderItem1 = orderItemDAO.isExist(pid);
               orderItem1.setNumber(orderItem1.getNumber()+num);
               orderItemDAO.update(orderItem1);
               oiid = orderItem1.getId();

           }else {
               //之前不存在订单项
               System.out.println("之前不存在订单项");
               Product product = productDAO.get(pid);
               OrderItem orderItem = new OrderItem();
               orderItem.setUser((User) request.getSession().getAttribute("user"));
               orderItem.setNumber(num);
               orderItem.setProduct(product);
               orderItemDAO.add(orderItem);
               oiid = orderItem.getId();
           }
//            return "forebuy?oiid="+oiid;
            return  "@forebuy?oiid="+oiid;
        }

        //http://localhost:8080/tmallFullStack/forebuy?oiid=5

    /**
     * 结算页面
     * @param request
     * @param response
     * @param page
     * @return
     */
        public String buy(HttpServletRequest request,HttpServletResponse response,Page page){


            String[] oiids = request.getParameterValues("oiid");
            List<OrderItem> ois = new ArrayList<>();
            float total =0 ;

            for(String strid:oiids){
                int oiid = Integer.parseInt(strid);
                OrderItem oi = orderItemDAO.get(oiid);
                total +=oi.getProduct().getPromotePrice()*oi.getNumber();
                ois.add(oi);

            }

            request.getSession().setAttribute("ois",ois);

            request.getSession().setAttribute("ois",ois);
            request.setAttribute("total",total);
            return  "buy.jsp";

        }


    /**
     * 加入购物车
     * @param request
     * @param response
     * @param pag
     * @return
     */
        public String addCart(HttpServletRequest request,HttpServletResponse response,Page pag){

            int pid = Integer.parseInt(request.getParameter("pid"));
            int num = Integer.parseInt(request.getParameter("num"));

            int oiid = 0;

            //之前已经存在该订单项只需修改数量即可
            if(orderItemDAO.isExist(pid)!=null){
                System.out.println("之前已经存在订单");
                OrderItem orderItem1 = orderItemDAO.isExist(pid);
                orderItem1.setNumber(orderItem1.getNumber()+num);
                orderItemDAO.update(orderItem1);
                oiid = orderItem1.getId();

            }else {
                //之前不存在订单项
                System.out.println("之前不存在订单项");
                Product product = productDAO.get(pid);
                OrderItem orderItem = new OrderItem();
                orderItem.setUser((User) request.getSession().getAttribute("user"));
                orderItem.setNumber(num);
                orderItem.setProduct(product);
                orderItemDAO.add(orderItem);
                oiid = orderItem.getId();
            }
//            return "forebuy?oiid="+oiid;
            return  "%success";


        }

    /**
     * 查看购物车
     * @param request
     * @param response
     * @param page
     * @return
     */
        public String  cart(HttpServletRequest request,HttpServletResponse response,Page page){
            User user = (User) request.getSession().getAttribute("user");
            List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
            request.setAttribute("ois",ois);
            return"cart.jsp";
        }


    /**
     * 改变OrderItem中订购商品的数量;通过ajax实现
     * @param request
     * @param response
     * @param page
     * @return
     */
        public String changeOrderItem(HttpServletRequest request,HttpServletResponse response,Page page){

            int pid = Integer.parseInt(request.getParameter("pid"));
            int number = Integer.parseInt(request.getParameter("number"));

            OrderItem orderItem = orderItemDAO.isExist(pid);
            orderItem.setNumber(number);
            orderItemDAO.update(orderItem);

            return "%success";

        }


        public String deleteOrderItem(HttpServletRequest request,HttpServletResponse response,Page page){

            int oiid = Integer.parseInt(request.getParameter("oiid"));

            orderItemDAO.delete(oiid);
            return  "%success";
        }

    /**
     * 生成订单，进行数据库的更新
     * @param request
     * @param response
     * @param page
     * @return
     */
        public String  createOrder(HttpServletRequest request,HttpServletResponse response,Page page){

            List<OrderItem> ois = (List<OrderItem>) request.getSession().getAttribute("ois");

            //首先获取订单页提取的参数
            String  address = request.getParameter("address");
            String post = request.getParameter("post");
            String receiver = request.getParameter("receiver");
            String mobile = request.getParameter("mobile");
            String userMessage = request.getParameter("userMessage");

            Date createDate = new Date();

            User user = (User)request.getSession().getAttribute("user");
            String status = OrderDAO.WAIT_PAY;

            Order order = new Order();

            order.setStatus(status);
            order.setMobile(mobile);
            order.setUserMessage(userMessage);
            order.setCreateDate(createDate);
            order.setAddress(address);
            order.setPost(post);
            order.setUser(user);
            order.setreceiver(receiver);

            String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            order.setOrderCode(orderCode);
            orderDAO.add(order);

            float total = 0;
            for(OrderItem oi:ois){

                oi.setOrder(order);
                orderItemDAO.update(oi);
                total = oi.getProduct().getPromotePrice()*oi.getNumber();
            }


            int oid = order.getId();
//            System.out.println("order的id为"+oid);
            return "@forealipay?total="+total+"&oid="+oid;




        }



        public String alipay(HttpServletRequest request,HttpServletResponse response,Page page){

//            float total = Float.parseFloat(request.getParameter("total"));
//            int oid = Integer.parseInt(request.getParameter("oid"));
//            request.setAttribute("total",total);
//            request.setAttribute("oid",oid);

            return  "alipay.jsp";


        }

    /**
     * 付款成功
     * @param request
     * @param response
     * @param page
     * @return
     */
        public String payed(HttpServletRequest request,HttpServletResponse response,Page page){

            int oid = Integer.parseInt(request.getParameter("oid"));



            Order order = orderDAO.get(oid);
            request.setAttribute("o",order);
            order.setStatus(OrderDAO.WAIT_DELIEVERY);
            order.setPayDate(new Date());
            orderDAO.update(order);
            return "payed.jsp";


        }

    /**
     * 查看已经够买的订单
     * @param request
     * @param response
     * @param page
     * @return
     */
        public String bought(HttpServletRequest request,HttpServletResponse response,Page page){

            User user = (User)request.getSession().getAttribute("user");
            List<Order> os = orderDAO.list(user.getId());

            orderItemDAO.fill(os);
            request.setAttribute("os",os);

            return  "bought.jsp";

        }

    /**
     * 确认收货
     * @param request
     * @param response
     * @param page
     * @return
     */
        public String confirmPay(HttpServletRequest request,HttpServletResponse response,Page page){

            int oid = Integer.parseInt(request.getParameter("oid"));
//            System.out.println(oid);
            Order order = orderDAO.get(oid);
            orderItemDAO.fill(order);

            orderDAO.update(order);
//            System.out.println("order"+order);
//            System.out.println(order);


            request.setAttribute("o",order);
            return "confirmPay.jsp";


        }

        public String orderConfirmed(HttpServletRequest request,HttpServletResponse response,Page page){

            int oid = Integer.parseInt(request.getParameter("oid"));
            Order order = orderDAO.get(oid);

            order.setStatus(OrderDAO.WAIT_REVIEW);
            order.setConfirmDate(new Date());
            orderDAO.update(order);

            return "orderConfirmed.jsp";
        }


        public String review(HttpServletRequest request,HttpServletResponse response,Page page){

            int oid = Integer.parseInt(request.getParameter("oid"));

            Order order = orderDAO.get(oid);

            orderItemDAO.fill(order);

            Product product = order.getOrderItems().get(0).getProduct();

            List<Review> reviews = reviewDAO.list(product.getId());

            request.setAttribute("reviews",reviews);

            request.setAttribute("p",product);
            request.setAttribute("o",order);
            return "review.jsp";
        }


       public String doreview(HttpServletRequest request,HttpServletResponse response,Page page){

            String content = request.getParameter("content");

            int oid = Integer.parseInt(request.getParameter("oid"));

            int pid = Integer.parseInt(request.getParameter("pid"));
            User user = (User)request.getSession().getAttribute("user");


           Review review = new Review();
           review.setContent(content);
           review.setUser(user);
           review.setCreateDate(new Date());
           review.setProduct(productDAO.get(pid));

           reviewDAO.add(review);

           return "@forereview?oid="+oid+"&showonly=true";




       }


}
