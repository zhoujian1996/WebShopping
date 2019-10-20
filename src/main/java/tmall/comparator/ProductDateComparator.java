package tmall.comparator;

import tmall.bean.Product;

import java.util.Comparator;

/**
 * @author:zhoujian
 * @date:2019/10/18 0018 17:00
 */
public class ProductDateComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return o1.getCreateDate().compareTo(
                o2.getCreateDate()
        );
    }
}
