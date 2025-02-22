package me.zrxjava.sercurity.component;

import me.zrxjava.common.base.ResponseResult;
import me.zrxjava.common.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author void
 * @create 2020-09-17
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
        ServletUtils.renderString(response,ResponseResult.failed(HttpServletResponse.SC_UNAUTHORIZED,authException==null?"Unauthorized":authException.getMessage()));
    }
}
