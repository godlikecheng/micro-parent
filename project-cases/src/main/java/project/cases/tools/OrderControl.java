package project.cases.tools;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 *  特殊拦截功能 - 接口访问记录
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)  // 执行顺序的注解
public class OrderControl implements Filter {

    private Integer i = 1;  // 定义全局变量

    /**
     *  开始特殊拦截
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (false) {
            System.out.println("抱歉您被拦截了....");
        } else {
//            System.out.println("服务启动接口被调用了:" + i + "次");
            i ++;
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}
