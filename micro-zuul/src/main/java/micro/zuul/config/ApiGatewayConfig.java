package micro.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import micro.zuul.filter.TokenFilter;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public TokenFilter accessFilter(){
        return new TokenFilter();
    }
}
