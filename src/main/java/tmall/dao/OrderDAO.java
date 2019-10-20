package tmall.dao;

import tmall.bean.Order;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 17:26
 */
public class OrderDAO {


    public static final String WAIT_PAY ="waitPay";
    public static final String WAIT_DELIEVERY ="waitDelivery";
    public static final String WAIT_CONFIRM ="waitConfirm";
    public static final String WAIT_REVIEW ="waitReview";
    public static final String fINISH ="finish";
    public static final String DELETE ="delete";

    /**
     *
     * @return
     * 获得Category的所有记录
     */
    public int getTotal(){
        int total = 0;
        String sql = "select count(*) from order_";

        try(Connection connection = DBUtil.getConnection();
            Statement statement =connection.createStatement();) {

            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  total;
    }

    public void add(Order order){
        String sql = "insert into ORDER_(id,orderCode,address,post,receiver,mobile,userMessage,createDate,payDate,ConfirmDate,uid,status) values (null,?,?,?,?,?,?,?,?,?,?,?); ";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

            ps.setString(1,order.getOrderCode());
            ps.setString(2,order.getAddress());
            ps.setString(3,order.getPost());
            ps.setString(4,order.getreceiver());
            ps.setString(5,order.getMobile());
            ps.setString(6,order.getUserMessage());
            ps.setTimestamp(7, DateUtil.d2t(order.getCreateDate()));
            ps.setTimestamp(8, DateUtil.d2t(order.getPayDate()));
            ps.setTimestamp(9, DateUtil.d2t(order.getConfirmDate()));
            ps.setInt(10, order.getUser().getId());
            ps.setString(11,order.getStatus());
            ps.execute();

            ResultSet rs =  ps.getGeneratedKeys();//获得getGeneratedKeys
            if(rs.next()){
                int id = rs.getInt(1);
                order.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Order order){

        String sql ="update ORDER_ set orderCode=?,address=?,post=?,receiver=?,mobile=?,userMessage=?,createDate=?,payDate=?,ConfirmDate=?,uid=?,status=?,deliveryDate=? WHERE id=?";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps =connection.prepareStatement(sql);) {


            ps.setString(1,order.getOrderCode());
            ps.setString(2,order.getAddress());
            ps.setString(3,order.getPost());
            ps.setString(4,order.getreceiver());
            ps.setString(5,order.getMobile());
            ps.setString(6,order.getUserMessage());
            ps.setTimestamp(7, DateUtil.d2t(order.getCreateDate()));
            ps.setTimestamp(8, DateUtil.d2t(order.getPayDate()));
            ps.setTimestamp(9, DateUtil.d2t(order.getConfirmDate()));
            ps.setInt(10, order.getUser().getId());
            ps.setString(11,order.getStatus());
            ps.setTimestamp(12, DateUtil.d2t(order.getDeliveryDate()));
            System.out.println(order.getDeliveryDate()+"数据库中");
            ps.setInt(13,order.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id){
        String sql ="delete from Order_ where id =?;";
        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {



            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order get(int id){
        Order order = null;
        String sql ="select * from order_ where id =?;";

        try ( Connection connection = DBUtil.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql);){

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                order = new Order();

                order.setId(rs.getInt("id"));
                order.setOrderCode(rs.getString("orderCode"));
                order.setAddress(rs.getString("address"));
                order.setPost(rs.getString("post"));
                order.setreceiver(rs.getString("receiver"));
                order.setMobile(rs.getString("mobile"));
                order.setUserMessage(rs.getString("USERMESSAGE"));
                order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                order.setPayDate(DateUtil.t2d(rs.getTimestamp("paydate")));
                order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
                User user = new User();
                user.setId(rs.getInt("uid"));
                order.setUser(user);
                order.setStatus(rs.getString("status"));



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  order;
    }

    public List<Order>list(){
       List<Order> orders = list(0,Short.MAX_VALUE);
       return  orders;
    }

    public List<Order> list(int start,int count){
        List<Order> orders = new ArrayList<>();
        String sql ="select * from order_ limit ?,?;";

        try(  Connection connection = DBUtil.getConnection();

              PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Order order = new Order();

                order.setId(rs.getInt("id"));
                order.setOrderCode(rs.getString("orderCode"));
                order.setAddress(rs.getString("address"));
                order.setPost(rs.getString("post"));
                order.setreceiver(rs.getString("receiver"));
                order.setMobile(rs.getString("mobile"));
                order.setUserMessage(rs.getString("USERMESSAGE"));
                order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                order.setPayDate(DateUtil.t2d(rs.getTimestamp("paydate")));
                order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));




                int uid = rs.getInt("uid");
                User user = new UserDAO().get(uid);
                order.setUser(user);
                order.setStatus(rs.getString("status"));

                orders.add(order);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  orders;
    }


    /**
     * 查询指定用户的订单状态，通常是"delete"
     * @param uid
     * @param excludedStatus
     * @param start
     * @param count
     * @return
     */
    public List<Order> list(int uid,String excludedStatus,int start,int count){
        List<Order> orders = new ArrayList<>();
        String sql ="select * from order_  where status!=? and uid=? limit ?,?;";

        try(Connection connection = DBUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setString(1,excludedStatus);
            ps.setInt(2,uid);
            ps.setInt(3,start);
            ps.setInt(4,count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Order order = new Order();

                order.setId(rs.getInt("id"));
                order.setOrderCode(rs.getString("orderCode"));
                order.setAddress(rs.getString("address"));
                order.setPost(rs.getString("post"));
                order.setreceiver(rs.getString("receiver"));
                order.setMobile(rs.getString("mobile"));
                order.setUserMessage(rs.getString("USERMESSAGE"));
                order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                order.setPayDate(DateUtil.t2d(rs.getTimestamp("paydate")));
                order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
                User user = new User();
                user.setId(rs.getInt("uid"));
                order.setUser(user);
                order.setStatus(rs.getString("status"));

                orders.add(order);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  orders;
    }



    /**
     * 查询指定用户的订单状态，通常是"delete"
     * @param uid
     * @param excludedStatus

     * @return
     */
    public List<Order> list(int uid,String excludedStatus){
        List<Order> orders = new ArrayList<>();
        orders = list(uid,excludedStatus,0,Short.MAX_VALUE);
        return  orders;
    }



    public List<Order> list(int uid){
        List<Order> orders = new ArrayList<>();
        String sql ="select * from order_  where uid=? ;";

        try(Connection connection = DBUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement(sql);) {



            ps.setInt(1,uid);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Order order = new Order();

                order.setId(rs.getInt("id"));
                order.setOrderCode(rs.getString("orderCode"));
                order.setAddress(rs.getString("address"));
                order.setPost(rs.getString("post"));
                order.setreceiver(rs.getString("receiver"));
                order.setMobile(rs.getString("mobile"));
                order.setUserMessage(rs.getString("USERMESSAGE"));
                order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                order.setPayDate(DateUtil.t2d(rs.getTimestamp("paydate")));
                order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
                User user = new User();
                user.setId(rs.getInt("uid"));
                order.setUser(user);
                order.setStatus(rs.getString("status"));

                orders.add(order);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  orders;
    }




    public static void main(String[] args) {
        OrderDAO orderDao = new OrderDAO();

//        Order order = new Order();
//        order.setStatus(WAIT_DELIEVERY);
//        User user = new User();
//        user.setId(2);
//        order.setUser(user);
//        order.setCreateDate(new Date());
//        order.setPayDate(new Date());
//        order.setDeliveryDate(new Date());
//        order.setConfirmDate(new Date());
//        order.setUserMessage("上海");
//        order.setMobile("178893112312");
//        order.setOrderCode("133");

//        orderDao.add(order);

        System.out.println(orderDao.list(3,WAIT_DELIEVERY));
    }





}
