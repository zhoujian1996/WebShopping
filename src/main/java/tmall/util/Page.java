package tmall.util;

/**
 * @author:zhoujian
 * @date:2019/10/11 0011 14:59
 */
public class Page {

    int start;
    int count;
    int total;
    String param;


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Page(int start, int count, int total, String param) {
        this.start = start;
        this.count = count;
        this.total = total;
        this.param = param;
    }

    public Page() {
    }

    public boolean isHasPreviouse(){

        if(start==0)
          return false;
        return  true;
    }

    public boolean isHasNext(){
        if(start==getLast())
            return  false;
        return  true;
    }

    /**
     * 获取分页的页数
     * @return
     */
    public int getTotalPage(){
        int totalPage;
        //假设总数是50，是能够被5整除的，那就是10页
        if(0==total%count){
            totalPage = total/count;
        }else{
            totalPage = total/count+1;
        }

        if(0==totalPage){
            totalPage = 1;
        }
        return  totalPage;
    }

    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                '}';
    }

    /**
     * 获取最后一个页面
     * @return
     */
    public int getLast(){
        int last;
        //假设总数是50，是能够被5整除的，那么最后一页开始就是45
        if(0==total%count){
            last = total/count;
        }else{
            last = total - total%count;

        }

        last = last<0?0:last;
        return  last;
    }


}
