package tmall.dao;

import tmall.bean.Category;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 16:14
 * CategoryDao用于建立对于Category对象的ORM映射
 */
public class CategoryDAO {


    /**
     *
     * @return
     * 获得Category的所有记录
     */
    public int getTotal(){
        int total = 0;
        try(Connection connection = DBUtil.getConnection(); Statement statement =connection.createStatement();) {
            String sql = "select count(*) from Category";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

        }
        return  total;
    }

    public void add(Category category){

        try( Connection connection = DBUtil.getConnection();) {

            String sql = "insert into Category values (null,?); ";
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,category.getName());
            ps.execute();

            ResultSet rs =  ps.getGeneratedKeys();//获得getGeneratedKeys
            if(rs.next()){
                int id = rs.getInt(1);
                category.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Category category){


        try( Connection connection = DBUtil.getConnection();) {
            String sql ="update Category set name=? where id =?;";


            PreparedStatement ps = null;
            ps = connection.prepareStatement(sql);
            ps.setString(1,category.getName());
            ps.setInt(2,category.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id){
        String sql ="delete from  Category where id =?;";
        try( Connection connection = DBUtil.getConnection(); PreparedStatement ps =  connection.prepareStatement(sql);) {
            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Category get(int id){
        Category category = null;
        String sql ="select id,name from Category where id =?;";
        try(Connection connection = DBUtil.getConnection(); PreparedStatement ps =connection.prepareStatement(sql);) {

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  category;
    }

    public List<Category>list(){
        List<Category> categorys = new ArrayList<>();
        String sql ="select id,name from Category ;";
        try ( Connection connection = DBUtil.getConnection();
              Statement statement = connection.createStatement();){


            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                Category category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                categorys.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  categorys;
    }

    public List<Category> list(int start,int count){
        List<Category> categorys = new ArrayList<>();
        String sql ="select id,name from Category limit ?,?;";
        try(  Connection connection = DBUtil.getConnection();

              PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Category category = new Category();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                categorys.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  categorys;
    }


    public static void main(String[] args) {
        CategoryDAO categoryDao = new CategoryDAO();

        Category category = new Category();
        category.setName("体育");

        categoryDao.add(category);

//        System.out.println(categoryDao.getTotal());

//        Category category = new Category();
//        category.setId(3);
//        category.setName("啊是VS东方VS的VS的");
//        categoryDao.update(category);

//        categoryDao.delete(3);

//        Category category = categoryDao.get(4);
//        System.out.println(category);

        List<Category> categories = categoryDao.list(1,1);
        System.out.println(categories);

    }




}
