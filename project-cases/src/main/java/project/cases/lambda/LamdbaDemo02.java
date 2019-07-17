package project.cases.lambda;

public class LamdbaDemo02 {

		public static void main(String[] args) {
		// 1.1使用匿名内部类
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello world 1 !");
			}
		}).start();
		System.out.println("-----------------------------");

		// 1.2使用 lambda expression
		new Thread(() -> System.out.println("Hello world 2 !")).start();
		System.out.println("-----------------------------");

		// 2.1使用匿名内部类
		Runnable race1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello world 3 !");
			}
		};
		System.out.println("-----------------------------");

		// 2.2使用 lambda expression
		Runnable race2 = () -> System.out.println("Hello world 4 !");
		System.out.println("-----------------------------");

		// 直接调用 run 方法(没开新线程哦!)
		race1.run();
		race2.run();


	}
}
