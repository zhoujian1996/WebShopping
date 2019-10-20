package tmall.bean;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 15:29
 *
 */
public class ProductImage {

    private int id;
    private String type;

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", product=" + product +
                '}';
    }

    private Product product;//与Product的多对一关系

    public ProductImage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
