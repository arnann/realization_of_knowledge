package com.arnan.realization_of_knowledge.controller;

import com.alibaba.fastjson2.JSON;
import com.arnan.realization_of_knowledge.dao.UserDao;
import com.arnan.realization_of_knowledge.dao.impl.UserDaoImpl;
import com.arnan.realization_of_knowledge.pojo.Question;
import com.arnan.realization_of_knowledge.pojo.User;
import com.arnan.realization_of_knowledge.service.QuestionService;
import com.arnan.realization_of_knowledge.service.impl.QuestionServiceImpl;
import com.arnan.realization_of_knowledge.vo.PageInfo;
import com.arnan.realization_of_knowledge.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {
    private QuestionService questionService = new QuestionServiceImpl();

    private UserDao userDao = new UserDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = request.getParameter("actionName");
        if("save".equals(actionName)){
            // 发布问题
            saveQuestion(request,response);
        }else if ("delete".equals(actionName)){
            // 删除问题
            deleteQuestion(request,response);
        }else if("showTypeList".equals(actionName)){
            // 分类查询
            showTypeList(request,response);
        } else if ("showMyQuestions".equals(actionName)) {
            // 我的问题
            showMyQuestions(request,response);
        }else if("selectQuestionByQid".equals(actionName)){
            // 查询问题
            selectQuestionByQid(request,response);
        } else if ("updateQuestionByQid".equals(actionName)) {
            // 修改问题
            updateQuestionByQid(request,response);
        }
    }

    private void updateQuestionByQid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String qid = request.getParameter("qid");
        String content = request.getParameter("content");
        ResultInfo resultInfo = questionService.updateQuestionByQid(Integer.valueOf(qid), content);
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    private void showTypeList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String type = request.getParameter("type");
        Integer pageNum = 1;
        String pageNumStr = request.getParameter("pageNum");
        if(pageNumStr != null && !"".equals(pageNumStr)) pageNum = Integer.valueOf(pageNumStr);
        PageInfo pageInfo = questionService.showTypeList(type, pageNum,4);
        request.setAttribute("type", type);
        request.setAttribute("pageInfo", pageInfo);
        request.getRequestDispatcher("/static/web/index.jsp?type=" + type + "&pageNum=" + pageNum).forward(request,response);
    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String qid = request.getParameter("qid");
        ResultInfo resultInfo = questionService.deleteQuestion(Integer.valueOf(qid));
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    private void saveQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        User user = (User)request.getSession().getAttribute("user");
        User user1 = userDao.selectByUsername(user.getUsername());
        ResultInfo resultInfo = questionService.saveQuestion(user1.getUid(),title, content, type);
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    private void showMyQuestions(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User)request.getSession().getAttribute("user");
        Integer pageNum = 1;
        String pageNumStr = request.getParameter("pageNum");
        if(pageNumStr != null && !pageNumStr.equals("")) pageNum = Integer.valueOf(pageNumStr);
        PageInfo pageInfo = questionService.showMyQuestions(user.getUsername(), pageNum, 5);
        request.setAttribute("pageInfo", pageInfo);
        request.getRequestDispatcher("/static/web/apply.jsp?pageNum=" + pageNum).forward(request,response);
    }

    private void selectQuestionByQid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String qid = request.getParameter("qid");
        ResultInfo<Question> resultInfo = questionService.selectQuestionByQid(Integer.valueOf(qid));
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
