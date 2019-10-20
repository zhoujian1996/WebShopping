package tmall.bean;

import java.util.Date;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 15:35
 *
 */
public class Review {


    private  int id;

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", product=" + product +
                ", createDate=" + createDate +
                '}';
    }

    private  String content;
    private  User user;
    private Product product;
    private Date createDate;

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
