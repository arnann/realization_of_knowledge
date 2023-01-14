package com.arnan.realization_of_knowledge.dao.impl;

import com.arnan.realization_of_knowledge.dao.QuestionDao;
import com.arnan.realization_of_knowledge.pojo.Question;
import com.arnan.realization_of_knowledge.utils.DBUtils;
import com.arnan.realization_of_knowledge.vo.MyQuestionsVo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

public class QuestionDaoImpl implements QuestionDao {
    @Override
    public Integer saveQuestion(Question question) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "insert into t_question(uid,title,content,type,state) values(?,?,?,?,?)";
        try {
            return qr.update(sql,question.getUid(), question.getTitle(), question.getContent(), question.getType(), question.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer updateStateByQid(Integer qid, Integer state) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "update t_question set state = ? where qid = ?";
        try {
            return qr.update(sql, state, qid);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Question> selectByType(String type, Integer pageNum, Integer pageSize) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from t_question where type = ? and state = 0 limit ?,?";
        try {
            return qr.query(sql,new BeanListHandler<>(Question.class), type, (pageNum - 1) * pageSize, pageSize);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Question selectQuestionByQid(Integer qid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from t_question where qid = ?";
        try {
            return qr.query(sql,new BeanHandler<>(Question.class), qid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getTotalCount(String type) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select count(*) from t_question where type = ? and state = 0";
        try {
            Object count = qr.query(sql,new ScalarHandler<>(),type);
            return Integer.valueOf(String.valueOf(count));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer deleteQuestionByQid(Integer qid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "delete from t_question where qid = ?";
        try {
            return qr.update(sql,qid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Question> selectQuestionsByUid(Integer uid, Integer pageNum, Integer pageSize) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from t_question where uid = ? limit ? , ?";
        try{
            return qr.query(sql,new BeanListHandler<>(Question.class), uid,(pageNum - 1) * pageSize, pageSize);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getMyTotalCount(Integer uid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select count(*) from t_question where uid = ?";
        try{
            Object count = qr.query(sql,new ScalarHandler<>(),uid);
            return Integer.valueOf(String.valueOf(count));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateQuestionByQid(Integer qid, String content) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "update t_question set content = ? where qid = ?";
        try {
            return qr.update(sql, content, qid);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateAidByQid(Integer qid, Long aid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "update t_question set aid = ? where qid = ?";
        try {
            return qr.update(sql, aid, qid);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MyQuestionsVo> queryQuestionsByUid(Integer uid, Integer pageNum, Integer pageSize) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select Q.qid,Q.content as 'question',Q.state,A.answer" +
                " from t_question Q left join t_answer A" +
                " on Q.aid = A.aid" +
                " where Q.uid = ? limit ? , ?";
        try{
            return qr.query(sql,new BeanListHandler<>(MyQuestionsVo.class), uid,(pageNum - 1) * pageSize, pageSize);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
