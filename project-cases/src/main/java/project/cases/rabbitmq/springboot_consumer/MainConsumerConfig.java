package project.cases.rabbitmq.springboot_consumer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *  RabbitMQ消费端的主配置类
 */

@Configuration
@ComponentScan({"project.cases.rabbitmq.springboot_consumer.*"})
public class MainConsumerConfig {
}
