package com.arnan.realization_of_knowledge.dao;

import com.arnan.realization_of_knowledge.pojo.Question;
import com.arnan.realization_of_knowledge.vo.MyQuestionsVo;

import java.util.List;

public interface QuestionDao {
    Integer saveQuestion(Question question);

    Integer updateStateByQid(Integer qid, Integer state);

    List<Question> selectByType(String type, Integer pageNum, Integer pageSize);

    Question selectQuestionByQid(Integer qid);

    Integer getTotalCount(String type);

    Integer deleteQuestionByQid(Integer qid);

    List<Question> selectQuestionsByUid(Integer uid, Integer pageNum, Integer pageSize);

    Integer getMyTotalCount(Integer uid);

    Integer updateQuestionByQid(Integer qid, String content);

    Integer updateAidByQid(Integer qid, Long aid);

    List<MyQuestionsVo> queryQuestionsByUid(Integer uid, Integer pageNum, Integer pageSize);
}
