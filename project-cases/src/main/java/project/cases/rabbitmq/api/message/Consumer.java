package project.cases.rabbitmq.api.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 *  消费端 - Message属性设置
 */
public class Consumer {

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

		// 1. 创建一个ConnectionFactory,并进行配置
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("39.107.250.117");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");

		// 2. 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();

		// 3. 通过connection创建一个Channel
		Channel channel = connection.createChannel();

		// 4. 声明(创建)一个队列
		String queueName = "test001";
		channel.queueDeclare(queueName, true, false, false, null);

		// 5. 创建消费者
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

		// 6.设置Channel
		channel.basicConsume(queueName, true, queueingConsumer);

		while (true) {
			// 7. 消息的获取
			QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println("消费者: " + msg);

			// 获取自定义消息
			Map<String, Object> headers =delivery.getProperties().getHeaders();
			System.out.println("headers get my1 value: " + headers.get("my1"));

		}

	}

}
