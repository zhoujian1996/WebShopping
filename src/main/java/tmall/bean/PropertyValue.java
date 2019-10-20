package tmall.bean;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 15:32
 */
public class PropertyValue {



    private  int id;
    private String value;
    private Product product;
    private  Property property;

    @Override
    public String toString() {
        return "PropertyValue{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", product=" + product +
                ", property=" + property +
                '}';
    }

    public PropertyValue() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
