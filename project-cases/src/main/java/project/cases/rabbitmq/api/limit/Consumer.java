package project.cases.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 *  消费端 => 消费端限流
 */

public class Consumer {

	public static void main(String[] args) throws Exception {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("39.107.250.117");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchangeName = "test_qos_exchange";
		String queueName = "test_qos_queue";
		String routingKey = "qos.#";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		
		//1 限流方式  第一件事就是 autoAck设置为 false <================
		// 第一个参数: 对消费的大小进行限制
		// 第二个参数: 一次性最大多少条
		// 第三个参数: false表示不应用到channel级别,应用到consumer级别
		channel.basicQos(0, 1, false);

		channel.basicConsume(queueName, false, new MyConsumer(channel));
	}
}
