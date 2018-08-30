Ribbon
===

#这是一个springcloud ribbon的Demo,可以用来演示客户端负载均衡。
---
    首先启动这三个应用
---
* eureka 服务注册中心，product ribbon的服务将会注册在eureka中
* ribbon 客户端负载均衡，接受来自浏览器url中的请求，转发到product服务中，ribbon得到product返回的String,打印到日志中
* product 提供服务给ribbon
    
这三个应用启动之后，输入`http://localhost:8761/`,就能进入eureka的首页，在`Instances currently registered with Eureka`中能看到
    已经注册的`PRODUCT`和`RIBBON`两个服务。
    
在浏览器中输入`http://localhost:7071/getProductMsg`，就来到了com.rosam.ribbon.controller的ConsumerController中的getProductMsg()方法，
    该方法提供了三种方式，实现向product服务发起请求，在com.rosam.ribbon.config中我对RestTemplate进行了配置<br>

```Java
    @Component
    public class RestTemplateConfig {
      @Bean
      @LoadBalanced
      public RestTemplate restTemplate(){
          return new RestTemplate();
      }
    }
```
配置之后，我就可以在com.rosam.ribbon.ConsumerController对RestTemplate进行注入了。

Ribbon的负载均衡规则
默认是轮询的方式，当然我们可以进行修改，在ribbon的resource目录下application.yml中配置
`PRODUCT.ribbon.NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule`
那么它采用的方式就是随机，我们可以创建两个product服务的实例，在edit Configuration，克隆一个product,修改VM :`-Dserver.port=8081`，修改不同的返回值.
在浏览器中输入`http://localhost:7071/getProductMsg`,浏览器打印的就是随机的其中一个的返回值。这样就是实现了负载均衡规则。
    
    

