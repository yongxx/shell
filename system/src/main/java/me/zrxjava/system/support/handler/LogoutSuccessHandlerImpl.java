package me.zrxjava.system.support.handler;

import me.zrxjava.common.base.ResponseResult;
import me.zrxjava.common.utils.ServletUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出登录处理器
 * @author void
 * @create 2021-01-11
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ServletUtils.renderString(response, ResponseResult.success("退出登录成功"));
    }
}
