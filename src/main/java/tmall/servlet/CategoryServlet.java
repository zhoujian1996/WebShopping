package tmall.servlet;


import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:zhoujian
 * @date:2019/10/13 0013 15:11
 */
@WebServlet(value="/categoryServlet")
public class CategoryServlet extends  BaseBackServlet {

    /**
     * 新添Category
     * 向数据库中插入记录
     * 保存上传的新图像
     * @param request
     * @param response
     * @param page
     * @return
     */
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {

        Map<String,String> params = new HashMap<>();
        InputStream is = super.parseUpload(request,params);

        //获取Category的名字
        String categoryName = params.get("name");
        Category category = new Category();
        category.setName(categoryName);
        categoryDAO.add(category); //项数据库中添加数据
        //上传文件待保存路径

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        //待保存图像的名称
        File file = new File(imageFolder, category.getId()+".jpg");

        try{

            if(is!=null&&0!=is.available()){
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024*1024];
                int lenght = 0;
                while(-1!=(lenght=is.read(b))){
                    fos.write(b,0,lenght);
                }
                fos.flush();

                //通过下列代码把文件保存为jpg合适
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img,"jpg",file);
            }

        }catch(Exception e){
            e.printStackTrace();
        }



        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {

        String id = request.getParameter("id");
        categoryDAO.delete(new Integer(id));
        return "@admin_category_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {

        int id = Integer.parseInt(request.getParameter("id"));
        Category c = new Category();
        c.setId(id);

        request.setAttribute("c",c);

        return "admin/editCategory.jsp";

    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {

        Map<String,String> params = new HashMap<>();
        InputStream is = super.parseUpload(request,params);

        //获取Category的名字
        String categoryName = params.get("name");
        System.out.println(params.get("id"));
        int id = new Integer(params.get("id"));

        Category category = new Category();
        category.setId(id);
        category.setName(categoryName);
        categoryDAO.update(category); //项数据库中添加数据
        //上传文件待保存路径

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        //待保存图像的名称
        File file = new File(imageFolder, category.getId()+".jpg");

        try{

            if(is!=null&&0!=is.available()){
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024*1024];
                int lenght = 0;
                while(-1!=(lenght=is.read(b))){
                    fos.write(b,0,lenght);
                }
                fos.flush();

                //通过下列代码把文件保存为jpg合适
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img,"jpg",file);
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        return  "@admin/listCategory.jsp";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {

        /**
         * 查询category中类数
         */
        List<Category> cs = categoryDAO.list(page.getStart(),page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("thecs",cs);
        request.setAttribute("page",page);

        return  "admin/listCategory.jsp";



    }
}
