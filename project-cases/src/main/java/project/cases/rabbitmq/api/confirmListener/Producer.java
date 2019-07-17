package project.cases.rabbitmq.api.confirmListener;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  生产者 => Confirm确认消息实现
 */
public class Producer {

	public static void main(String[] args) throws Exception {

		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("39.107.250.117");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2 获取Connection
		Connection connection = connectionFactory.newConnection();
		
		//3 通过Connection创建一个新的Channel
		Channel channel = connection.createChannel();
		
		//4 指定我们的消息投递模式: 消息的确认模式 <=========
		channel.confirmSelect();
		
		String exchangeName = "test_confirm_exchange";
		String routingKey = "confirmListener.save";
		
		//5 发送一条消息
		String msg = "Hello RabbitMQ Send confirmListener message!";
		channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
		
		//6 添加一个确认监听 <=========
		channel.addConfirmListener(new ConfirmListener() {  //匿名接口

			// 失败的时候调用
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.err.println("-------no ack!-----------");
			}

			// 成功的时候调用
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				System.err.println("-------ack!-----------");
			}
		});

		// 因为在这里是做一个响应的消息确认程序, 所以不用close.
	}
}
