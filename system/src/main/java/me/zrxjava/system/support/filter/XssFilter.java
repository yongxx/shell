package me.zrxjava.system.support.filter;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 防止XSS攻击的过滤器
 *
 * @author void
 */
public class XssFilter implements Filter
{
    /**
     * 排除链接
     */
    public List<String> excludes = new ArrayList<>();

    /**
     * xss过滤开关
     */
    public boolean enabled = false;

    @Override
    public void init(FilterConfig filterConfig) {
        String tempExcludes = filterConfig.getInitParameter("excludes");
        String tempEnabled = filterConfig.getInitParameter("enabled");
        if (StringUtils.isNotEmpty(tempExcludes))
        {
            String[] url = tempExcludes.split(",");
            excludes.addAll(Arrays.asList(url));
        }
        if (StringUtils.isNotEmpty(tempEnabled))
        {
            enabled = Boolean.parseBoolean(tempEnabled);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (handleExcludeURL(req, resp))
        {
            chain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response)
    {
        if (!enabled)
        {
            return true;
        }
        if (excludes == null || excludes.isEmpty())
        {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : excludes)
        {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy()
    {

    }
}