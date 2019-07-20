package project.cases.tools;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  日志级别
 * Logback是log4j框架的作者开发的新一代日志框架，它效率更高、能够适应诸多的运行环境，同时天然支持SLF4J。SpringBoot默认使用Logback
 * TRACE 很低的日志级别，一般不会使用。
 * DEBUG 指出细粒度信息事件对调试应用程序是非常有帮助的，主要用于开发过程中打印一些运行信息。
 * INFO  消息在粗粒度级别上突出强调应用程序的运行过程。打印一些你感兴趣的或者重要的信息，这个可以用于生产环境中输出程序运行的一些重要信息，但是不能滥用，避免打印过多的日志。
 * WARN  表明会出现潜在错误的情形，有些信息不是错误信息，但是也要给程序员的一些提示。
 * ERROR 指出虽然发生错误事件，但仍然不影响系统的继续运行。打印错误和异常信息，如果不想输出太多的日志，可以使用这个级别。
 * FATAL 指出每个严重的错误事件将会导致应用程序的退出。这个级别比较高了。重大错误，这种级别你可以直接停止程序了。
 */

@RestController
public class Logger {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

	/**
	 *  日志打印
	 */
	@RequestMapping("logger")
	public void setLogger() {

		logger.trace("很低的日志级别，一般不会使用。");
		logger.debug("DEBUG 指出细粒度信息事件对调试应用程序是非常有帮助的，主要用于开发过程中打印一些运行信息。");
		logger.info("INFO  消息在粗粒度级别上突出强调应用程序的运行过程。打印一些你感兴趣的或者重要的信息，但是不能滥用，避免打印过多的日志。");
		logger.warn("WARN  表明会出现潜在错误的情形，有些信息不是错误信息，但是也要给程序员的一些提示。");
		logger.error("指出虽然发生错误事件，但仍然不影响系统的继续运行。打印错误和异常信息，如果不想输出太多的日志，可以使用这个级别。");

	}

}
