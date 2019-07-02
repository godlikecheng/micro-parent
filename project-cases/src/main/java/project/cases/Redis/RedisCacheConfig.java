package project.cases.Redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

/**
 *  编写自定义 redis 配置类
 *  该类告诉 spring 当前使用的缓存服务为 redis 并自定义了缓存 key 生成的规则
 *   => 缓存一般使用在服务层，在你想缓存的方法上面添加相应的注解即可，下面三个缓存的注解你得掌握。
 *   @Cacheable spring 会在其被调用后将返回值缓存起来，以保证下次利用同样的参数来执行该方法时可以直接从缓存中获取结果，而不需要再次执行该方法。
 *   @CachePut 标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
 *   @CacheEvict 用来标注在需要清除缓存元素的方法或类上的。
 *
 *   => UserService中进行测试
 */

//@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

	protected final static Logger log = LoggerFactory.getLogger(RedisCacheConfig.class);

	private volatile JedisConnectionFactory mJedisConnectionFactory;
	private volatile RedisTemplate<String, String> mRedisTemplate;
	private volatile RedisCacheManager mRedisCacheManager;

	public RedisCacheConfig() {
		super();
	}

	public RedisCacheConfig(JedisConnectionFactory mJedisConnectionFactory, RedisTemplate<String, String> mRedisTemplate, RedisCacheManager mRedisCacheManager) {
		super();
		this.mJedisConnectionFactory = mJedisConnectionFactory;
		this.mRedisTemplate = mRedisTemplate;
		this.mRedisCacheManager = mRedisCacheManager;
	}

	public JedisConnectionFactory redisConnectionFactory() {
		return mJedisConnectionFactory;
	}

	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
		return mRedisTemplate;
	}

	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		return mRedisCacheManager;
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object o, Method method, Object... objects) {
				StringBuilder sb = new StringBuilder();
				sb.append(o.getClass().getName());
				sb.append(method.getName());
				for (Object obj : objects) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
}
