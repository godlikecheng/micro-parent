package project.cases.stream;

import org.junit.Test;
import project.cases.CasesApplicationTests;
import project.cases.model.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 *  JDK1.8新特性 Stream的测试类
 */
public class StreamTest extends CasesApplicationTests {

	/**
	 *  获取Stream
	 */
	String[] arrTest = new String[]{"ab", "cd", "ef"};  // 1、数组
	Stream<String> arrStream = Arrays.stream(arrTest);

	List<String> listTest = Arrays.asList("ab", "cd", "ef");  // 2、集合
	Stream<String> colStream = listTest.stream();

	Stream<String> stream = Stream.of("ab", "cd", "ef");  // 3、值

	/**
	 *  Stream方法使用 - 测试数据
	 */
	List<User> list = Arrays.asList(
			// name，age
			new User("张三", 11),
			new User("王五", 20),
			new User("王五", 91),
			new User("张三", 8),
			new User("李四", 44),
			new User("李四", 44),
			new User("李四", 44)
	);

	/**
	 *  forEach() 使用该方法迭代流中的每个数据
	 */
	/**
	 * 1. forEach 迭代输出每条数据.
	 */
	@Test
	public void testForEach(){
		// java 8 前
		System.out.println("java 8 前");
		for(User user: list){
			System.out.println(user);
		}
		// java 8 lambda
		System.out.println("java 8 lambda");
		list.forEach(user -> System.out.println(user));

		// java 8 stream lambda
		System.out.println("java 8 stream lambda");
		list.stream().forEach(user -> System.out.println(user));
	}

	/**
	 *  2. sorted() 使用该方法排序数据
	 */
	/**
	 * sort 排序.
	 */
	@Test
	public void testSort() {
		System.out.println("-----排序前-----");
		list.forEach(user -> System.out.println(user));
		System.out.println("-----排序后-----");
		// java 8 前
		System.out.println("java 8 前");
		Collections.sort(list, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
//				return o1.getAge().compareTo(o2.getAge());   // 没有该方法
				return 1;
			}
		});
		for (User user : list) {
			System.out.println(user);
		}
		// java 8 stream 方法引用
		System.out.println("java 8 stream 方法引用");
		list.stream().sorted(Comparator.comparing(User::getAge)).forEach(user -> System.out.println(user));
	}

	/**
	 * 3. filter()：使用该方法过滤
	 */
	@Test
	public void testFilter() {
		// 输出年龄大于50的人
		System.out.println("-----过滤前-----");
		list.forEach(user -> System.out.println(user));
		System.out.println("-----过滤后-----");
		// java 8 前
		System.out.println("java 8 前");
		for(User user: list){
			if (user.getAge() > 50) {
				System.out.println(user);
			}
		}
		// java 8 stream
		System.out.println("java 8 stream");
		list.stream().filter((User user) -> user.getAge() > 50).forEach(user -> System.out.println(user));
	}

	/**
	 *  4. limit()：使用该方法截断
	 */
	/**
	 * limit 截断.
	 */
	@Test
	public void testLimit() {
		// 从第三个开始截断，只输出前三个
		System.out.println("-----截断前-----");
		list.forEach(user -> System.out.println(user));
		System.out.println("-----截断后-----");
		// java 8 前
		System.out.println("java 8 前");
		for (int i = 0; i < 3; i++) {
			System.out.println(list.get(i));
		}
		// java 8 stream
		System.out.println("java 8 stream");
		list.stream().limit(3).forEach(user -> System.out.println(user));
	}

	/**
	 *  5. skip()：与limit互斥，使用该方法跳过元素
	 */
	@Test
	public void testSkip() {
		// 跳过前三个元素，从第四个开始输出
		System.out.println("-----跳过前-----");
		list.forEach(user -> System.out.println(user));
		System.out.println("-----跳过后-----");
		// java 8 前
		System.out.println("java 8 前");
		for (int i = 3; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		// java 8 stream
		System.out.println("java 8 stream");
		list.stream().skip(3).forEach(user -> System.out.println(user));
	}

	/**
	 *  6. distinct()：使用该方法去重，注意：必须重写对应泛型的hashCode()和equals()方法
	 */
	@Test
	public void testDistinct() {
		// 因为Arrays.asList() 返回的是Arrays的内部类ArrayList，操作remove，add会报错
		List<User> users = new ArrayList(list);
		// 为list去除重复数据
		System.out.println("-----去重前-----");
		list.forEach(user -> System.out.println(user));
		System.out.println("-----去重后-----");
		// java 8 前
		System.out.println("java 8 前");
		for (int i = 0; i < users.size() - 1; i++) {
			for (int j = users.size() - 1; j > i; j--) {
				if (users.get(j).getAge() == users.get(i).getAge() && users.get(j).getName()
						.equals(users.get(i).getName())) {
					users.remove(i);
				}
			}
		}
		for (User user : users) {
			System.out.println(user);
		}
		// java 8 stream
		System.out.println("java 8 stream");
		users.stream().distinct().forEach(user -> System.out.println(user));
	}

	/**
	 * 6. 去重+按照年龄大于40以后从小到大+只取前二
	 */
	@Test
	public void demo() {
		list.stream().distinct().filter(user -> user.getAge() > 40).sorted(
				Comparator.comparing(User::getAge)).limit(2).forEach(user -> System.out.println(user));
	}

	/**
	 *  7. max，min，sum，avg，count  测试计算
	 */
	@Test
	public void testNum() {
		IntSummaryStatistics num = list.stream().mapToInt(u -> u.getAge())
				.summaryStatistics();
		System.out.println("总共人数：" + num.getCount());
		System.out.println("平均年龄：" + num.getAverage());
		System.out.println("最大年龄：" + num.getMax());
		System.out.println("最小年龄：" + num.getMin());
		System.out.println("年龄之和：" + num.getSum());
	}

	/**
	 *  8. map()：接收一个方法作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素 -> Map映射
	 */
	@Test
	public void testMap() {
		// 只输出所有人的年龄
		list.stream().forEach(user -> System.out.println(user));
		System.out.println("映射后----->");
		List<Integer> ages = list.stream().map(user -> user.getAge()).collect(toList());
		ages.forEach(age -> System.out.println(age));

		// 小写转大写
		List<String> words = Arrays.asList("aaa", "vvvv", "cccc");
		System.out.println("全部大写---->");
		List<String> collect = words.stream().map(s -> s.toUpperCase()).collect(toList());
		collect.forEach(s -> System.out.println(s));
	}

	/**
	 *  9. flatMap()：对每个元素执行mapper指定的操作，并用所有mapper返回的Stream中的元素组成一个新的Stream作为最终返回结果，通俗易懂就是将原来的stream中的所有元素都展开组成一个新的stream
	 */
	@Test
	public void testFlatMap() {
		//创建一个 装有两个泛型为integer的集合
		Stream<List<Integer>> stream = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5));
		// 将两个合为一个
		Stream<Integer> integerStream = stream.flatMap(
				(Function<List<Integer>, Stream<Integer>>) integers -> integers.stream());
		// 为新的集合
		List<Integer> collect = integerStream.collect(toList());
		System.out.println("新stream大小:"+collect.size());
		System.out.println("-----合并后-----");
		collect.forEach(o -> System.out.println(o));
	}

	/**
	 *  10. findFirst() ：使用该方法获取第一个元素
	 */
	@Test
	public void testFindFirst(){
		User user = list.stream().findFirst().get();
		System.out.println(user);
	}

}
