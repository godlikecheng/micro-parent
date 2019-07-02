package project.cases.thread;

/**
 *  多线程案例之 - 前台线程
 */
public class NewThread implements Runnable {

	public void run() {
		try {
			for(int i = 0; i < 5; i ++) {
				Thread th = Thread.currentThread();  // 获取当前线程
				String th_name = th.getName();  // 获取当前线程名字
				System.out.println("方法线程名称:" + th_name + " || 第" + i + "个");
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("=============方法线程结束============");
	}
}

