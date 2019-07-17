package project.cases.rabbitmq.spring;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;  // 注意引入的是这个包
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import project.cases.rabbitmq.spring.adapter.MessageDelegate;
import project.cases.rabbitmq.spring.convert.ImageMessageConverter;
import project.cases.rabbitmq.spring.convert.PDFMessageConverter;
import project.cases.rabbitmq.spring.convert.TextMessageConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *  rabbitmq配置类
 *
 *  setDefaultListenerMethod: 默认监听方法名称:用于设置监听方法名称
 *  Delegate: 实际真实的委托对象,用于消息处理
 *  queueOrTagMethodName: 队列标识与方法名称组成的集合 || 可以一一进行队列与方法名称的匹配 || 队列和方法名称绑定, 即指定队列里的消息会被绑定的方法所接收处理
 *
 */

@Configuration
@ComponentScan({"project.cases.rabbitmq.spring.*"})
public class RabbitMQConfig {

	@Bean
	public ConnectionFactory connectionFactory(){
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses("39.107.250.117");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		connectionFactory.setVirtualHost("/");
		return connectionFactory;
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}

	/**
	 * 针对消费者配置
	 * 1. 设置交换机类型
	 * 2. 将队列绑定到交换机
	 FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
	 HeadersExchange ：通过添加属性key-value匹配
	 DirectExchange:按照rRoutingKey分发到指定队列
	 TopicExchange:多关键字匹配
	 */
	@Bean
	public TopicExchange exchange001() {
		return new TopicExchange("topic001", true, false);
	}

	@Bean
	public Queue queue001() {
		return new Queue("queue001", true); //队列持久
	}

	@Bean
	public Binding binding001() {
		return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
	}

	@Bean
	public TopicExchange exchange002() {
		return new TopicExchange("topic002", true, false);
	}

	@Bean
	public Queue queue002() {
		return new Queue("queue002", true); //队列持久
	}

	@Bean
	public Binding binding002() {
		return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
	}

	@Bean
	public Queue queue003() {
		return new Queue("queue003", true); //队列持久
	}

	@Bean
	public Binding binding003() {
		return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");  // 将队列3绑定到路由exchange001上面
	}

	@Bean
	public Queue queue_image() {
		return new Queue("image_queue", true); //队列持久
	}

	@Bean
	public Queue queue_pdf() {
		return new Queue("pdf_queue", true); //队列持久
	}

	// ------------------------------------------------------------------------------

	/**
	 *  注入rabbitTemplate模板
	 */
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}

	//--------------------------------------------------------------------------------

	@Bean
	public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);

		// 设置监听队列
		container.setQueues(queue001(), queue002(), queue003(), queue_image(), queue_pdf());
		// 设置消费者的数量
		container.setConcurrentConsumers(1);
		// 设置最大数量
		container.setMaxConcurrentConsumers(5);
		// 设置是否重回队列 => false为不重回队列
		container.setDefaultRequeueRejected(false);
		// 配置是否自动签收 => auto为自动签收
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		container.setExposeListenerChannel(true);
		// 消费者标签策略
		container.setConsumerTagStrategy(new ConsumerTagStrategy() {
			@Override
			public String createConsumerTag(String queue) {
				return queue + "_" + UUID.randomUUID().toString();
			}
		});


		// 信息监听
		/*container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				String msg = new String(message.getBody());
				System.err.println("-------------消费者: " + msg);
			}
		});*/


		/**
		 * 1 适配器方式. 默认是有自己的方法名字的：handleMessage
		 // 可以自己指定一个方法的名字: consumeMessage
		 // 也可以添加一个转换器: 从字节数组转换为String
		 MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		 adapter.setDefaultListenerMethod("consumeMessage");
		 adapter.setMessageConverter(new TextMessageConverter());
		 container.setMessageListener(adapter);
		 */


		/**
		 * 2 适配器方式: 我们的队列名称 和 方法名称 也可以进行一一的匹配
		 *
		 MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		 adapter.setMessageConverter(new TextMessageConverter());
		 Map<String, String> queueOrTagToMethodName = new HashMap<>();
		 queueOrTagToMethodName.put("queue001", "method1");
		 queueOrTagToMethodName.put("queue002", "method2");
		 adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
		 container.setMessageListener(adapter);
		 */


		// 1.1 支持json格式的转换器 Jackson2JsonMessageConverter
		/**
		 MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		 adapter.setDefaultListenerMethod("consumeMessage");

		 // 下面两行为核心操作
		 Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
		 adapter.setMessageConverter(jackson2JsonMessageConverter);

		 container.setMessageListener(adapter);
		 */


		// 1.2 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象转换
		/**
		 MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		 adapter.setDefaultListenerMethod("consumeMessage");

		 Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();

		// 下面两行为核心操作
		 DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
		 jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);

		 adapter.setMessageConverter(jackson2JsonMessageConverter);
		 container.setMessageListener(adapter);
		 */

		//1.3 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象多映射转换

		 /*MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		 adapter.setDefaultListenerMethod("consumeMessage");
		 Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
		 DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();

		 // 以下四行为重点
		 Map<String, Class<?>> idClassMapping = new HashMap<String, Class<?>>();
		 idClassMapping.put("order", project.cases.rabbitmq.spring.entity.Order.class);
		 idClassMapping.put("packaged", project.cases.rabbitmq.spring.entity.Packaged.class);

		 javaTypeMapper.setIdClassMapping(idClassMapping);  // 支持多个对象间的映射关系转换

		 jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
		 adapter.setMessageConverter(jackson2JsonMessageConverter);
		 container.setMessageListener(adapter);*/


		//1.4 ext convert
		MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
		adapter.setDefaultListenerMethod("consumeMessage");

		//全局的转换器:
		ContentTypeDelegatingMessageConverter convert = new ContentTypeDelegatingMessageConverter();

		TextMessageConverter textConvert = new TextMessageConverter();
		convert.addDelegate("text", textConvert);
		convert.addDelegate("html/text", textConvert);
		convert.addDelegate("xml/text", textConvert);
		convert.addDelegate("text/plain", textConvert);

		Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
		convert.addDelegate("json", jsonConvert);
		convert.addDelegate("application/json", jsonConvert);

		ImageMessageConverter imageConverter = new ImageMessageConverter(); // ImageMessageConverter
		convert.addDelegate("image/png", imageConverter);
		convert.addDelegate("image", imageConverter);

		PDFMessageConverter pdfConverter = new PDFMessageConverter();  // PDFMessageConverter
		convert.addDelegate("application/pdf", pdfConverter);

		adapter.setMessageConverter(convert);
		container.setMessageListener(adapter);

		return container;
	}

}
