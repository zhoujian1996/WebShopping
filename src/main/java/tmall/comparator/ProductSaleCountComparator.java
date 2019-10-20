package tmall.comparator;

import tmall.bean.Product;

import java.util.Comparator;

/**
 * @author:zhoujian
 * @date:2019/10/18 0018 17:03
 */
public class ProductSaleCountComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return (int)(o1.getSaleCount()-o2.getSaleCount());
    }
}
