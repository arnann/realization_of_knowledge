package com.arnan.realization_of_knowledge.controller;

import com.alibaba.fastjson.JSON;
import com.arnan.realization_of_knowledge.pojo.Payment;
import com.arnan.realization_of_knowledge.pojo.User;
import com.arnan.realization_of_knowledge.service.PaymentService;
import com.arnan.realization_of_knowledge.service.impl.PaymentServiceImpl;
import com.arnan.realization_of_knowledge.vo.PageInfo;
import com.arnan.realization_of_knowledge.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private PaymentService paymentService = new PaymentServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = request.getParameter("actionName");
        if("showMyPayments".equals(actionName)){
            // 展示我的订单
            showMyPayments(request,response);
        } else if ("pay".equals(actionName)) {
            // 支付
            pay(request,response);
        } else if ("delete".equals(actionName)) {
            // 删除订单
            deletePaymentByPid(request,response);
        }
    }

    private void deletePaymentByPid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        ResultInfo resultInfo = paymentService.deleteByPid(Long.valueOf(pid));
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    private void pay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String payOption = request.getParameter("payOption");
        String pid = request.getParameter("pid");
        User user = (User) request.getSession().getAttribute("user");
        ResultInfo resultInfo = paymentService.pay(user.getUid(),Long.valueOf(pid),Integer.valueOf(payOption));
        response.getWriter().write(JSON.toJSONString(resultInfo));
        response.getWriter().close();
    }

    private void showMyPayments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer pageNum = 1;
        String pageNumStr = request.getParameter("pageNum");
        if(pageNumStr != null && !pageNumStr.equals("")) pageNum = Integer.valueOf(pageNumStr);
        User user = (User)request.getSession().getAttribute("user");
        PageInfo<Payment> pageInfo = paymentService.showMyPayments(pageNum, 5, user.getUid());
        request.setAttribute("pageInfo", pageInfo);
        request.getRequestDispatcher("/static/web/payment.jsp?pageNum=" + pageNum).forward(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
