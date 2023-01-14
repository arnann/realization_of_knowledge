package com.arnan.realization_of_knowledge.service;

import com.arnan.realization_of_knowledge.pojo.Answer;
import com.arnan.realization_of_knowledge.vo.PageInfo;
import com.arnan.realization_of_knowledge.vo.ResultInfo;

public interface AnswerService {
    ResultInfo saveAnswer(Answer answer);
    ResultInfo updateAnswer(Long aid,String answer);
    PageInfo<Answer> showMyAnswers(Integer uid,Integer pageNum,Integer pageSize);
    ResultInfo selectAnswerByAid(Long aid);
    ResultInfo deleteAnswerByAid(Long aid);
}
