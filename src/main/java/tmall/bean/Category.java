package tmall.bean;

import java.util.List;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 14:58
 */
public class Category {

    private String name;
    private int id;
    List<Product> products;
    List<List<Product>> productsByRow;//一个分类对应多个List<Product>，提供这个属性，是为了在首页竖状导航的分类名称右边显示产品列表

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }


    public Category() {
    }


    public Category(String name, int id, List<Product> products, List<List<Product>> productsByRow) {
        this.name = name;
        this.id = id;
        this.products = products;
        this.productsByRow = productsByRow;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
