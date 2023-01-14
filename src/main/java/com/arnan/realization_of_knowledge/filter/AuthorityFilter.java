package com.arnan.realization_of_knowledge.filter;

import com.arnan.realization_of_knowledge.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/*")
public class AuthorityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 拦截非法请求
        String uri = request.getRequestURI();
        if(uri.contains("static/bootstrap3") || uri.contains("static/js") || uri.contains("static/img")
        || uri.contains("static/js") || uri.contains("static/css")){
            filterChain.doFilter(request,response);
            return;
        }
        String s = uri.substring(uri.lastIndexOf("/") + 1);
        if (uri.contains("login") || uri.contains("register") || s.equals("")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (uri.contains("/user")) {
            String actionName = request.getParameter("actionName");
            if ("login".equals(actionName) || "register".equals(actionName)) {
                // 这里不需要logout用户行为 因为session中含有user
                filterChain.doFilter(request, response);
                return;
            }
        }
        HttpSession session = request.getSession();
        if ((User) session.getAttribute("user") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 自动登录，判断是否是选择记住我的用户
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    String[] split = cookie.getValue().split("-");
                    String username = split[0];
                    String password = split[1];
                    String path = "/user?actionName=login&username=" + username + "&password=" + password;
                    request.getRequestDispatcher(path).forward(request, response);
                    return;
                }
            }
        }

        // 最后，要放行用户首次访问得页面
        if (uri.equals("/")) {
            filterChain.doFilter(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/static/web/login.jsp");
    }
}
