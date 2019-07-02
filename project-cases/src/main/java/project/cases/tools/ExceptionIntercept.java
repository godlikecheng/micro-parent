package project.cases.tools;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *  异常拦截类
 */

//@ControllerAdvice  // 异常处理注解-已关闭(要想开启,打开注解)
public class ExceptionIntercept {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public void InterceptException(Exception e) {

        System.out.println("系统出现了异常或错误,异常信息↓↓↓↓↓");
        e.printStackTrace();

    }

}
