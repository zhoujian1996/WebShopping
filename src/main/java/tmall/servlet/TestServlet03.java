package tmall.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author:zhoujian
 * @date:2019/10/13 0013 14:39
 */
public class TestServlet03 extends TestServlet01{


    @Override
    public void print() {
        System.out.println("test testServl");
    }
}
