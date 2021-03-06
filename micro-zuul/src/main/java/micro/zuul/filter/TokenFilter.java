package micro.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 微服务 - Token拦截类
 *
 * @author 张宜成
 */

@Component
public class TokenFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(TokenFilter.class);

	@Value("${server.port}")
	private String serverPort;

	/*
	 * 判断过滤器是否生效
	 */
	@Override
	public boolean shouldFilter() {
		System.out.println("--------------------Zuul过滤拦截生效--------------------");
		return true;  // 此处设置为true则run()方法执行, 设置为false则run()方法不执行
	}

	/*
	 * 编写过滤器拦截业务逻辑代码
	 */
	@Override
	public Object run() {

		System.out.println("--------------------Zuul过滤拦截开始--------------------");

		// 获取上下文
		RequestContext currentContext = RequestContext.getCurrentContext();
		// 获取request
		HttpServletRequest request = currentContext.getRequest();
		// 获取token的时候,一般从请求头中拿到
		String userToken = request.getParameter("userToken");
		userToken = "111";  // 正式拦截中将此行删除
		if (StringUtils.isEmpty(userToken)) {
			// 不会继续执行了.不会去调用服务接口,网关服务直接响应给客户端
			currentContext.setSendZuulResponse(false);
			currentContext.setResponseStatusCode(401);
			currentContext.setResponseBody("用户的Token令牌不能为空");
			return null;
		}

		// 否则正常执行业务逻辑.....
		System.out.println("网关服务器端口:" + serverPort);
		System.out.println("--------------------Zuul过滤拦截结束--------------------");
		return null;

	}

	/*
	 * 过滤器类型 pre 表示在 请求之前进行拦截
	 */
	@Override
	public String filterType() {

		return "pre";
	}

	/*
	 * 过滤器的执行顺序。当请求在一个阶段的时候存在多个过滤器时，需要根据该方法的返回值依次执行
	 */
	@Override
	public int filterOrder() {

		return 0;
	}

}
