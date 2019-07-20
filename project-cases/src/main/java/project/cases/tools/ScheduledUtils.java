package project.cases.tools;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

/**
 *  定时任务测试类
 */

@RestController
public class ScheduledUtils {

	/**
	 *   定时任务初步测试
	 */
	@Scheduled(cron = "00 43 22 13 * ?")
	public void ScheduledTest() {

		System.out.println("定时初期准备运行....");
	}

	@Scheduled(cron = "15 43 22 13 * ?")
	public void ScheduledTest02() throws InterruptedException {

		for (int i = 0; i < 10000; i ++) {
			System.out.println("运行中................................." + i);
			Thread.sleep(1);
		}
	}

	@Scheduled(cron = "00 44 22 13 * ?")
	public void ScheduledTest03() {

		System.out.println("定时初期准备运行....");
	}

}
