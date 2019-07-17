package project.cases.rabbitmq.springboot_consumer.consumer;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class RabbitReceiver {

	// 能够实现自动创建
	@RabbitListener(bindings = @QueueBinding(
			// 指定队列名称和是否持久化
			value = @Queue(value = "queue-1",
					durable="true"),
			// 指定路由名称, 是否持久化, 类型, 是否过滤, 指定路由key
			exchange = @Exchange(value = "exchange-1",
					durable="true",
					type= "topic",
					ignoreDeclarationExceptions = "true"),
					key = "springboot.*")
	)

	@RabbitHandler
	public void onMessage(Message message, Channel channel) throws Exception {
		System.err.println("消费端Payload: " + message.getPayload());  // 消费端收到消费体内容
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		channel.basicAck(deliveryTag, false);  //手工ACK
	}

	// -------------------------------------------

	@RabbitListener(bindings = @QueueBinding(
			// 指定队列名称和是否持久化
			value = @Queue(value = "queue-2",
					durable="true"),
			// 指定路由名称, 是否持久化, 类型, 是否过滤, 指定路由key
			exchange = @Exchange(value = "exchange-2",
					durable="true",
					type= "topic",
					ignoreDeclarationExceptions = "true"),
			key = "springboot.*")
	)
	// 此方式时动态获取配置文件的内容 - 正式环境这么写
//	@RabbitListener(bindings = @QueueBinding(
//			value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
//					durable="${spring.rabbitmq.listener.order.queue.durable}"),
//			exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
//					durable="${spring.rabbitmq.listener.order.exchange.durable}",
//					type= "${spring.rabbitmq.listener.order.exchange.type}",
//					ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
//			key = "${spring.rabbitmq.listener.order.key}")
//	)
	// 此处将message拆开 拆成了@Payload和@Headers
	@RabbitHandler
	public void onOrderMessage(@Payload project.cases.rabbitmq.springboot_consumer.entity.Order order,  // @Payload 注解用于指定实际的消息体内容
	                           Channel channel,
	                           @Headers Map<String, Object> headers) throws Exception {  // @Headers
		System.err.println("消费端order: " + order.getId());
		Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}

}
