package project.cases.upload;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 图片映射配置类
 * WebMvcConfigurer(官方推荐) WebMvcConfigurationSupport 都可
 */
//@Configuration   // 打开注释则"图片映射配置"生效, 注释不生效 开启此处注解可能会造成静态资源加载不出来
public class WebMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * 配置的图片映射
	 */
	private static final String imgPath = "file:" + Constant.UPLOAD_PATH + Constant.IMG_FILE_NAME + "\\";

	/**
	 * 路径映射
	 * 所有访问upload/img/**的请求映射到文件上传的路径下 C:\Users\lenovo/upload/img（图片的保存路径）
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("\\upload\\img\\**").addResourceLocations(imgPath);
		super.addResourceHandlers(registry);
	}

}
