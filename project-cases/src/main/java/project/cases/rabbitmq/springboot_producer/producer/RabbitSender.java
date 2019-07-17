package project.cases.rabbitmq.springboot_producer.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;  // 注意这个不要引错包
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;  // 注意引这个包
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;  // 注意引这个包
import project.cases.rabbitmq.springboot_consumer.entity.Order;

import java.util.Map;

@Component
public class RabbitSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	// 实现一个监听器用户监听Broker端给我们返回的确认请求  --- 1
	// 注意一点, 在发消息的时候对template进行配置mandatory=true保证监听有效
	// 回调函数: confirm确认
	final ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			System.err.println("correlationData: " + correlationData);
			System.err.println("ack: " + ack);
			// 如果ack为false则进行补偿措施
			if (!ack) {
				System.err.println("异常处理...");
			}
			// ....处理可靠性投递
		}
	};

	// 保证消息对Broker端是可达的,如果出现路由键不可达的情况,则使用监听器对不可达的消息进行后续的处理, 保证消息的路由成功   --- 2
	// 回调函数: return返回 如果路由不存在调用
	final ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
		@Override
		public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
		                            String exchange, String routingKey) {
			System.err.println("return exchange: " + exchange + ", routingKey: "
					+ routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
		}
	};

	public void send(Object message, Map<String, Object> properties) throws Exception{

		MessageHeaders mhs = new MessageHeaders(properties);
		Message msg = MessageBuilder.createMessage(message, mhs);
		rabbitTemplate.setConfirmCallback(confirmCallback);  // ----  1
		rabbitTemplate.setReturnCallback(returnCallback);  // ----  2
		CorrelationData cd = new CorrelationData("1234567890");  // 此处是伪代码, 正式时使用id + 时间戳,全局唯一
		rabbitTemplate.convertAndSend("exchange-1", "springboot.hello", msg);
	}

	//发送消息方法调用: 构建自定义对象消息
	public void sendOrder(Order order) throws Exception {
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
		//id + 时间戳 全局唯一
		CorrelationData correlationData = new CorrelationData("0987654321");
		rabbitTemplate.convertAndSend("exchange-2", "springboot.def", order, correlationData);
	}

}
