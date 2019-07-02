package project.cases.upload;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件测试类
 */


@RestController
public class ImgUploadControllerTest {

	/**
	 * 图片上传
	 */
	@PostMapping(value = "/imgUpload")
	public String uploadImg(@RequestParam("image") MultipartFile img) {
		try {
			// 图片上传调用工具类 - 保存图片
			String path = FileUtil.saveImg(img);
			return path;
		} catch (Exception e) {
			return "上传图片失败";
		}
	}

}
