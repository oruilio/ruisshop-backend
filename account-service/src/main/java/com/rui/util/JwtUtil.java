package com.rui.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    private static long tokenExpiration = 1000 * 60 * 60 * 24; //ms数 -- 24h
    //    private static long tokenExpiration = 1000 * 60;
    private static String tokenSignKey = "r1u23i789";

    public static String createToken(Integer id,String mobile){
        String token = Jwts.builder()
                //载荷：自定义信息
                .claim("id", id)
                .claim("mobile", mobile)
                //载荷：默认信息
                .setSubject("uushop-user") //令牌主题
                .setExpiration(new Date(System.currentTimeMillis()+tokenExpiration)) //过期时间
                .setId(UUID.randomUUID().toString())
                //签名哈希
                .signWith(SignatureAlgorithm.HS256, tokenSignKey)
                //组装jwt字符串
                .compact();
        return token;
    }

    //解密，解密过程无异常就可以放行，不用返回具体的解密数值
    public static boolean checkToken(String token){
        if(StringUtils.isEmpty(token)){
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
