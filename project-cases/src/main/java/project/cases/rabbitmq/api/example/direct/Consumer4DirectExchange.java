package project.cases.rabbitmq.api.example.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 *  消费者example => direct模式 => 直接交换模式
 * @描述: 生产者和消费者,具有相同的交换机名称(Exchange)、 交换机类型和相同的密钥(routingKey), 那么消费者既可成功获取到消息.
 * @备注:
 */
public class Consumer4DirectExchange {

	public static void main(String[] args) throws Exception {
		
		
        ConnectionFactory connectionFactory = new ConnectionFactory() ;  
        
        connectionFactory.setHost("39.107.250.117");
        connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        Connection connection = connectionFactory.newConnection();
        
        Channel channel = connection.createChannel();  
		//4 声明
		String exchangeName = "test_direct_exchange";
		String exchangeType = "direct";
		String queueName = "test_direct_queue";
		String routingKey = "test.direct";
		
		//表示声明(创建)了一个交换机
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		//表示声明(创建)了一个队列
		channel.queueDeclare(queueName, false, false, false, null);
		//建立一个绑定关系:
		channel.queueBind(queueName, exchangeName, routingKey);
		
        //durable 是否持久化消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queueName, true, consumer);  
        //循环获取消息  
        while(true){  
            //获取消息，如果没有消息，这一步将会一直阻塞  
            Delivery delivery = consumer.nextDelivery();  
            String msg = new String(delivery.getBody());    
            System.out.println("收到消息：" + msg);  
        } 
	}
}
