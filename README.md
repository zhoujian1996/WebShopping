# WebShopping
模仿天猫网站

# 用到的技术 jsp+mysql+servlet
 *  表示层：jsp
 * 持久化层：mysql ，jdbc
 * 控制层： servlet

# 用到的思想
  因为对于不同的页面所采用不同的servlet处理的话，需要大量的servlet并且需要对其进行大量的配置。为简化开发，可以采用filter+servlet的方法：
  通过过滤器拦截uri,对uri进行分析，然后反射可以在一个servlet内通过方法的调用实现简化开发。
  
  

# 开发流程
     *   先进行表结构的设计
     *  实体类的设计，对数据库表有良好的映射
     *  DAO类的开发，构建对表的操作与实体类之间的映射关系
     *  后台开发
                *  商品分类管理 
                                * 查询分类
                                * 增加分类
                                * 修改分类
                                * 删除分类
                                
                 * 属性管理
                 
                 * 产品管理
                 
                 * 产品图片管理
                 
                 * 产品属性值设置
                 
                 * 用户管理
                 
                 * 订单管理
       * 前台管理
                 * 注册
                 * 登录
                 *退出
                 * 产品ye
                 *分类页
                 * 搜索
                 * 购物车
                 * 订单
                 
                 
# 总结
    （1）把握重点，李清业务逻辑，把重点放在后端，jsp了解即可。
     (2) 在增删该查的基础上，合理增加功能
      
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
