package project.cases.rabbitmq.api.returnListener;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 *  生产端 returnListener =>
 *  在某些情况下,如果我们在发送消息的时候,当前的exchange不存在或者是指定的路由key路由不到,
 *  这个时候如果我们需要监听这种不可达的消息,就要使用Return Listener!!!
 */
public class Producer {

	public static void main(String[] args) throws Exception {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("39.107.250.117");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_return_exchange";
		String routingKey = "return.save";
		String routingKeyError = "abc.save";  //<============
		
		String msg = "Hello RabbitMQ Return Message";

		//  监控不可达消息 <====================================
		channel.addReturnListener(new ReturnListener() {
			@Override
			public void handleReturn(int replyCode, String replyText, String exchange,
					String routingKey, BasicProperties properties, byte[] body) throws IOException {
				System.err.println("---------handle  return----------");
				System.err.println("replyCode: " + replyCode);  // 响应码
				System.err.println("replyText: " + replyText);  //文本
				System.err.println("exchange: " + exchange);  // 交换机
				System.err.println("routingKey: " + routingKey);
				System.err.println("properties: " + properties);
				System.err.println("body: " + new String(body));  // 实际的消息体
			}
		});

		// 第三个参数设置为true, 就算路由不到,也不会将消息删除  <=============
//		channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());   // 路由key符和消费者的规范

		// 设置为true才会返回handleReturn 否则不会去监听,设置成false直接被rabbitmq删除.
		channel.basicPublish(exchange, routingKeyError, true, null, msg.getBytes());  // 路由key不符合消费者的规范,因此返回调用handleReturn
		
		
		
		
	}
}
