package com.piaolink.common.filter;

import com.piaolink.admin.entity.OpAdmin;
import com.piaolink.admin.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserFilter implements Filter {
    final Logger logger = LoggerFactory.getLogger(getClass());
    public static Map<String, String> exceptionUrl = new HashMap<>();

    static {
        exceptionUrl.put("/captcha.jpg", "captcha");
        exceptionUrl.put("", "Login URL");
        exceptionUrl.put("", "Logout UEL");
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
    //     HttpServletRequest httpRequest = (HttpServletRequest) request;
    //     String requestUri = httpRequest.getRequestURI();
    //     String contextPath = httpRequest.getContextPath();
    //     String relativeUri = requestUri.replace(contextPath, "");
    //     if (exceptionUrl.get(relativeUri) != null || relativeUri.contains("/user/")) {
    //         HttpServletResponse httpResponse = (HttpServletResponse) response;
    //         HttpSession session = httpRequest.getSession();
    //         try {
    //             OpAdmin opAdmin = (OpAdmin) session.getAttribute("user");
    //             if (opAdmin == null) {
    //                 // httpResponse.sendRedirect(httpRequest.getContextPath());
    //                 httpRequest.getRequestDispatcher("/WEB-INF/views/errorPage/404.jsp").forward(httpRequest, httpResponse);
    //             } else {
    //                 chain.doFilter(request, response);
    //             }
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //         return;
    //     }
    //     chain.doFilter(request, response);
    // }

    public void destroy() {

    }
}
