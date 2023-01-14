package com.arnan.realization_of_knowledge.dao;

import com.arnan.realization_of_knowledge.pojo.Answer;

import java.util.List;

public interface AnswerDao {
    Integer saveAnswer(Answer answer);
    Integer updateAnswer(Long aid, String answer);
    Integer getTotalCount(Integer uid);
    List<Answer> selectAnswersByUid(Integer uid,Integer pageNum,Integer pageSize);
    Answer selectAnswerByAid(Long aid);
    Integer deleteAnswerByAid(Long aid);
}
