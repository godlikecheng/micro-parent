package project.cases.upload;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 *  文件上传工具类
 */
public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 图片存储 完整路径（{user.home}\img\coldStone\XXX.jpg）
	 * @return 返回相对路径
	 */
	public static String saveImg(MultipartFile file) {

		// 获取文件上传的根目录 C:/Users/lenovo/upload/img
//		String path = Constant.UPLOAD_PATH + Constant.IMG_FILE_NAME;  // 绝对路径

//		String saveName = UUID.randomUUID().toString() + "." + getFileSuffix(file.getOriginalFilename());
		String saveName = UUID.randomUUID().toString() + "-" + getFileSuffix(file.getOriginalFilename());

		String resultType = judgeType(saveName);
		String path = Constant.UPLOAD_PATH + resultType;

		logger.info(" --- 图片保存路径：{}, 图片保存名称：{} --- ", path, saveName);

		// 保存
		try {
			// 保存文件图片
			File targetFile = new File(path);

			//  如果该目录不存在则创建
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 创建新文件
			file.transferTo(new File(path + "\\" + saveName));
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("--- 图片保存异常：{} ---" + e.getMessage());
			return null;
		}

		String filePath =  Constant.VIRTUAL_IMG_PATH;  // 相对路径

		return filePath + "\\" + saveName;  //返回相对路径 + 文件名  upload\img\01dd02e5-f225-4b81-9a20-65ab06c9d3f6.jpg
	}

	/**
	 * 返回截取的文件后缀
	 */
	private static String getFileSuffix(String path) {
		return getFileSuffix(path, "2");
	}

	/**
	 * 获取文件名称或后缀(最后一个"."之后内容)
	 * @param type 1名称 2后缀
	 */
	private static String getFileSuffix(String path, String type){
		if(StringUtils.isNotEmpty(path) && path.indexOf(".") > 0) {
			String name = path.substring(0, path.lastIndexOf("."));  // 文件名称
			String suffix = path.substring(path.lastIndexOf(".") + 1);  // 文件后缀

//			return StringUtils.equals("1", type) ? name : suffix;
			return name + '.' + suffix;
		} else {
			return null;
		}
	}

	/**
	 *  判断文件的类型
	 */
	private static String judgeType( String saveName) {

		String suffix = saveName.substring(saveName.lastIndexOf(".") + 1);  // 文件后缀
		switch(suffix) {
			case "txt":
				return "txt";
			case "jpg":
				return "img";
			case "png":
				return "img";
			case "rar":
				return "zip";
			case "zip":
				return "zip";
			default:
				return "other";
		}
	}


}
