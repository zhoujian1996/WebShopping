package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/10 0010 19:31
 */
public class ProductDAO {


    /**
     *
     * @return
     * 获得Category的所有记录
     */
    public int getTotal(){
        int total = 0;
        String sql = "select count(*) from product";

        try(  Connection connection = DBUtil.getConnection();
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

    public void add(Product product){
        String sql = "insert into product values (null,?,?,?,?,?,?,?); ";

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

            ps.setString(1,product.getName());
            ps.setString(2,product.getSubTitle());
            ps.setFloat(3,product.getOrignalPrice());
            ps.setFloat(4,product.getPromotePrice());
            ps.setInt(5,product.getStock());
            ps.setInt(6,product.getCategory().getId());
            ps.setTimestamp(7, DateUtil.d2t(product.getCreateDate()));

            ps.execute();

            ResultSet rs =  ps.getGeneratedKeys();//获得getGeneratedKeys
            if(rs.next()){
                int id = rs.getInt(1);
                product.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Product product){

        String sql ="update Product set name=?,subtitle=?,originalprice=?,promotePrice=?,stock=?,cid=?,createdate=? where id =?;";

        try( Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setString(1,product.getName());
            ps.setString(2,product.getSubTitle());
            ps.setFloat(3,product.getOrignalPrice());
            ps.setFloat(4,product.getPromotePrice());
            ps.setInt(5,product.getStock());
            ps.setInt(6,product.getCategory().getId());
            ps.setTimestamp(7,DateUtil.d2t(product.getCreateDate()));
            ps.setInt(8,product.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id){
        String sql ="delete from  product where id =?;";

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product get(int id){
        Product product = null;
        String sql ="select * from Product where id =?;";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps= connection.prepareStatement(sql);){

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setSubTitle(rs.getString("subtitle"));
                product.setOrignalPrice(rs.getFloat("originalPrice"));
                product.setPromotePrice(rs.getFloat("PROMOTEpRICE"));
                product.setStock(rs.getInt("stock"));
                product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));

                setFirstProductImage(product);
                setProductDetailImages(product);
                setProductImages(product);
                setSaleAndReviewNumber(product);



                int cid = rs.getInt("cid");
                Category category = new CategoryDAO().get(cid);
                product.setCategory(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  product;
    }

    public List<Product> list(int cid){
        List<Product> products = new ArrayList<>();
        products = list(cid,0,Short.MAX_VALUE);
        return  products;
    }

    public List<Product> list(int cid,int start,int count){
        List<Product> products = new ArrayList<>();
        String sql ="select * from Product where cid=? limit ?,?;";

        try( Connection connection = DBUtil.getConnection();

             PreparedStatement ps = connection.prepareStatement(sql);) {

            ps.setInt(1,cid);
            ps.setInt(2,start);
            ps.setInt(3,count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setSubTitle(rs.getString("subtitle"));
                product.setOrignalPrice(rs.getFloat("originalPrice"));
                product.setPromotePrice(rs.getFloat("PROMOTEpRICE"));
                product.setStock(rs.getInt("stock"));
                product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                setFirstProductImage(product);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  products;
    }

    public List<Product> list(int start,int count){
        List<Product> products = new ArrayList<>();
        String sql ="select * from Product limit ?,?;";

        try( Connection connection = DBUtil.getConnection();

             PreparedStatement ps = connection.prepareStatement(sql);) {


            ps.setInt(1,start);
            ps.setInt(2,count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setSubTitle(rs.getString("subtitle"));
                product.setOrignalPrice(rs.getFloat("originalPrice"));
                product.setPromotePrice(rs.getFloat("PROMOTEpRICE"));
                product.setStock(rs.getInt("stock"));
                product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  products;
    }

    public List<Product> list(){
        List<Product> products = new ArrayList<>();
        products = list(0,Short.MAX_VALUE);
        return  products;
    }


    /**
     * 获取某种分类下商品的数量
     * @param cid
     * @return
     */
    public int getTotal(int cid){
        int totalNumber = 0;
        String sql ="select count(*)  from Product where cid=?;";

        try( Connection connection = DBUtil.getConnection();

             PreparedStatement ps = connection.prepareStatement(sql);
        ) {

            ps.setInt(1,cid);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
              totalNumber = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  totalNumber;

    }


    /**
     * 为分类填称产品集合
     * @param c
     */
    public void fill(Category c){
            List<Product> products = list(c.getId());
            c.setProducts(products);
    }

    public void fill(List<Category> cs){
        for(Category category:cs){
            fill(category);
        }
    }

    /**
     * 为多个分类设置ProductsByRow属性
     * @param cs
     */
    public void fillByRow(List<Category> cs){
        int productNumberEachRow = 8;
         for(Category category:cs){
             List<List<Product>> productsByRow = new ArrayList<>();
             List<Product> products = list(category.getId());
             for(int i=0;i<products.size();i+=productNumberEachRow){
                 int size = i+productNumberEachRow;
                 size = size>products.size()?products.size():size;
                 List<Product> productsOfEachRow = products.subList(i,size);
                 productsByRow.add(productsOfEachRow);

             }
             category.setProductsByRow(productsByRow);
         }
    }


    /**
     * 根据关键字查询商品
     * @param keyword
     * @param start
     * @param count
     * @return
     */
    public List<Product>  search(String keyword,int start,int count){

        List<Product> products = new ArrayList<>();
        String sql ="select * from Product where name like ? limit ?,?;";

        if((null==keyword)||(keyword.trim().length()==0)){
            return  products;
        }else{

            try(Connection connection = DBUtil.getConnection();

                PreparedStatement ps = connection.prepareStatement(sql);) {


                ps.setString(1,"%"+keyword.trim()+"%");
                ps.setInt(2,start);
                ps.setInt(3,count);
                ResultSet rs = ps.executeQuery();

                while (rs.next()){
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setSubTitle(rs.getString("subtitle"));
                    product.setOrignalPrice(rs.getFloat("originalPrice"));
                    product.setPromotePrice(rs.getFloat("PROMOTEpRICE"));
                    product.setStock(rs.getInt("stock"));
                    product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));

                    setFirstProductImage(product);
                    setProductDetailImages(product);
                    setProductImages(product);
                    setSaleAndReviewNumber(product);

                    products.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return  products;


        }


    }
    public List<Product>  search(String keyword){

        List<Product> products = new ArrayList<>();
        String sql ="select * from Product where name like ? ;";

        if((null==keyword)||(keyword.trim().length()==0)){
            return  products;
        }else{

            try(Connection connection = DBUtil.getConnection();

                PreparedStatement ps = connection.prepareStatement(sql);
            ) {

                ps.setString(1,"%"+keyword.trim()+"%");

                ResultSet rs = ps.executeQuery();

                while (rs.next()){
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setSubTitle(rs.getString("subtitle"));
                    product.setOrignalPrice(rs.getFloat("originalPrice"));
                    product.setPromotePrice(rs.getFloat("PROMOTEpRICE"));
                    product.setStock(rs.getInt("stock"));
                    product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));


                    setFirstProductImage(product);
                    setProductDetailImages(product);
                    setProductImages(product);
                    setSaleAndReviewNumber(product);

                    products.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return  products;


        }


    }


    /**
     * 一个产品有多个图片，但是只有一个主图片，把第一张图片设置为主图片
     * @param p
     */
    public void setFirstProductImage(Product p){
        List<ProductImage> productImages = new ProductImageDAO().list(p, ProductImageDAO.type_single);
        if(!productImages.isEmpty()){
            p.setFirstProductImage(productImages.get(0));
        }
    }

    public void setProductDetailImages(Product product){
        List<ProductImage> productImages = new ProductImageDAO().list(product, ProductImageDAO.type_detail);
        if(!productImages.isEmpty()){
          product.setProductDetailImages(productImages);
        }
    }

    public void setProductImages(Product product){
        List<ProductImage> productImages = new ProductImageDAO().list(product, ProductImageDAO.type_single);
        if(!productImages.isEmpty()){
            product.setProductSingleImages(productImages);
        }
    }

    /**
     * 为产品设置销量和评价数量
     * @param product
     * 注意这个方法均需要用到其他表查询
     */
    public void setSaleAndReviewNumber(Product product){
        int saleCount = new OrderItemDAO().getSaleCount(product.getId());//利用orderItem表查询Product的销量数
        product.setSaleCount(saleCount);

        int reviewCount = new ReviewDAO().getTotal(product.getId());
        product.setReviewCount(reviewCount);

    }




    public void setSaleAndReviewNumber(List<Product> products){
        for(Product product:products) {

            setSaleAndReviewNumber(product);
        }
    }

    public static void main(String[] args) {

        ProductDAO productDao = new ProductDAO();
        Product product = new Product();
        product.setId(900);

        System.out.println(new ProductImageDAO().list(product,ProductImageDAO.type_single));


    }



}
