package micro.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.spring4all.swagger.EnableSwagger2Doc;

/**
 *  微服务 - 测试服务类2
 * @author 张宜成
 */
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2Doc
@EnableHystrix
@SpringBootApplication
public class ClientRunApplication02 {

	public static void main(String[] args) {
		SpringApplication.run(ClientRunApplication02.class, args);
	}

}