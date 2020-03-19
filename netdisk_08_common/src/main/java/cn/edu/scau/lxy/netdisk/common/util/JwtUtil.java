package cn.edu.scau.lxy.netdisk.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * @author linxinying
 * @description 令牌工具类，包含令牌生成和解析。
 *              需要用到该工具类的微服务对应的配置文件必须定义“jwt.config.key”，
 *              “jwt.config.ttl”可以不定义
 * @date 2020/3/16 20:28
 */
@Data
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    private String key;  //盐

    private long ttl;  //过期时间

    //生成jwt
    public String createJWT(String id,String subject,String roles){
        long currMillis=System.currentTimeMillis();
        Date now=new Date(currMillis);
        JwtBuilder builder= Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256,key)
                .claim("roles",roles);
        if(ttl>0){
            builder.setExpiration(new Date(currMillis + ttl));
        }
        return builder.compact();
    }

    //解析jwt
    public Claims parseJWT(String jwtStr){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }
}
