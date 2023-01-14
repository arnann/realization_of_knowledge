package com.arnan.realization_of_knowledge.service.impl;

import com.arnan.realization_of_knowledge.dao.AnswerDao;
import com.arnan.realization_of_knowledge.dao.PaymentDao;
import com.arnan.realization_of_knowledge.dao.QuestionDao;
import com.arnan.realization_of_knowledge.dao.UserDao;
import com.arnan.realization_of_knowledge.dao.impl.AnswerDaoImpl;
import com.arnan.realization_of_knowledge.dao.impl.PaymentDaoImpl;
import com.arnan.realization_of_knowledge.dao.impl.QuestionDaoImpl;
import com.arnan.realization_of_knowledge.dao.impl.UserDaoImpl;
import com.arnan.realization_of_knowledge.pojo.Answer;
import com.arnan.realization_of_knowledge.pojo.Payment;
import com.arnan.realization_of_knowledge.pojo.Question;
import com.arnan.realization_of_knowledge.service.AnswerService;
import com.arnan.realization_of_knowledge.vo.PageInfo;
import com.arnan.realization_of_knowledge.vo.ResultInfo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class AnswerServiceImpl implements AnswerService {
    private AnswerDao answerDao = new AnswerDaoImpl();
    private QuestionDao questionDao = new QuestionDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private PaymentDao paymentDao = new PaymentDaoImpl();
    @Override
    public ResultInfo saveAnswer(Answer answer) {
        // 修改问题状态
        Integer integer = questionDao.updateStateByQid(answer.getQid(), 1);
        if(integer == null){
            return new ResultInfo(0,"修改问题状态异常~");
        }
        // 设置回复的Aid
        LocalDateTime localDateTime = LocalDateTime.now();
        answer.setAid(localDateTime.toInstant(ZoneOffset.ofHours(5)).toEpochMilli());
        // 修改问题的Aid
        Integer integer1 = questionDao.updateAidByQid(answer.getQid(), answer.getAid());
        if(integer1 == null){
            return new ResultInfo(0,"修改问题Aid异常~");
        }
        // 创建订单
        Payment payment = new Payment();
        payment.setPid(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        payment.setAid(answer.getAid());
        payment.setUid(questionDao.selectQuestionByQid(answer.getQid()).getUid());
        payment.setResponder( userDao.selectByUid(answer.getUid()).getUsername());
        payment.setState(1);
        payment.setCreate_time(LocalDateTime.now());
        Integer integer2 = paymentDao.savePayment(payment);
        if(integer2 == null){
            return new ResultInfo(0,"订单创建异常~");
        }
        // 保存问题
        Integer rows = answerDao.saveAnswer(answer);
        if(rows != null){
            return new ResultInfo<>(1,"回复成功~");
        }
        return new ResultInfo(0,"回复失败~");
    }

    @Override
    public ResultInfo updateAnswer(Long aid, String answer) {
        Question question = questionDao.selectQuestionByQid(answerDao.selectAnswerByAid(aid).getQid());
        if(question.getState() == 2){
            return new ResultInfo(0,"已支付订单不能再修改~");
        }
        Integer rows = answerDao.updateAnswer(aid, answer);
        if(rows != null){
            return new ResultInfo(1,"修改成功~");
        }
        return new ResultInfo(0,"修改失败~");
    }

    @Override
    public PageInfo<Answer> showMyAnswers(Integer uid,Integer pageNum,Integer pageSize) {
        Integer totalCount = answerDao.getTotalCount(uid);
        if(totalCount == 0) return null;
        Integer pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        if(pageNum <= 1) pageNum = 1;
        if(pageNum >= pageCount) pageNum = pageCount;
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageCount(pageCount);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotalRecorder(totalCount);
        List<Answer> list = answerDao.selectAnswersByUid(uid, pageNum, pageSize);
        pageInfo.setData(list);
        return pageInfo;
    }

    @Override
    public ResultInfo selectAnswerByAid(Long aid) {
        Answer answer = answerDao.selectAnswerByAid(aid);
        if(answer != null){
            return new ResultInfo(1,answer);
        }
        return new ResultInfo(0,"查询回复内容时发生异常！");
    }

    @Override
    public ResultInfo deleteAnswerByAid(Long aid) {
        // 修改该回复对应的问题状态，修改为待解决
        Answer answer = answerDao.selectAnswerByAid(aid);
        Integer integer = questionDao.updateStateByQid(answer.getQid(), 0);
        if(integer == null){
            return new ResultInfo(0,"修改问题状态时发生了异常！");
        }
        // 删除产生的订单
        Integer integer1 = paymentDao.deleteByAid(aid);
        if(integer1 == null){
            return new ResultInfo(0,"删除订单时发生了异常！");
        }
        Integer integer2 = answerDao.deleteAnswerByAid(aid);
        if(integer1 != null){
            return new ResultInfo(1,"删除成功！");
        }
        return new ResultInfo(0,"删除失败！");
    }
}
