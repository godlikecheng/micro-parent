package project.cases.rabbitmq.api.example.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  example => fanout
 * @描述: 不处理路由键, 只需要简单的将队列绑定到交换机上
 *        发送到交换机的消息都会被转发到与该交换机绑定的所有队列上
 *        Fanout交换机转发消息是最快的
 */
public class Producer4FanoutExchange {

	
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
		String exchangeName = "test_fanout_exchange";
		//5 发送
		for(int i = 0; i < 10; i ++) {
			String msg = "Hello World RabbitMQ 4 FANOUT Exchange Message ...";
			channel.basicPublish(exchangeName, "", null , msg.getBytes()); 			
		}
		channel.close();  
        connection.close();  
	}
	
}
