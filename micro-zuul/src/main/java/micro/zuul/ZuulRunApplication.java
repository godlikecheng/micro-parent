package micro.zuul;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Zuul服务 - 微服务网关
 * @author 张宜成
 */

@EnableZuulProxy
@EnableEurekaClient
@EnableSwagger2Doc
@SpringBootApplication
public class ZuulRunApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulRunApplication.class);
	}

	// 网关配置 - Swagger添加文档来源
	@Component
	@Primary
	class DocumentationConfig implements SwaggerResourcesProvider {
		@Override
		public List<SwaggerResource> get() {
			List resources = new ArrayList<>();
			// "microService-test01" 逗号前面可以随便写,后面要按照yml的配置写
			resources.add(swaggerResource("micro-client01", "/micro-client01/v2/api-docs", "2.0"));
			resources.add(swaggerResource("micro-client02", "/micro-client02/v2/api-docs", "2.0"));
			return resources;
		}

		private SwaggerResource swaggerResource(String name, String location, String version) {
			SwaggerResource swaggerResource = new SwaggerResource();
			swaggerResource.setName(name);
			swaggerResource.setLocation(location);
			swaggerResource.setSwaggerVersion(version);
			return swaggerResource;
		}
	}
}
