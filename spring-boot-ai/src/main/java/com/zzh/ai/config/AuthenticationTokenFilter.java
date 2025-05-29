package com.zzh.ai.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * 拦截器
 */
@Component
@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        //得到请求头信息authorization信息
        String accessToken = request.getHeader("Authorization");
        if (accessToken != null) {
            //从Redis中获取内容
//            String userInfo = stringRedisTemplate.opsForValue().get(RedisConf.USER_TOKEN + Constants.SYMBOL_COLON + accessToken);
//            if (!StringUtils.isEmpty(userInfo)) {
//                Map<String, Object> map = JsonUtils.jsonToMap(userInfo);
//                //把userUid存储到 request中
//                request.setAttribute(SysConf.TOKEN, accessToken);
//                request.setAttribute(SysConf.USER_UID, map.get(SysConf.UID));
//                request.setAttribute(SysConf.USER_NAME, map.get(SysConf.NICK_NAME));
//                log.info("解析出来的用户:{}", map.get(SysConf.NICK_NAME));
//            }

            // 构建认证对象
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    "admin", null, Collections.emptyList() // 你也可以提供权限
            );
            // 把认证信息注入上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}


