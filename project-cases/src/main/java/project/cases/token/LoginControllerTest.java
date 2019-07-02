/*
package project.cases.token;

import kim.chengcheng.goods.dao.UsersDao;
import kim.chengcheng.goods.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

*/
/**
 *  测试Token - JWT签发
 *//*


@RestController
public class LoginControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);

    @Autowired
    UsersDao usersDao;

    // 测试用,如果正式使用则存放到数据库或者是redis中
    private static Map<String,Object> TokenValidation =  new HashMap<String, Object>();

    */
/**
     *  用户登录
     *//*

    @RequestMapping(value="loginTokenTest",method = RequestMethod.POST)
    public Result login(String username, String password,HttpServletResponse
            response) {
        Users users =  usersDao.selectUsers(username);
        if(users != null){
            if(users.getPassword().equals(password)){
                //把token返回给客户端-->客户端保存至cookie-->客户端每次请求附带cookie参数
                String JWT = JwtUtils.createJWT(username);
                TokenValidation.put(JWT, users);  // 存储Toekn
                return Result.ok(TokenValidation);
            }else{
                return Result.fail("500", "用户名或密码错误,请重试");
            }
        }else{
            return Result.fail("500", "无此用户!");
        }
    }

    */
/**
     * 获取用户信息
     *//*

    @RequestMapping(value="selectTestsUsers",method = RequestMethod.POST)
    public Result description(String username, HttpServletRequest request) {

        // 从前端获取Header请求头
        String token = request.getHeader("authorization");
        if (null == token || "".equals(token)) {
            return Result.fail("500", "Token不能为空,请重新登录!!");
        }

        try {
            // 从前端获取到Token令牌去对应的存储位置取
            Object users =  TokenValidation.get(token);
            return Result.ok(users.toString());
        } catch (Exception e) {
            // 如果前端传入的Token令牌和存入的Token令牌不一致,会导致TOken验证失败
            return Result.ok("500", "Token验证失败,请重新登录!!");
        }

    }
}
*/
