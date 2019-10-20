package tmall.servlet;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author:zhoujian
 * @date:2019/10/11 0011 15:54
 */
public abstract class TestServlet01 extends HttpServlet {
    public abstract void print();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Method method = this.getClass().getMethod("print");
            method.invoke(this);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();

        }

        System.out.println(123);
        resp.sendRedirect("index.jsp");

    }
}
