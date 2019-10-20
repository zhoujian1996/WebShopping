package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
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
 * @date:2019/10/14 0014 18:27
 */
@WebServlet(value="/productImageServlet")
public class ProductImageServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {

        Map<String,String> params = new HashMap<>();
        InputStream is = super.parseUpload(request,params);


        String type = params.get("type");
        int pid = Integer.parseInt(params.get("pid"));
        Product product = productDAO.get(pid);

        //根据上传的参数生成productImage对象
        ProductImage pi = new ProductImage();
        pi.setType(type);
        pi.setProduct(product);
        productImageDAO.add(pi);


        //生成文件
        String fileName = pi.getId()+".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;

        if(type.equals(ProductImageDAO.type_single)){

             imageFolder = request.getSession().getServletContext().getRealPath("img/productSingle");
             imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
             imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");

        }else{
            imageFolder = request.getSession().getServletContext().getRealPath("img/productDetail");

        }

        //待保存图像的名称
        File file = new File(imageFolder,fileName);
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

                if(productImageDAO.equals(ProductImageDAO.type_single)){
                    File f_small = new File(imageFolder_small,fileName);
                    File f_middle = new File(imageFolder_middle,fileName);

                    ImageUtil.resizeImage(file,56,56,f_small);
                    ImageUtil.resizeImage(file,217,198,f_middle);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return "@admin_productImage_list?pid="+pid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {

        int imgId = Integer.parseInt(request.getParameter("id"));
        ProductImage productImage = productImageDAO.get(imgId);
        int pid = productImage.getProduct().getId();
        productImageDAO.delete(imgId);
        return "@admin_productImage_list?pid="+pid;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {

        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productDAO.get(pid);
        List<ProductImage> pisSingle = productImageDAO.list(product, ProductImageDAO.type_single,page.getStart(),page.getCount());
        List<ProductImage> pisDetail = productImageDAO.list( product,ProductImageDAO.type_detail,page.getStart(),page.getCount());

        request.setAttribute("pisSingle",pisSingle);
        request.setAttribute("pisDetail",pisDetail);
        request.setAttribute("p",product);
        request.setAttribute("page",page);


        return "admin/listProductImage.jsp";
    }
}
