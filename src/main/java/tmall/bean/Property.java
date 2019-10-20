package tmall.bean;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 15:08
 * 属性表
 */
public class Property {

    private int id;
    private String name;
    private  Category category;

    public Property() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
