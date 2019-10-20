package tmall.servlet;

import javax.servlet.annotation.WebServlet;

/**
 * @author:zhoujian
 * @date:2019/10/13 0013 15:09
 */
@WebServlet(value="/categoryServlet01")
public class CategoryServlet01 extends  TestServlet01 {
    @Override
    public void print() {
        System.out.println("categoryServlet01");
    }
}
