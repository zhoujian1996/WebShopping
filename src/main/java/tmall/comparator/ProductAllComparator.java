package tmall.comparator;

import tmall.bean.Product;

import java.util.Comparator;

/**
 * @author:zhoujian
 * @date:2019/10/18 0018 16:58
 */
public class ProductAllComparator implements Comparator<Product> {


    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount()*o2.getSaleCount()-o1.getReviewCount()*o1.getSaleCount();
    }
}
