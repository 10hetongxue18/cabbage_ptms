package cn.xiaobaicai.cabbage_ptms_backend.util;


import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Jwt工具类，生成JWT和认证
 * @author: hetongxue
 */
public class JwtUtil {
 
    private static final Logger logger = LoggerFactory.getLogger(cn.xiaobaicai.cabbage_ptms_backend.util.JwtUtil.class);
    /**
     * 密钥
     */
    private static final String SECRET = "cabbage";
 
    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 1800L;//单位为秒
 
    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(TbUser user) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                // 添加头部
                .withHeader(map)
                //可以将基本信息放到claims中
                //userId
                .withClaim("id", user.getId())
                //userName
                .withClaim("userName", user.getUserName())
                //userAccount
                .withClaim("userAccount", user.getUserAccount())
                //超时设置,设置过期的日期
                .withExpiresAt(expireDate)
                //签发时间
                .withIssuedAt(new Date())
                //SECRET加密
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }
 
    /**
     * 校验token并解析token
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
 
            //decodedJWT.getClaim("属性").asString()  获取负载中的属性值
 
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("token解码异常");
            //解码异常则抛出异常
            return null;
        }
        return jwt.getClaims();
    }

}