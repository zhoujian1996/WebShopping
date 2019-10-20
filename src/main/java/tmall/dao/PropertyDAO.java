package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 18:02
 */
public class PropertyDAO {


    public int getTotal(){
        int total = 0;
        String sql = "select count(*) from Property";

        try(        Connection connection = DBUtil.getConnection();
                    Statement statement =connection.createStatement();

        ) {
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  total;
    }

    public void add(Property property){
        String sql = "insert into Property(id,cid,name) values (null,?,?); ";

        try(            Connection connection = DBUtil.getConnection();
                        PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

        ) {
            ps.setInt(1,property.getCategory().getId());
            ps.setString(2,property.getName());
            ps.execute();

            ResultSet rs =  ps.getGeneratedKeys();//获得getGeneratedKeys
            if(rs.next()){
                int id = rs.getInt(1);
                property.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Property property){

        String sql ="update Property set name=?,cid=? where id =?;";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setString(1,property.getName());
            ps.setInt(2,property.getCategory().getId());
            ps.setInt(3,property.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id){
        String sql ="delete from  Property where id =?;";
        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Property get(int id){
        Property property = null;
        String sql ="select id,cid,name from Property where id =?;";
        try(  Connection connection = DBUtil.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                property = new Property();
                property.setId(rs.getInt(1));
                property.setName(rs.getString(3));
                Category category = new Category();
                category.setId(rs.getInt(2));
                property.setCategory(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  property;
    }

    /**
     * 查询所有属性
     * @return
     */
    public List<Property>list(){
        List<Property> properties = new ArrayList<>();
        String sql ="select id,cid,name from Property ;";

        try(  Connection connection = DBUtil.getConnection();
              Statement statement = connection.createStatement();) {

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                Property property = new Property();
                property.setId(rs.getInt(1));
                property.setName(rs.getString(3));
                Category category = new Category();
                category.setId(rs.getInt(2));
                property.setCategory(category);
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  properties;
    }

    /**
     * 查询所有属性 按照（start-count)
     * @param start
     * @param count
     * @return
     */
    public List<Property> list(int start,int count){
        List<Property> properties = new ArrayList<>();
        String sql ="select id,cid,name from Property limit ?,?;";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Property property = new Property();
                property.setId(rs.getInt(1));
                property.setName(rs.getString(3));
                Category category = new Category();
                category.setId(rs.getInt(2));
                property.setCategory(category);
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  properties;
    }


    /**
     * 查询某个分类下的属性总数，在分页显示的时候会用到
     * @param cid
     * @return
     */
    public int getTotal(int cid){
        int total = 0;
        String sql = "select count(*) from Property where cid=?";

        try(  Connection connection = DBUtil.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1,cid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  total;

    }

    /**
     * 查询某个分类下的属性对象
     * @param cid
     */
    public List<Property> list(int cid){
        String sql ="select id,cid,name from Property where cid=?;";

        List<Property> properties = new ArrayList<>();
        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1,cid);
            ResultSet rs =  ps.executeQuery();

            while (rs.next()){
                Property property = new Property();
                property.setId(rs.getInt(1));
                property.setName(rs.getString(3));
                Category category = new Category();
                category.setId(rs.getInt(2));
                property.setCategory(category);
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  properties;

    }

    /**
     * 查询某个分类下的属性对象
     * @param cid
     * @param start
     * @param count
     * @return
     */
    public List<Property> list(int cid,int start,int count){

        List<Property> properties = new ArrayList<>();
        String sql ="select id,cid,name from Property where cid=? limit ?,? ;";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(2,start);
            ps.setInt(3,count);
            ps.setInt(1,cid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Property property = new Property();
                property.setId(rs.getInt(1));
                property.setName(rs.getString(3));
                Category category = new Category();
                category.setId(rs.getInt(2));
                property.setCategory(category);
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  properties;
    }




    public static void main(String[] args) {

        PropertyDAO propertyDao = new PropertyDAO();

//        System.out.println(propertyDao.list(6,1,2));
//
//        Category category = new Category();
//        category.setId(6);
//
//        Property property = new Property();
//        property.setId(1);
//        property.setName("与毛球");
//        property.setCategory(category);
//
//        propertyDao.update(property);

//        System.out.println(ca);
//
//
//        propertyDao.add(property);

//        System.out.println(propertyDao.getTotal());

//        propertyDao.update(property);


//        System.out.println(propertyDao.list(0,1));


    }




}
