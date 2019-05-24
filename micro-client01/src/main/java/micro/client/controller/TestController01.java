package micro.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api("测试控制类")
@RestController
public class TestController01 {
	
	/*
	 * 测试方法
	 */
	@ApiOperation("测试方法")
	@RequestMapping(value="/getTest",method=RequestMethod.GET)
	public String getTest() {
		return "this is Test 7000";
	}
	
	/*
	 *  Swagger测试
	 */
	@ApiOperation("Swagger测试")
	@ApiImplicitParam(name="swagger", value="测试swagger参数", required=true, dataType="String")
	@RequestMapping(value="/swaggerTest",method=RequestMethod.GET)
	public String setSwagger(String swagger) {
		System.out.println("Swagger测试" + swagger);
		return swagger;
	}
	
}

