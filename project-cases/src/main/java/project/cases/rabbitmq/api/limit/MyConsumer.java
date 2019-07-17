package project.cases.rabbitmq.api.limit;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 *  消费端限流
 */
public class MyConsumer extends DefaultConsumer {

	private Channel channel;  // <<<<<<<<<<<<
	
	public MyConsumer(Channel channel) {
		super(channel);
		this.channel = channel;  // <<<<<<<<<<<<
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
		System.err.println("-----------consume message----------");
		System.err.println("consumerTag: " + consumerTag);
		System.err.println("envelope: " + envelope);
		System.err.println("properties: " + properties);
		System.err.println("body: " + new String(body));

		// <<<<<<<<<<< envelope.getDeliveryTag() 取到消息的标签
		channel.basicAck(envelope.getDeliveryTag(), false); // 如果注释掉该行,则消费者只消费一条(一条在Consumer中有所设置)
		
	}

}
