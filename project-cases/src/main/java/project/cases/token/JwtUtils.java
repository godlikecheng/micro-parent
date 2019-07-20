package project.cases.token;

import io.jsonwebtoken.*;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 *  用于生成Token，和Token验证
 */
public class JwtUtils {

    public static final String JWT_SECERT = "6ACF4742CAB18FEFE053258E300A7B77";

    /**
     *  创建key
     */
    public static SecretKey generalKey() {

        byte[] encodedKey = Base64.decode(JWT_SECERT);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 签发JWT
     * @param subject 可以是JSON数据 尽可能少
     */
    public static String createJWT(String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)   // 主题
                .setIssuer("user")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
//        if (ttlMillis >= 0) {
//            long expMillis = nowMillis + ttlMillis;
//            Date expDate = new Date(expMillis);
//            builder.setExpiration(expDate); // 过期时间
//        }
        return builder.compact();
    }

    /**
     * 验证JWT
     */
    public static Boolean validateJWT(String jwtStr) {
        boolean claims = false;
        try {
            parseJWT(jwtStr);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 解析JWT字符串
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }


}
