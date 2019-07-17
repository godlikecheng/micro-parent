package project.cases.rabbitmq.api.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 *  生产端 - Message属性设置
 */
public class Producer {

	public static void main(String[] args) throws IOException, TimeoutException {

		// 1. 创建一个ConnectionFactory,并进行配置
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("39.107.250.117");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");

		// 2. 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();

		// 3. 通过connection创建一个Channel
		Channel channel = connection.createChannel();

		// headers自定义属性值
		Map<String, Object> headers = new HashMap<>();
		headers.put("my1","111");
		headers.put("my2","222");

		// message属性设置
		AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
				.deliveryMode(2)  // 2 为持久化 || 1 为非持久化消息
				.contentEncoding("UTF-8")  // 字符集设置
				.expiration("10000")  // 设置过期时间为10s => 设置于消息体本身
				.headers(headers)  // 自定义属性
				.build();

		// 4. 通过channel发送数据
		for (int i = 0; i < 5; i ++) {
			String msg = "Hello RabbitMQ!!!";
			// 1. exchange 2.routingKey
//			channel.basicPublish("","test001",null, msg.getBytes());
			channel.basicPublish("","test001",properties, msg.getBytes());  // properties: message属性设置
		}

		// 关闭相关的连接
		channel.close();
		connection.close();
	}

}
