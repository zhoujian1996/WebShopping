package tmall.dao;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 18:47
 */
public class ProductImageDAO {

    public static final String type_single = "type_single";//单个图片
    public static final String type_detail = "type_detail";//详情图片

    /**
     *
     * @return
     * 获得Category的所有记录
     */
    public int getTotal(){
        String sql = "select count(*) from productimage";
        int total = 0;
        try(            Connection connection = DBUtil.getConnection(); Statement statement =connection.createStatement();
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

    public void add(ProductImage productImage){
        String sql = "insert into productimage(id,pid,type) values (null,?,?); ";

        try(   Connection connection = DBUtil.getConnection();
               PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

            ps.setInt(1,productImage.getProduct().getId());
            ps.setString(2,productImage.getType());
            ps.execute();

            ResultSet rs =  ps.getGeneratedKeys();//获得getGeneratedKeys
            if(rs.next()){
                int id = rs.getInt(1);
                productImage.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(ProductImage productImage){

        String sql ="update productimage set pid=? ,type=? where id =?;";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setInt(1,productImage.getProduct().getId());
            ps.setString(2,productImage.getType());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id){
        String sql ="delete from  productimage where id =?;";

        try(  Connection connection = DBUtil.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProductImage get(int id){
        ProductImage productImage = null;
        String sql ="select id,pid,type from ProductImage where id =?;";

        try ( Connection connection = DBUtil.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql);){

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                productImage= new ProductImage();
                 productImage.setId(rs.getInt(1));
                Product product = new Product();
                product.setId(rs.getInt(2));
                productImage.setProduct(product);
                productImage.setType(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  productImage;
    }

    public List<ProductImage>list(){
        String sql ="select id,pid,type from ProductImage;";
        List<ProductImage>productImages = new ArrayList<>();
        try( Connection connection = DBUtil.getConnection();
             Statement statement = connection.createStatement();) {


            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                ProductImage productImage= new ProductImage();
                productImage.setId(rs.getInt(1));
                Product product = new Product();
                product.setId(rs.getInt(2));
                productImage.setProduct(product);
                productImage.setType(rs.getString(3));
                productImages.add(productImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  productImages;
    }


    public List<ProductImage> list(String type,int start, int count){
        List<ProductImage> productImages = new ArrayList<>();
        String sql ="select id,pid,type from productimage where  type =? limit ?,?;";

        try(            Connection connection = DBUtil.getConnection();
        ) {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1,type);
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                ProductImage productImage= new ProductImage();
                productImage.setId(rs.getInt(1));
                Product product = new Product();
                product.setId(rs.getInt("pid"));
                productImage.setProduct(product);
                productImage.setType(rs.getString(3));
                productImages.add(productImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  productImages;
    }

    public List<ProductImage> list(Product product,String type){
        return  list(product,type,0,Short.MAX_VALUE);
    }

    public List<ProductImage> list(Product product,String type,int start, int count){
        List<ProductImage> productImages = new ArrayList<>();
        String sql ="select id,pid,type from productimage where pid=? and type =? limit ?,?;";

        try( Connection connection = DBUtil.getConnection();) {


            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,product.getId());
            ps.setString(2,type);
            ps.setInt(3,start);
            ps.setInt(4,count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                ProductImage productImage= new ProductImage();
                productImage.setId(rs.getInt(1));
                productImage.setProduct(product);
                productImage.setType(rs.getString(3));
                productImages.add(productImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  productImages;
    }

    public static void main(String[] args) {
        ProductImageDAO productImageDao = new ProductImageDAO();

//
//        ProductImage productImage = new ProductImage();
//        productImage.setType(type_detail);
//        Product product = new Product();
//        product.setId(2);
//        productImage.setProduct(product);
//
//        productImageDao.add(productImage);

//        System.out.println(productImageDao.getTotal());
        Product product = new Product();
        product.setId(2);
         System.out.println(productImageDao.list(product,type_single));



    }



}
