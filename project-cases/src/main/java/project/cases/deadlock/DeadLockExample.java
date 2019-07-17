package project.cases.deadlock;

/**
 *  产生死锁的代码
 */
public class DeadLockExample {
	public static void main(String[] args) {
		final String resource1 = "ABAP";
		final String resource2 = "Java";
		Thread t1 = new Thread() {
			public void run() {
				synchronized (resource1) {
					System.out.println("thread 1: locked resource 1");
					try {
						Thread.sleep(100);
					}
					catch (Exception e) {
					}
					synchronized (resource2) {
						System.out.println("thread 1: locked resource 2");
					}
				}
			}
		}
				;
		Thread t2 = new Thread() {
			public void run() {
				synchronized (resource2) {
					System.out.println("thread 2: locked resource 2");
					try {
						Thread.sleep(100);
					}
					catch (Exception e) {
					}
					synchronized (resource1) {
						System.out.println("thread 2: locked resource 1");
					}
				}
			}
		}
				;
		t1.start();
		t2.start();
	}
}
