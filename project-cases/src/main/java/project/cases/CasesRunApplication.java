package project.cases;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2Doc
@EnableHystrix
@SpringBootApplication
public class CasesRunApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasesRunApplication.class, args);
	}
}
