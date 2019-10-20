package tmall.dao;

import tmall.bean.User;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 17:26
 */
public class UserDAO {

    /**
     *
     * @return
     * 获得Category的所有记录
     */
    public int getTotal(){
        int total = 0;
        String sql = "select count(*) from User";

        try( Connection connection = DBUtil.getConnection();
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

    public void add(User user){
        String sql = "insert into User(id,name,password) values (null,?,?); ";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

            ps.setString(1,user.getName());
            ps.setString(2,user.getPassword());
            ps.execute();

            ResultSet rs =  ps.getGeneratedKeys();//获得getGeneratedKeys
            if(rs.next()){
                int id = rs.getInt(1);
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(User user){

        String sql ="update User set name=? ,password=? where id =?;";

        try (  Connection connection = DBUtil.getConnection();
               PreparedStatement ps = connection.prepareStatement(sql);){


            ps.setString(1,user.getName());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id){
        String sql ="delete from  User where id =?;";

        try ( Connection connection = DBUtil.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql);){


            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User get(int id){
        User user = null;
        String sql ="select id,name,password from User where id =?;";
        try(  Connection connection = DBUtil.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setPassword(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  user;
    }

    public List<User>list(){
        List<User> users = new ArrayList<>();
        String sql ="select id,name,password from User ;";
        try( Connection connection = DBUtil.getConnection();
             Statement statement = connection.createStatement();) {


            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                User user= new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setPassword(rs.getString(3));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> list(int start,int count){
        List<User> users = new ArrayList<>();
        String sql ="select id,name,password from User limit ?,?;";
        try (    Connection connection = DBUtil.getConnection();

                 PreparedStatement ps = connection.prepareStatement(sql);){


            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                User user= new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setPassword(rs.getString(3));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  users;
    }


   //除开CRUD之外，UserDao还提供了一用于支持业务的方法
    //在业务上，注册的时候，需要判断某个用户是否已经存在，账号密码是否正确等操作
    public User get(String name){
        User user = null;
        String sql ="select id,name,password from User where name =?;";
        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps= connection.prepareStatement(sql);) {


            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setPassword(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  user;
    }

    /**
     * 以boolean形式返回某个用户名是否已经存在
     * @param name
     * @return
     */
    public boolean isExist(String name){
        return get(name)==null?false:true;
    }

    /**
     * 根据账户和密码获取对象，这才是合理的判断账号密码是否正确的方式，而不是以下把所有的用户信息查出来，在内存中进行比较
     * @param name
     * @param password
     * @return
     */
    public User get(String name,String password){
        User user = null;
        String sql ="select id,name,password from User where name =? and password=?;";

        try (  Connection connection = DBUtil.getConnection();
               PreparedStatement ps = connection.prepareStatement(sql);){

            ps.setString(1,name);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setPassword(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  user;
    }

    public static void main(String[] args) {
        UserDAO userDao = new UserDAO();
        System.out.println(userDao.get("yanghui","111"));
        System.out.println(userDao.get("yanghui1","111"));

    }





}
