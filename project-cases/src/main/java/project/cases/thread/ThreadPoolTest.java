package project.cases.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FixedThreadPool线程池测试
 * 此包下单独的一个人java测试类,与其他的没有任何关系
 */
public class ThreadPoolTest {

	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			final int index = i;
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(index);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}


	}
}
