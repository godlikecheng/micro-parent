package project.cases.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  生产端 - dlx-死信队列
 *
 *   操作步骤
 *   1. 先运行Consumer端,创建死信队列和普通队列
 *   2. 关闭掉消费端,目的是为了看清在没有消费者的情况下,消息队列过期的数据移动到死信队列中
 *   3. 运行生产端.查看效果
 */
public class Producer {

	public static void main(String[] args) throws Exception {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("39.107.250.117");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_dlx_exchange";
		String routingKey = "dlx.save";
		
		String msg = "Hello RabbitMQ DLX Message";
		
		for(int i =0; i<1; i ++){
			
			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.expiration("10000")  // 如果消费端没有消费的话,会过期,则会被提交到已经设置好的死信队列中
					.build();
			channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());
		}
		
	}
}
