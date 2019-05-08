package com.moda.permission.spring.boot.filter;

import com.moda.permission.spring.boot.wrapper.CustomHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 重写请求体过滤器
 *
 * @author lyh
 * @date 2019-5-8
 **/
public class RewriteRequestBodyFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(RewriteRequestBodyFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("doFilter...");
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            requestWrapper = new CustomHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }
}
