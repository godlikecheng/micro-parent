package project.cases.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  Addition中的请求
 */
@Controller
public class PageController {

	/**
	 *  Netty测试页面跳转
	 */
	@RequestMapping("netty")
	public String nettySkip() {

		return "Addition/Netty/websocket";
	}

	/**
	 *  webSocket测试页面跳转
	 */
	@RequestMapping("webSocket")
	public String webSocketSkip() {

		return "Addition/WebSocket/index";
	}

	/**
	 *  Sse测试页面跳转
	 */
	@RequestMapping("sse")
	public String sseSkip() {

		return "Addition/Sse/sse";
	}


}
