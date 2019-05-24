package micro.client.controller;

import micro.client.fegin.FeginApiTest01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *  微服务 - Fegin客端端调用工具
 * @author 张宜成
 *
 */

@Api("Fegin客端端调用工具")
@RestController
public class FeginController {
	//  extends BaseApiService implements IOrderService
	
	@Autowired
	private FeginApiTest01 feginApiTest01;  // 注入Fegin的接口(TestFeginApi01)
	
	/*
	 * 调用接口
	 */
	@ApiOperation("Fegin接口调用")
	@RequestMapping(value="getTest2", method=RequestMethod.GET)
	public String feginTest() {
		System.out.println("非Hystrix:" + Thread.currentThread());
		return feginApiTest01.getTest();
	}
	
	/*
	 * 解决服务雪崩效应
	 */
	@ApiOperation("解决服务雪崩效应测试")
	@HystrixCommand(fallbackMethod="hystrixManage")
	@RequestMapping(value="/HystrixTest", method=RequestMethod.GET)
	public String HystrixTest() {
		System.out.println("Hystrix：" + Thread.currentThread());
		return feginApiTest01.getTest();
	}
	
	// fallbackMethod="" 方法的作用：服务降级处理
	// @HystrixCommand() 默认开启线程池隔离方式， 服务降级，服务熔断。
	@ApiOperation("雪崩效应降级处理")
	@RequestMapping(value = "/hystrixManage", method=RequestMethod.GET)
	public String hystrixManage() {
		
		return "服务降级，服务器忙，请稍后重试";
	}
	
	
}
