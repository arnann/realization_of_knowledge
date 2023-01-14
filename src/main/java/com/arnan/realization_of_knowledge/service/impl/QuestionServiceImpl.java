package com.arnan.realization_of_knowledge.service.impl;

import com.arnan.realization_of_knowledge.dao.PaymentDao;
import com.arnan.realization_of_knowledge.dao.QuestionDao;
import com.arnan.realization_of_knowledge.dao.UserDao;
import com.arnan.realization_of_knowledge.dao.impl.PaymentDaoImpl;
import com.arnan.realization_of_knowledge.dao.impl.QuestionDaoImpl;
import com.arnan.realization_of_knowledge.dao.impl.UserDaoImpl;
import com.arnan.realization_of_knowledge.pojo.Question;
import com.arnan.realization_of_knowledge.service.QuestionService;
import com.arnan.realization_of_knowledge.vo.MyQuestionsVo;
import com.arnan.realization_of_knowledge.vo.PageInfo;
import com.arnan.realization_of_knowledge.vo.ResultInfo;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private QuestionDao questionDao = new QuestionDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private PaymentDao paymentDao = new PaymentDaoImpl();
    @Override
    public ResultInfo saveQuestion(Integer uid,String title, String content, String type) {
        Integer paymentCount = paymentDao.getNonPaymentCountByUid(uid);
        if(paymentCount >= 5){
            return new ResultInfo(0,"您的未支付订单已存在5个，请先处理订单！");
        }
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setType(type);
        question.setUid(uid);
        question.setState(0);
        Integer integer = questionDao.saveQuestion(question);
        if(integer == 1){
            return new ResultInfo<>(1,"保存成功");
        }
        return new ResultInfo(0,"保存失败！");
    }

    @Override
    public PageInfo<Question> showTypeList(String type, Integer pageNum, Integer pageSize) {
        PageInfo<Question> pageInfo = new PageInfo();
        // 获取总记录
        Integer totalRecorder = questionDao.getTotalCount(type);
        if(totalRecorder == 0) return null;
        pageInfo.setTotalRecorder(totalRecorder);
        // 设置每页记录数
        pageInfo.setPageSize(pageSize);
        // 设置总页数
        Integer pageCount = totalRecorder % pageSize == 0 ? totalRecorder / pageSize : (totalRecorder / pageSize + 1);
        pageInfo.setPageCount(pageCount);
        // 设置当前页
        if(pageNum <= 1) pageNum = 1;
        if(pageNum > pageCount) pageNum = pageCount;
        pageInfo.setPageNum(pageNum);
        // 设置查询到的数据
        List<Question> list = questionDao.selectByType(type, pageNum, pageSize);
        pageInfo.setData(list);
        // 返回给前台
        return pageInfo;
    }

    @Override
    public ResultInfo deleteQuestion(Integer qid) {
        Question question = questionDao.selectQuestionByQid(qid);
        if(question == null){
            return new ResultInfo(0,"该订单不存在！");
        }
        if(question.getState() == 1){
            return new ResultInfo(0,"未支付订单不能删除！");
        }
        Integer rows = questionDao.deleteQuestionByQid(qid);
        if(rows != 1){
            return new ResultInfo(0,"删除失败");
        }
        return new ResultInfo(1,"删除成功");
    }

    @Override
    public PageInfo showMyQuestions(String username, Integer pageNum,Integer pageSize) {
        Integer uid = userDao.selectByUsername(username).getUid();
        Integer totalCount = questionDao.getMyTotalCount(uid);
        if(totalCount == 0) return null;
        Integer pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        if(pageNum <= 1) pageNum = 1;
        if(pageNum >= pageCount) pageNum = pageCount;

//        List<Question> list = questionDao.selectQuestionsByUid(uid,pageNum,pageSize);
        List<MyQuestionsVo> list = questionDao.queryQuestionsByUid(uid,pageNum,pageSize);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageCount(pageCount);
        pageInfo.setData(list);
        pageInfo.setTotalRecorder(totalCount);
        return pageInfo;
    }

    @Override
    public ResultInfo<Question> selectQuestionByQid(Integer qid) {
        Question question = questionDao.selectQuestionByQid(qid);
        if(question != null){
            return new ResultInfo<>(1,question);
        }
        return new ResultInfo<>(0,"该记录不存在！");
    }

    @Override
    public ResultInfo updateQuestionByQid(Integer qid, String content) {
        Integer rows = questionDao.updateQuestionByQid(qid, content);
        if(rows != null){
            return new ResultInfo(1,"修改成功！");
        }
        return new ResultInfo(0,"修改失败");
    }
}
