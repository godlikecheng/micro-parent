package micro.client.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 微服务 - Swagger 配置文件
 * @author 张宜成
 */
@Configuration
@EnableSwagger2 // 开启Swagger功能
public class SwaggerConfig {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// 生成API扫包范围
				.apis(RequestHandlerSelectors.basePackage("micro.client.controller")).paths(PathSelectors.any()).build();
	}

	// 创建API文档信息
	private ApiInfo apiInfo() {

		// title 文档标题 // description 文档描述 // termsOfServiceUrl 官方网址
		return new ApiInfoBuilder().title("微服务 - micro-client02")
				.description("微服务 - micro-client - micro-client02服务。")
				.termsOfServiceUrl("http://www.chengcheng.kim")
				// .contact(contact)
				.version("1.0").build();
	}
}