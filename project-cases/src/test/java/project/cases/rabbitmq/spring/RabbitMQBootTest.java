package project.cases.rabbitmq.spring;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.cases.CasesApplicationTests;
import project.cases.rabbitmq.springboot_consumer.entity.Order;
import project.cases.rabbitmq.springboot_producer.producer.RabbitSender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  rabbitmq整合springboot的测试类
 */
public class RabbitMQBootTest extends CasesApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private RabbitSender rabbitSender;

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 *  目前回调存在问题 ------ 待解决
	 * @throws Exception
	 */
	@Test
	public void testSender1() throws Exception {
		Map<String, Object> properties = new HashMap<>();
		properties.put("number", "12345");
		properties.put("send_time", simpleDateFormat.format(new Date()));
		rabbitSender.send("Hello RabbitMQ For Spring Boot!", properties);
	}

	@Test
	public void testSender2() throws Exception {
		Order order = new Order("001", "第一个订单");
		rabbitSender.sendOrder(order);
	}





}
