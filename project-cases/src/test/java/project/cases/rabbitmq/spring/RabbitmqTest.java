package project.cases.rabbitmq.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import project.cases.CasesApplicationTests;
import project.cases.rabbitmq.spring.entity.Order;
import project.cases.rabbitmq.spring.entity.Packaged;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class RabbitmqTest extends CasesApplicationTests {

	@Autowired
	private RabbitAdmin rabbitAdmin;

	@Test
	public void testAdmin() throws Exception {

		// 创建Exchange => Direct Topic Fanout
		rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));
		rabbitAdmin.declareExchange(new TopicExchange("test.topic", false, false));
		rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));

		// 创建queue
		rabbitAdmin.declareQueue(new Queue("test.direct.queue", false));
		rabbitAdmin.declareQueue(new Queue("test.topic.queue", true));
		rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));

		// 建立一个新的绑定关系
		rabbitAdmin.declareBinding(new Binding("test.direct.queue",
				Binding.DestinationType.QUEUE,
				"test.direct",
				"direct",
				new HashMap<>()));

		// 建立一个topic的绑定,===综合和写法,可以之前不用先声明队列和路由,这种方法支持链式编程===
		rabbitAdmin.declareBinding(
				BindingBuilder
				.bind(new Queue("test.topic.queue", false))  // 直接创建队列
				.to(new TopicExchange("test.topic", false, false))  // 直接创建交换机,建立关联关系
				.with("user.#")  // 指定路由key
		);

		// fanout的没有with
		rabbitAdmin.declareBinding(
				BindingBuilder
				.bind(new Queue("test.fanout.queue", true))
				.to(new FanoutExchange("test.fanout", false, false))
		);

		// 清空队列数据
		rabbitAdmin.purgeQueue("test.topic.queue", true);
	}

	// --------------------------------------------------------------------------------

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void testSendMessage() throws Exception {

		// 1. 创建消息
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.getHeaders().put("desc", "信息描述..");
		messageProperties.getHeaders().put("type", "自定义消息类型..");
		Message message = new Message("Hello RabbitMQ".getBytes(), messageProperties);

		rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {

				System.err.println("------------添加额外的设置-------------");
				message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
				message.getMessageProperties().getHeaders().put("attr", "额外新加的属性");

				return message;
			}
		});
	}

	@Test
	public void testSendMessage2() throws Exception {
		//1 创建消息
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("text/plain");
		Message message = new Message("mq 消息1234".getBytes(), messageProperties);

		rabbitTemplate.send("topic001", "spring.abc", message);
		rabbitTemplate.convertAndSend("topic001", "spring.amqp", "hello object message send!");
		rabbitTemplate.convertAndSend("topic002", "rabbit.abc", "hello object message send!");
	}

	@Test
	public void testSendMessage4Text() throws Exception {
		//1 创建消息
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("text/plain");
		Message message = new Message("mq 消息1234".getBytes(), messageProperties);

		rabbitTemplate.send("topic001", "spring.abc", message);
		rabbitTemplate.send("topic002", "rabbit.abc", message);
	}

	/**
	 *  json 转map
	 */
	@Test
	public void testSendJsonMessage() throws Exception {

		Order order = new Order();
		order.setId("001");
		order.setName("消息订单");
		order.setContent("描述信息");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(order);
		System.err.println("order 4 json: " + json);

		MessageProperties messageProperties = new MessageProperties();
		//这里注意一定要修改contentType为 application/json
		messageProperties.setContentType("application/json");
		Message message = new Message(json.getBytes(), messageProperties);

		rabbitTemplate.send("topic001", "spring.order", message);
	}

	/**
	 *  json转java对象
	 */
	@Test
	public void testSendJavaMessage() throws Exception {

		Order order = new Order();
		order.setId("001");
		order.setName("订单消息");
		order.setContent("订单描述信息");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(order);
		System.err.println("order 4 json: " + json);

		MessageProperties messageProperties = new MessageProperties();
		//这里注意一定要修改contentType为 application/json
		messageProperties.setContentType("application/json");
		messageProperties.getHeaders().put("__TypeId__", "project.cases.rabbitmq.spring.entity.Order");
		Message message = new Message(json.getBytes(), messageProperties);

		rabbitTemplate.send("topic001", "spring.order", message);
	}

	@Test
	public void testSendMappingMessage() throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		Order order = new Order();
		order.setId("001");
		order.setName("订单消息");
		order.setContent("订单描述信息");

		String json1 = mapper.writeValueAsString(order);
		System.err.println("order 4 json: " + json1);

		MessageProperties messageProperties1 = new MessageProperties();
		//这里注意一定要修改contentType为 application/json
		messageProperties1.setContentType("application/json");
		messageProperties1.getHeaders().put("__TypeId__", "order");
		Message message1 = new Message(json1.getBytes(), messageProperties1);
		rabbitTemplate.send("topic001", "spring.order", message1);

		Packaged pack = new Packaged();
		pack.setId("002");
		pack.setName("包裹消息");
		pack.setDescription("包裹描述信息");

		String json2 = mapper.writeValueAsString(pack);
		System.err.println("pack 4 json: " + json2);

		MessageProperties messageProperties2 = new MessageProperties();
		//这里注意一定要修改contentType为 application/json
		messageProperties2.setContentType("application/json");
		messageProperties2.getHeaders().put("__TypeId__", "packaged");
		Message message2 = new Message(json2.getBytes(), messageProperties2);
		rabbitTemplate.send("topic001", "spring.pack", message2);
	}

	@Test
	public void testSendExtConverterMessage() throws Exception {

			// 图片测试
/*			byte[] body = Files.readAllBytes(Paths.get("d:/002_books", "picture.png"));
			MessageProperties messageProperties = new MessageProperties();
			messageProperties.setContentType("image/png");
			messageProperties.getHeaders().put("extName", "png");
			Message message = new Message(body, messageProperties);
			rabbitTemplate.send("", "image_queue", message);*/  // 如果第一个参数为空, 则第二个参数默认去找名称相同的人队列

		// pdf测试
		byte[] body = Files.readAllBytes(Paths.get("d:/002_books", "mysql.pdf"));
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("application/pdf");
		Message message = new Message(body, messageProperties);
		rabbitTemplate.send("", "pdf_queue", message);  // 如果第一个参数为空, 则第二个参数默认去找名称相同的人队列
	}


}
