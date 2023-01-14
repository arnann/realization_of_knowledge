package com.arnan.realization_of_knowledge.controller;

import com.alibaba.fastjson2.JSON;
import com.arnan.realization_of_knowledge.pojo.User;
import com.arnan.realization_of_knowledge.service.UserService;
import com.arnan.realization_of_knowledge.service.impl.UserServiceImpl;
import com.arnan.realization_of_knowledge.vo.ResultInfo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet("/userUpload")
public class UserUploadServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        // 1.使用ServletFileUpload类解析文件
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        servletFileUpload.setHeaderEncoding("UTF-8");
        String tempUserName = null;
        try {
            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            // 2.遍历解析后的请求，判断每一个FileItem
            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {
                    // 普通表单项
                    if (fileItem.getFieldName().equals("username")) {
                        user.setUsername(fileItem.getString("UTF-8"));
                        tempUserName = fileItem.getString("UTF-8");
                    }
                    if (fileItem.getFieldName().equals("age"))
                        user.setAge(Integer.valueOf(fileItem.getString("UTF-8")));
                    if (fileItem.getFieldName().equals("gender"))
                        user.setGender(Integer.parseInt(fileItem.getString("UTF-8")));
                    if (fileItem.getFieldName().equals("classnum")) user.setClassnum(fileItem.getString("UTF-8"));
                    if (fileItem.getFieldName().equals("introduction"))
                        user.setIntroduction(fileItem.getString("UTF-8"));
                } else {
                    // 文件
                    String realPath = request.getSession().getServletContext().getRealPath("/static/upload/");
                    String fileName = fileItem.getName();
                    File file = new File(realPath, fileName);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                    // 写入
                    InputStream inputStream = fileItem.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(file);
                    int len = -1;
                    while ((len = inputStream.read()) != -1) {
                        outputStream.write(len);
                    }
                    user.setAvatar(fileName);
                    outputStream.close();
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 调用业务层
        User user1 = (User) request.getSession().getAttribute("user");
        ResultInfo resultInfo = userService.updateUserInfo(user, user1.getUsername());
        // 重设cookie和session
        user1.setUsername(tempUserName);
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")){
                String[] values = cookie.getValue().split("-");
                Cookie cookie1 = new Cookie("user", tempUserName + "-" + values[1]);
                cookie.setMaxAge(3 * 24 * 60 * 60);
                response.addCookie(cookie1);
            }
        }
        // 响应给前端JSON数据
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
