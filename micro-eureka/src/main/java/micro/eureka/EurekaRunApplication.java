package micro.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka服务 - 微服务注册中心
 * @author 张宜成
 */

@EnableEurekaServer
@SpringBootApplication
public class EurekaRunApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaRunApplication.class, args);
	}

}
