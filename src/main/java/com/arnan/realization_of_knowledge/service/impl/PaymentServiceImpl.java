package com.arnan.realization_of_knowledge.service.impl;

import com.arnan.realization_of_knowledge.dao.AnswerDao;
import com.arnan.realization_of_knowledge.dao.PaymentDao;
import com.arnan.realization_of_knowledge.dao.QuestionDao;
import com.arnan.realization_of_knowledge.dao.UserDao;
import com.arnan.realization_of_knowledge.dao.impl.AnswerDaoImpl;
import com.arnan.realization_of_knowledge.dao.impl.PaymentDaoImpl;
import com.arnan.realization_of_knowledge.dao.impl.QuestionDaoImpl;
import com.arnan.realization_of_knowledge.dao.impl.UserDaoImpl;
import com.arnan.realization_of_knowledge.pojo.Payment;
import com.arnan.realization_of_knowledge.pojo.User;
import com.arnan.realization_of_knowledge.service.PaymentService;
import com.arnan.realization_of_knowledge.vo.PageInfo;
import com.arnan.realization_of_knowledge.vo.ResultInfo;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private PaymentDao paymentDao = new PaymentDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private QuestionDao questionDao = new QuestionDaoImpl();
    private AnswerDao answerDao = new AnswerDaoImpl();

    @Override
    public PageInfo<Payment> showMyPayments(Integer pageNum, Integer pageSize, Integer uid) {
        Integer totalCount = paymentDao.getCountByUid(uid);
        if (totalCount == 0) return null;
        Integer pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        if(pageNum <= 1) pageNum = 1;
        if(pageNum > pageCount) pageNum = pageCount;
        List<Payment> payments = paymentDao.selectAllByUid(pageNum, pageSize, uid);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageCount(pageCount);
        pageInfo.setTotalRecorder(totalCount);
        pageInfo.setData(payments);
        return pageInfo;
    }

    @Override
    public ResultInfo pay(Integer uid, Long pid, Integer payOption) {
        // 判断积分是否足够，交易
        User user = userDao.selectByUid(uid);
        Integer integration = user.getIntegration();
        if(integration < payOption){
            return new ResultInfo(0,"积分余额不足，请充值~");
        }
        integration -= payOption;
        Integer rows = userDao.updateIntegrationByUid(uid,integration);
        if(rows == null){
            return new ResultInfo(0,"更新您的积分时发生异常！");
        }
        String responder = paymentDao.selectByPid(pid).getResponder();
        Integer integration1 = userDao.selectByUsername(responder).getIntegration();
        Integer integer = userDao.updateIntegration(paymentDao.selectByPid(pid).getResponder(), integration1 + payOption);
        if(integer == 0){
            return new ResultInfo(0,"更新回复者的积分时发生异常！");
        }
        // 修改订单状态和支付时间
        Integer integer1 = paymentDao.updateStateAndPayTimeByPid(pid, 2, LocalDateTime.now());
        if(integer1 == null){
            return new ResultInfo(0,"更新订单状态和支付时间时发生异常！");
        }
        // 修改问题状态
        Integer integer2 = questionDao.updateStateByQid(answerDao.selectAnswerByAid(paymentDao.selectByPid(pid).getAid()).getQid(), 2);
        if(integer2 == null){
            return new ResultInfo(0,"更新问题状态时发生异常！");
        }
        return new ResultInfo(1,"支付成功！");
    }

    @Override
    public ResultInfo deleteByPid(Long pid) {
        // 未支付订单不能删除
        if(paymentDao.selectByPid(pid).getState() == 1){
            return new ResultInfo<>(0,"未支付订单不能删除~");
        }
        Integer integer = paymentDao.deleteByPid(pid);
        if(integer != 1){
            return new ResultInfo(0,"删除失败！");
        }
        return new ResultInfo(1,"删除成功！");
    }

}
