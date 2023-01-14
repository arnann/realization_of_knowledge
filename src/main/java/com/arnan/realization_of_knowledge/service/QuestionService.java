package com.arnan.realization_of_knowledge.service;

import com.arnan.realization_of_knowledge.pojo.Question;
import com.arnan.realization_of_knowledge.vo.PageInfo;
import com.arnan.realization_of_knowledge.vo.ResultInfo;


public interface QuestionService {
    ResultInfo saveQuestion(Integer uid,String title, String content, String type);
    PageInfo showTypeList(String type, Integer pageNum, Integer pageSize);
    ResultInfo deleteQuestion(Integer id);
    PageInfo showMyQuestions(String username, Integer pageNum, Integer pageSize);
    ResultInfo<Question> selectQuestionByQid(Integer qid);
    ResultInfo updateQuestionByQid(Integer qid,String content);
}
