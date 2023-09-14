package com.wybase.trans.common.util;

import com.wybase.trans.common.consts.BaseConsts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * JWT工具类
 * @author weiyu
 * @date 2023/9/14
 */
public class JwtUtil {

    public static String createToken(String userId, String userName, String tokenSignKey, long tokenExpiration) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] bytes = DatatypeConverter.parseBase64Binary(tokenSignKey);
        Key signingKey = new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setSubject("SRB-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim(BaseConsts.USER_ID, userId)
                .claim(BaseConsts.USER_NAME, userName)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compressWith(CompressionCodecs.GZIP);

        return builder.compact();
    }

    public static String getUserId(String token, String tokenSignKey) {
        Claims claims = getClaims(token, tokenSignKey);
        return (String) claims.get(BaseConsts.USER_ID);
    }

    public static String getUserName(String token, String tokenSignKey) {
        Claims claims = getClaims(token, tokenSignKey);
        return (String) claims.get(BaseConsts.USER_NAME);
    }

    public static void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

    /**
     * 校验token并返回Claims
     * @param token
     * @return
     */
    private static Claims getClaims(String token, String tokenSignKey) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(tokenSignKey))
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }
}
