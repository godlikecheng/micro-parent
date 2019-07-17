package project.cases.thread;

/**
 *  线程测试
 */
public class Test {

	// NewThread.java
	/*public static void main(String args[]) {
		NewThread newThread = new NewThread();  // 创建一个新线程
		thread thread = new thread(newThread, "线程1");
		thread.start();
		try {
			for (int i = 0; i < 5; i ++) {
				System.out.println("主函数线程: " + i);
				thread.sleep(200);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("==============主函数线程结束=============.");
	}*/

	// DamonThread.java - 后台线程测试
	/*public static void main(String[] args) {
		System.out.println("Main线程是后台线程吗?" + thread.currentThread().isDaemon());  // 判断是否是后台线程
		DamonThread dt = new DamonThread();
		thread thread = new thread(dt, "后台线程");
		System.out.println("thread线程是后台线程吗?" + thread.currentThread().isDaemon());
		thread.setDaemon(true);  // 将线程thread设置成后台线程  该设置必须要在该线程启动之前
		thread.start();

		// Main中的循环
		for (int i = 0; i < 10; i ++) {
			System.out.println("前台线程:" + i);
		}
	}*/

	// Ticket.java
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		new Thread(ticket, "线程一").start();
		new Thread(ticket, "线程二").start();
		new Thread(ticket, "线程三").start();
		new Thread(ticket, "线程四").start();
	}

}
