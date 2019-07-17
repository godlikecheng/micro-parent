package project.cases.rabbitmq.api.returnListener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 *  消费端 returnListener =>
 *  在某些情况下,如果我们在发送消息的时候,当前的exchange不存在或者是指定的路由key路由不到,
 *  这个时候如果我们需要监听这种不可达的消息,就要使用Return Listener!!!
 */
public class Consumer {

	public static void main(String[] args) throws Exception {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("39.107.250.117");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchangeName = "test_return_exchange";
		String routingKey = "return.#";
		String queueName = "test_return_queue";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, queueingConsumer);
		
		while(true){
			Delivery delivery = queueingConsumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.err.println("消费者: " + msg);
		}
	}
}
