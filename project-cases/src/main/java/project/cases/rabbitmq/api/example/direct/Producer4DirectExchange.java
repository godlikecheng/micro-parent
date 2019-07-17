package project.cases.rabbitmq.api.example.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  生产者example => direct模式 => 直接交换模式
 * @描述: 生产者和消费者,具有相同的交换机名称(Exchange)、 交换机类型和相同的密钥(routeingkey), 那么消费者即可成功获取到消息
 */
public class Producer4DirectExchange {

	
	public static void main(String[] args) throws Exception {
		
		//1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("39.107.250.117");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2 创建Connection
		Connection connection = connectionFactory.newConnection();
		//3 创建Channel
		Channel channel = connection.createChannel();  
		//4 声明
		String exchangeName = "test_direct_exchange";
		String routingKey = "test.direct";
		//5 发送
		
		String msg = "Hello World RabbitMQ 4  Direct Exchange Message  ... ";
		channel.basicPublish(exchangeName, routingKey , null , msg.getBytes()); 		
		
	}
	
}
