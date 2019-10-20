package tmall.dao;

import com.sun.org.apache.regexp.internal.RE;
import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/10 0010 14:17
 */
public class PropertyValueDAO {

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
            String sql = "select count(*) from propertyvalue";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  total;
    }

    public void add(PropertyValue propertyValue){
        String sql = "insert into PropertyValue(id,pid,ptid,value) values (null,?,?,?); ";

        try (            Connection connection = DBUtil.getConnection();
                         PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

        ){
            ps.setInt(1,propertyValue.getProduct().getId());
            ps.setInt(2,propertyValue.getProperty().getId());
            ps.setString(3,propertyValue.getValue());
            ps.execute();

            ResultSet rs =  ps.getGeneratedKeys();//获得getGeneratedKeys
            if(rs.next()){
                int id = rs.getInt(1);
               propertyValue.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(PropertyValue propertyValue){

        String sql ="update propertyvalue set value=?,pid=?,ptid=? where id =?;";

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setString(1,propertyValue.getValue());
            ps.setInt(2,propertyValue.getProduct().getId());
            ps.setInt(3,propertyValue.getProperty().getId());
            ps.setInt(4,propertyValue.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id){
        String sql ="delete from  propertyvalue where id =?;";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PropertyValue get(int id){
       PropertyValue propertyValue = null;
        String sql ="select id,pid,ptid,value from propertyValue where id =?;";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                propertyValue= new PropertyValue();
                propertyValue.setId(id);
                Property property = new Property();
                property.setId(rs.getInt(3));
                propertyValue.setProperty(property);
                Product product =new Product();
                product.setId(rs.getInt(2));
                propertyValue.setProduct(product);
                propertyValue.setValue(rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  propertyValue;
    }

    /**
     *
     * @param pid
     * @return
     * 查询某个产品下所有属性值
     */
    public List<PropertyValue> list(int pid){
        List<PropertyValue> propertyValues = new ArrayList<>();
        String sql ="select id,pid,ptid,value from propertyValue where pid=?;";

        try ( Connection connection = DBUtil.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql);){



            ps.setInt(1,pid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                PropertyValue propertyValue= new PropertyValue();
                propertyValue.setId(rs.getInt(1));

                int ptid =rs.getInt(3);
                Property property = new PropertyDAO().get(ptid);
                propertyValue.setProperty(property);
                Product product =new Product();
                product.setId(rs.getInt(2));
                propertyValue.setProduct(product);
                propertyValue.setValue(rs.getString(4));

                propertyValues.add(propertyValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  propertyValues;
    }



    public PropertyValue get(int ptid,int pid){
        PropertyValue propertyValue = null;
        String sql ="select id,pid,ptid,value from propertyValue where ptid =? and pid=?;";

        try ( Connection connection = DBUtil.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql);){

            ps.setInt(1,ptid);
            ps.setInt(2,pid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                propertyValue= new PropertyValue();
                propertyValue.setId(rs.getInt(1));
                Property property = new Property();
                property.setId(rs.getInt(3));
                propertyValue.setProperty(property);
                Product product =new Product();
                product.setId(rs.getInt(2));
                propertyValue.setProduct(product);
                propertyValue.setValue(rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  propertyValue;
    }




    public List<PropertyValue>list(){
        List<PropertyValue> propertyValues = new ArrayList<>();
        String sql ="select id,pid,ptid,value from PropertyValue;";

        try(Connection connection = DBUtil.getConnection();
            Statement statement = connection.createStatement();) {

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                PropertyValue propertyValue= new PropertyValue();
                propertyValue.setId(rs.getInt(1));
                int ptid = rs.getInt(3);
                Property property = new PropertyDAO().get(ptid);
                propertyValue.setProperty(property);
                System.out.println(property);
                Product product =new Product();
                product.setId(rs.getInt(2));
                propertyValue.setProduct(product);
                propertyValue.setValue(rs.getString(4));
                propertyValues.add(propertyValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  propertyValues;
    }

    public List<PropertyValue> list(int start, int count){
        List<PropertyValue> propertyValues = new ArrayList<>();
        String sql ="select id,pid,ptid,value from PropertyValue limit ?,?;";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1,start);
            preparedStatement.setInt(2,count);
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()){
                PropertyValue propertyValue= new PropertyValue();
                propertyValue.setId(rs.getInt(1));
                Property property = new Property();
                property.setId(rs.getInt(3));
                propertyValue.setProperty(property);
                Product product =new Product();
                product.setId(rs.getInt(2));
                propertyValue.setProduct(product);
                propertyValue.setValue(rs.getString(4));
                propertyValues.add(propertyValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  propertyValues;
    }


    /**
     *
     * @param product
     * 初始化某个产品对应的属性值：
     *      1、根据分类获取所有属性
     *      2、遍历每一个属性
     *      3、根据属性和产品获取属性值
     *      4、如果属性值不存在就创建一个属性值队形
     *
     */
    public void init(Product product){

            Category category = product.getCategory();
            List<Property> propertys = new PropertyDAO().list(category.getId());

            for(Property property:propertys){
                PropertyValue propertyValue = get(property.getId(),product.getId());
                if(null==propertyValue){
                    propertyValue = new PropertyValue();
                    propertyValue.setProduct(product);
                    propertyValue.setProperty(property);
                    add(propertyValue);
                }
            }

    }


    public static void main(String[] args) {
        PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
        List<PropertyValue> propertyValues = propertyValueDAO.list(42);
        for(PropertyValue propertyValue:propertyValues){
            System.out.println(propertyValue);
        }
    }


}
