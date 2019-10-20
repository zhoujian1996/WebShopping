package tmall.dao;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/10 0010 14:56
 */
public class ReviewDAO {
    /**
     *
     * @return
     * 获得Category的所有记录
     */
    public int getTotal(){
        int total = 0;
        String sql = "select count(*) from review";
        try (  Connection connection = DBUtil.getConnection();
               Statement statement =connection.createStatement();){


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
     *
     * @param pid
     * @return
     * 某条产品对应的评论数
     */
    public int getTotal(int pid){
        int total = 0;
        String sql = "select count(*) from review where pid=?";

        try( Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);) {



            preparedStatement.setInt(1,pid);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  total;
    }

    public void add(Review review){
        String sql = "insert into review(id,content,uid,pid,createDate) values (null,?,?,?,?); ";

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

            ps.setString(1,review.getContent());
            ps.setInt(2,review.getUser().getId());
            ps.setInt(3,review.getProduct().getId());
            ps.setTimestamp(4, DateUtil.d2t(review.getCreateDate()));
            ps.execute();

            ResultSet rs =  ps.getGeneratedKeys();//获得getGeneratedKeys
            if(rs.next()){
                int id = rs.getInt(1);
               review.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Review review){

        String sql ="update review set content=?,pid=?,uid=? where id =?;";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps  = connection.prepareStatement(sql);) {


            ps.setString(1,review.getContent());
            ps.setInt(2,review.getProduct().getId());
            ps.setInt(3,review.getUser().getId());
            ps.setInt(4,review.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id){
        String sql ="delete from review where id =?;";
        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Review get(int id){
        Review review = null;
        String sql ="select id,content,pid,uid,createdate from review where id =?;";

        try(   Connection connection = DBUtil.getConnection();
               PreparedStatement   ps = connection.prepareStatement(sql);) {


            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                 review = new Review();
                review.setId(id);
                review.setContent(rs.getString(2));
                Product product = new Product();
                product.setId(rs.getInt(3));
                review.setProduct(product);

                User user = new User();
                user.setId(rs.getInt(4));
                review.setUser(user);

                review.setCreateDate(DateUtil.t2d(rs.getTimestamp(5)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  review;
    }

    /**
     *
     * @param pid
     * @return
     * 获取某个产品的评论表
     */
    public List<Review >list(int pid){
        List<Review> reviews = new ArrayList<>();
        String sql ="select id,content,pid,uid,createdate from review where pid =?;";

        try(   Connection connection = DBUtil.getConnection();
               PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1,pid);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Review review = new Review();
                review.setId(rs.getInt(1));
                review.setContent(rs.getString(2));
                Product product = new Product();
                product.setId(rs.getInt(3));
                review.setProduct(product);

                User user = new User();
                user.setId(rs.getInt(4));
                review.setUser(user);

                review.setCreateDate(DateUtil.t2d(rs.getTimestamp(5)));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  reviews;
    }

    public List<Review> list(int pid,int start, int count){
        List<Review> reviews = new ArrayList<>();
        String sql ="select id,content,pid,uid,createdate from review where pid =? limit ?,?;";

        try(   Connection connection = DBUtil.getConnection();
               PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1,pid);
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,count);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Review review = new Review();
                review.setId(rs.getInt(1));
                review.setContent(rs.getString(2));
                Product product = new Product();
                product.setId(rs.getInt(3));
                review.setProduct(product);

                User user = new User();
                user.setId(rs.getInt(4));
                review.setUser(user);

                review.setCreateDate(DateUtil.t2d(rs.getTimestamp(5)));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  reviews;
    }

    public static void main(String[] args) {
        ReviewDAO reviewDao = new ReviewDAO();
//        Review review = new Review();
//        Product product = new Product();
//        product.setId(1);
//        review.setContent("我爱你");
//        review.setProduct(product);
//        User user = new User();
//        user.setId(2);
//        review.setUser(user);
//        review.setId(1);
//        review.setCreateDate(new Date());
        System.out.println(reviewDao.list(1,1,1));

//
//        reviewDao.add(review);
//        Product product = new Product();
//        product.setId(1);
//        System.out.println(reviewDao.getTotal(1));



    }




}
