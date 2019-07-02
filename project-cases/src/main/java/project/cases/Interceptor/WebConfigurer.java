package project.cases.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  拦截器配置类
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器及需要拦截的方法 => 前台拦截
        registry.addInterceptor(new BeforeInterceptor())
                .addPathPatterns("/address", "/insertAddress", "/delAddr", "/editAddrs")
                 // 网站首页, 搜索框, 新闻获取 未拦截
                .addPathPatterns("/insertInformation", "/information")
                .addPathPatterns("/collection", "/cancelCollect", "/addCollect")  // 商品介绍页 未拦截
                .addPathPatterns("/consultation", "/suggest", "/insertConsultation", "/insertSuggest", "/news")
                .addPathPatterns("/order", "/delOrdersOne", "/change", "/reCargo", "/cancel", "/orderinfo", "/commentlist", "/comment", "/commentListSubmit", "/refund", "/refundSubmit", "/deleteRefund")
                .addPathPatterns("/pay", "/success", "/walletlist")
                .addPathPatterns("/shopcart", "/delShopCartIntroduction", "/addShopCartToIntroduction", "/jiaNumber", "/jianNumber")
                .addPathPatterns("/password", "/updatePassword");  // 用户登录跳转, 验证用户登录, 用户注册跳转, 用户注册, 用户个人中心 未拦截


    }


}
