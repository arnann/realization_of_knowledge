package com.arnan.realization_of_knowledge.controller;

import com.alibaba.fastjson2.JSON;
import com.arnan.realization_of_knowledge.pojo.Answer;
import com.arnan.realization_of_knowledge.pojo.User;
import com.arnan.realization_of_knowledge.service.AnswerService;
import com.arnan.realization_of_knowledge.service.impl.AnswerServiceImpl;
import com.arnan.realization_of_knowledge.vo.PageInfo;
import com.arnan.realization_of_knowledge.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/answer")
public class AnswerServlet extends HttpServlet {
    private AnswerService answerService = new AnswerServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = request.getParameter("actionName");
        if("save".equals(actionName)){
            // 保存回复
            saveAnswer(request,response);
        } else if ("update".equals(actionName)) {
            // 修改回复
            updateAnswer(request,response);
        } else if ("showMyAnswers".equals(actionName)) {
            // 展示我的回复
            showMyAnswers(request,response);
        } else if ("selectAnswerByAid".equals(actionName)) {
            // 查询回复
            selectAnswerByAid(request,response);
        } else if ("deleteAnswerByAid".equals(actionName)) {
            // 删除问题
            deleteAnswerByAid(request,response);
        }
    }

    private void deleteAnswerByAid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String aid = request.getParameter("aid");
        ResultInfo resultInfo = answerService.deleteAnswerByAid(Long.valueOf(aid));
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    private void selectAnswerByAid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String aid = request.getParameter("aid");
        ResultInfo resultInfo = answerService.selectAnswerByAid(Long.valueOf(aid));
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    private void showMyAnswers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer pageNum = 1;
        String pageNumStr = request.getParameter("pageNum");
        if(pageNumStr != null && !pageNumStr.equals("")) pageNum = Integer.valueOf(pageNumStr);
        User user = (User)request.getSession().getAttribute("user");
        PageInfo<Answer> pageInfo = answerService.showMyAnswers(user.getUid(), pageNum, 5);
        request.setAttribute("pageInfo", pageInfo);
        request.getRequestDispatcher("/static/web/apply_answer.jsp?pageNum=" + pageNum).forward(request,response);
    }

    private void saveAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User)request.getSession().getAttribute("user");
        Answer answer = new Answer();
        Integer uid = user.getUid();
        answer.setUid(uid);
        answer.setQid(Integer.valueOf(request.getParameter("qid")));
        answer.setQuestion(request.getParameter("question"));
        answer.setAnswer(request.getParameter("answer"));
        ResultInfo resultInfo = answerService.saveAnswer(answer);
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    private void updateAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String aid = request.getParameter("aid");
        String answer = request.getParameter("answer");
        ResultInfo resultInfo = answerService.updateAnswer(Long.valueOf(aid), answer);
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
