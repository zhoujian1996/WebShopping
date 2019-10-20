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
    （1） 
