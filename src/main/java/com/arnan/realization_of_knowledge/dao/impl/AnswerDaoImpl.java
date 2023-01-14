package com.arnan.realization_of_knowledge.dao.impl;

import com.arnan.realization_of_knowledge.dao.AnswerDao;
import com.arnan.realization_of_knowledge.pojo.Answer;
import com.arnan.realization_of_knowledge.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

public class AnswerDaoImpl implements AnswerDao {
    @Override
    public Integer saveAnswer(Answer answer) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "insert into t_answer(aid,uid,qid,question,answer) values(?,?,?,?,?)";
        try {
            return qr.update(sql, answer.getAid(), answer.getUid(), answer.getQid(), answer.getQuestion(), answer.getAnswer());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateAnswer(Long aid,String answer) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "update t_answer set answer = ? where aid = ?";
        try{
            return qr.update(sql,answer,aid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getTotalCount(Integer uid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select count(*) from t_answer where uid = ?";
        try{
            Object count = qr.query(sql,new ScalarHandler<>(),uid);
            return Integer.valueOf(String.valueOf(count));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Answer> selectAnswersByUid(Integer uid,Integer pageNum,Integer pageSize) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from t_answer where uid = ? limit ? , ?";
        try {
            return qr.query(sql, new BeanListHandler<>(Answer.class), uid,(pageNum - 1) * pageSize,pageSize);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Answer selectAnswerByAid(Long aid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from t_answer where aid = ?";
        try{
            return qr.query(sql, new BeanHandler<>(Answer.class),aid );
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer deleteAnswerByAid(Long aid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "delete from t_answer where aid = ?";
        try{
            return qr.update(sql,aid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
