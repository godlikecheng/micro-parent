package project.cases.thread;

/**
 *  多线程案例之 - 后台线程
 *  线程有两种：前台线程和后台线程。
 *  区别是：应用程序必须运行完所有的前台线程才可以退出；而对于后台线程，应用程序则可以不考虑其是否已经运行完毕而直接退出，所有的后台线程在应用程序退出时都会自动结束。
 */
public class DamonThread implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 100; i ++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "-------- is running...");
		}

	}



}
