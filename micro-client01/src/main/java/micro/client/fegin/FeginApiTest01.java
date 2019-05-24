package micro.client.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  微服务 - Fegin客户端调用工具接口
 * @author 张宜成
 *
 */
@FeignClient(name = "micro-client02")
public interface FeginApiTest01 {

	// Fegin 书写的方式以SpringMVC接口形式书写
	// @FeginClient调用服务接口name就是服务的名称

	/*
	 * 调用哪个接口方法就将哪个方法拖过来
	 */
	@RequestMapping(value="/getTest2",method=RequestMethod.GET)
	public String getTest();

}