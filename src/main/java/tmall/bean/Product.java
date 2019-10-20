package tmall.bean;

import java.util.Date;
import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 15:10
 * 产品表
 */
public class Product {

    private int id;
    private String name;
    private String subTitle;

    private float promotePrice;
    private float orignalPrice;

    public float getOrignalPrice() {
        return orignalPrice;
    }

    public void setOrignalPrice(float orignalPrice) {
        this.orignalPrice = orignalPrice;
    }

    private int stock;
    private Category category;
    private Date createDate;

    private ProductImage firstProductImage;//是从productSingleImages集合中取出第一个来，用于显示这个产品的默认图片
    private List<ProductImage> productImages;
    private List<ProductImage> productSingleImages;//单个产品图片集合
    private List<ProductImage> productDetailImages;//详情产品集合
    private int reviewCount; //表示评价数量
    private int saleCount;//销售数量

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", originalPrice=" + orignalPrice +
                ", promotePrice=" + promotePrice +
                ", stock=" + stock +
                ", category=" + category +
                ", createDate=" + createDate +
                ", firstProductImage=" + firstProductImage +
                ", productImages=" + productImages +
                ", productSingleImages=" + productSingleImages +
                ", productDetailImages=" + productDetailImages +
                ", reviewCount=" + reviewCount +
                ", saleCount=" + saleCount +
                '}';
    }



    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }




    public float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ProductImage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<ProductImage> getProductSingleImages() {
        return productSingleImages;
    }

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }
}
