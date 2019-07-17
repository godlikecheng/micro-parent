package project.cases.rabbitmq.api.ack;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 *  重回队列 => 如果值为0, 则重回队列
 */
public class MyConsumer extends DefaultConsumer {

	private Channel channel ;
	
	public MyConsumer(Channel channel) {
		super(channel);
		this.channel = channel;
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
		System.err.println("-----------consume message----------");
		System.err.println("body: " + new String(body));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if((Integer)properties.getHeaders().get("num") == 0) {
			// 第三个参数设置为false就不重新投递了, 设置为true重新投递回队列
			// 重回队列会将失败的消息重新发回队列的尾部
			channel.basicNack(envelope.getDeliveryTag(), false, true);  // basicNack
		} else {
			channel.basicAck(envelope.getDeliveryTag(), false);  // basicAck
		}
	}

}
