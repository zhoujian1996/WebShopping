package tmall.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import tmall.bean.*;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 17:26
 */
public class OrderItemDAO {

    /**
     *
     * @return
     * 获得Category的所有记录
     */
    public int getTotal(){
        int total = 0;
        try {
            Connection connection = DBUtil.getConnection();
            Statement statement =connection.createStatement();
            String sql = "select count(*) from orderItem";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  total;
    }

    /**
     * 是否存某产品的订单项
     * @param pid
     * @return
     */
    public OrderItem isExist(int pid){
        OrderItem orderItem1 = null;
        List<OrderItem> orderItems = list();
        for(OrderItem orderItem:orderItems){

            if((orderItem.getProduct().getId()==pid) && (orderItem.getOrder().getId()==-1)){
               orderItem1 = orderItem;
                break;
            }
        }
        return  orderItem1;
    }

    public void add(OrderItem orderItem){

        try {
            Connection connection = DBUtil.getConnection();
            String sql = "insert into ORDERITEM values (null,?,?,?,?); ";
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,orderItem.getProduct().getId());

            //订单项在创建的时候，是没有订单信息的
            if(orderItem.getOrder()==null)
                   ps.setInt(2,-1);
                else
                     ps.setInt(2,orderItem.getOrder().getId());

            ps.setInt(3,orderItem.getUser().getId());
            ps.setInt(4,orderItem.getNumber());
            ps.execute();

            ResultSet rs =  ps.getGeneratedKeys();//获得getGeneratedKeys
            if(rs.next()){
                int id = rs.getInt(1);
                orderItem.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(OrderItem orderItem){


        try {
            String sql ="update ORDERiTEM set pid=?,oid=?,uid=?,number=? where id =?;";

            Connection connection = DBUtil.getConnection();
            PreparedStatement ps = null;
            ps = connection.prepareStatement(sql);
            ps.setInt(1,orderItem.getProduct().getId());
            ps.setInt(2,orderItem.getOrder().getId());
            ps.setInt(3,orderItem.getUser().getId());
            ps.setInt(4,orderItem.getNumber());
            ps.setInt(5,orderItem.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void delete(int id){
        try {
            String sql ="delete from  ORDERITEM where id =?;";

            Connection connection = DBUtil.getConnection();
            PreparedStatement ps = null;
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OrderItem get(int id){
        OrderItem orderItem = new OrderItem();
        try {
            String sql ="select * from ORDERITEM where id =?;";
            Connection connection = DBUtil.getConnection();
            PreparedStatement ps = null;
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                orderItem.setId(rs.getInt("id"));
                Order order = new Order();


                int pid = rs.getInt("pid");
                Product product = new ProductDAO().get(pid);

                int uid = rs.getInt("uid");
                User user = new UserDAO().get(uid);

                order.setId(rs.getInt("oid"));
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setUser(user);

                orderItem.setNumber(rs.getInt("NUMBER"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  orderItem;
    }

    public List<OrderItem>list(){
        List<OrderItem> orderItems = new ArrayList<>();
        try {
            String sql ="select * from orderItem;";
            Connection connection = DBUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getInt("id"));

                Order order = new Order();
                int pid = rs.getInt("pid");
                Product product = new ProductDAO().get(pid);

                int uid = rs.getInt("uid");
                User user = new UserDAO().get(uid);


                product.setId(rs.getInt("pid"));
                order.setId(rs.getInt("oid"));
                user.setId(rs.getInt("uid"));
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setUser(user);

                orderItem.setNumber(rs.getInt("NUMBER"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public List<OrderItem> list(int start,int count){
        List<OrderItem> orderItems = new ArrayList<>();
        try {
            String sql ="select * from orderItem limit ?,?;";
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,start);
            preparedStatement.setInt(2,count);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getInt("id"));
                Product product = new Product();
                Order order = new Order();
                User user = new User();

                product.setId(rs.getInt("pid"));
                order.setId(rs.getInt("oid"));
                user.setId(rs.getInt("uid"));
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setUser(user);

                orderItem.setNumber(rs.getInt("NUMBER"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public List<OrderItem> listByUser(int uid){
        return  listByUser(uid,0,Short.MAX_VALUE);
    }
    /**
     *查询某个用户的订单项
     * @param uid
     * @param start
     * @param count
     * @return
     */
    public List<OrderItem> listByUser(int uid,int start,int count){
        List<OrderItem> orderItems = new ArrayList<>();
        String sql ="select * from orderItem where uid=? and oid=-1 limit ?,?;";

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1,uid);
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,count);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getInt("id"));

                Order order = new Order();
                int pid = rs.getInt("pid");
                Product product = new ProductDAO().get(pid);


                User user = new UserDAO().get(uid);


                product.setId(rs.getInt("pid"));
                order.setId(rs.getInt("oid"));
                user.setId(rs.getInt("uid"));
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setUser(user);

                orderItem.setNumber(rs.getInt("NUMBER"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }


    /**
     * 查询某个订单下的orderItem
     * @param oid
     * @param start
     * @param count
     * @return
     */
    public List<OrderItem> listByOrder(int oid,int start,int count){
        List<OrderItem> orderItems = new ArrayList<>();
        String sql ="select * from orderItem where oid=?  limit ?,?;";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1,oid);
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,count);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getInt("id"));

                Order order = new Order();
                User user = new User();

                int pid = rs.getInt("pid");
                Product product = new ProductDAO().get(pid);
                order.setId(rs.getInt("oid"));
                user.setId(rs.getInt("uid"));
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setUser(user);

                orderItem.setNumber(rs.getInt("NUMBER"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public List<OrderItem> listByOrder(int oid){
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems = listByOrder(oid,0,Short.MAX_VALUE);
        return orderItems;
    }


    /**
     * 获取某一种产品的销量
     * @param pid
     * @return
     */
    public int getSaleCount(int pid){
        int saleNumber = 0;
        String sql ="select number from orderItem where pid =?";
        try(
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1,pid);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int number = rs.getInt("number");
                saleNumber+=number;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saleNumber;


    }

    /**
     *
     * @param order
     * 为订单设置订单项集合
     */
    public void fill(Order order){
        List<OrderItem> orderItems = listByOrder(order.getId());
//            System.out.println(orderItems.size());
        float total = 0;
        int totalNumber = 0;

        for(OrderItem orderItem:orderItems){
//                System.out.println(orderItem.getNumber()+"---->"+orderItem.getProduct().getPromotePrice()+"====");
            total+= orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
            totalNumber+=orderItem.getNumber();
        }

        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(orderItems);
    }


    /**
     * 需要用到 计算某个订单中 所有订单项的上哦in数量
     * @param orders
     */
    public void fill(List<Order> orders){
        for(Order order:orders){
            List<OrderItem> orderItems = listByOrder(order.getId());
//            System.out.println(orderItems.size());
            float total = 0;
            int totalNumber = 0;

            for(OrderItem orderItem:orderItems){
//                System.out.println(orderItem.getNumber()+"---->"+orderItem.getProduct().getPromotePrice()+"====");
                total+= orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
                totalNumber+=orderItem.getNumber();
            }

            order.setTotal(total);
            order.setTotalNumber(totalNumber);
            order.setOrderItems(orderItems);
        }
    }





    public static void main(String[] args) {
        OrderItemDAO orderItemDao = new OrderItemDAO();

        OrderDAO orderDAO =new OrderDAO();

        List<Order> orders = orderDAO.list();

        orderItemDao.fill(orders);

        for(Order order:orders){
            System.out.println(order.getTotal()+"--->"+order.getTotalNumber());
        }




    }





}
