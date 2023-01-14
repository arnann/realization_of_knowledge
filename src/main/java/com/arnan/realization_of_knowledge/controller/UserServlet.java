package com.arnan.realization_of_knowledge.controller;

import com.alibaba.fastjson2.JSON;
import com.arnan.realization_of_knowledge.pojo.User;
import com.arnan.realization_of_knowledge.service.UserService;
import com.arnan.realization_of_knowledge.service.impl.UserServiceImpl;
import com.arnan.realization_of_knowledge.vo.ResultInfo;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = request.getParameter("actionName");
        if ("register".equals(actionName)) {
            // 用户注册
            register(request, response);
        } else if ("login".equals(actionName)) {
            // 用户登录
            login(request, response);
        } else if ("logout".equals(actionName)) {
            // 用户退出
            logout(request, response);
        } else if ("changePassword".equals(actionName)) {
            // 修改密码
            changePassword(request, response);
        } else if ("selectUserInfo".equals(actionName)) {
            // 查询用户信息
            selectUserInfo(request,response);
        } else if ("updateIntegration".equals(actionName)) {
            // 修改积分
            updateIntegration(request,response);
        }
    }
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ResultInfo<User> resultInfo = userService.register(username, password);
        if (resultInfo.getCode() == 0) {
            request.setAttribute("resultInfo", resultInfo);
            request.getRequestDispatcher("/static/web/register.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/static/web/login.jsp").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ResultInfo<User> resultInfo = userService.login(username, password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (resultInfo.getCode() == 0) {
            // 如果用户输错也要数据回显
            resultInfo.setData(user);
            request.setAttribute("resultInfo", resultInfo);
            request.getRequestDispatcher("/static/web/login.jsp").forward(request, response);
            return;
        }
        user.setUid(userService.selectUserInfo(username).getData().getUid());
        // 数据正常
        request.getSession().setAttribute("user", user);
        // 判断记住我
        String rem = request.getParameter("rem");
        if (rem != null && rem.equals("1")) {
            Cookie cookie = new Cookie("user", username + "-" + password);
            cookie.setMaxAge(3 * 24 * 60 * 60);
            response.addCookie(cookie);
        }
        response.sendRedirect(request.getContextPath() + "/question?actionName=showTypeList&type=kaoyan");
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        // 不要忘记响应给客户端
        response.addCookie(cookie);
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/");
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String newPassword2 = request.getParameter("newPassword2");
        ResultInfo resultInfo = userService.changePassword(user.getUsername(), oldPassword, newPassword, newPassword2);
        if (resultInfo.getCode() == 1) {
            // 修改cookie和session
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("user")) {
                    String username = user.getUsername();
                    Cookie cookie1 = new Cookie("user", username + "-" + newPassword);
                    cookie1.setMaxAge(3 * 24 * 60 * 60);
                    response.addCookie(cookie1);
                }
            }
            user.setPassword(newPassword);
        }
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    private void selectUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User)request.getSession().getAttribute("user");
        ResultInfo<User> resultInfo = userService.selectUserInfo(user.getUsername());
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    private void updateIntegration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rechargePoints = request.getParameter("rechargePoints");
        User user = (User)request.getSession().getAttribute("user");
        ResultInfo resultInfo = userService.updateIntegration(user.getUsername(), Integer.valueOf(rechargePoints));
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
