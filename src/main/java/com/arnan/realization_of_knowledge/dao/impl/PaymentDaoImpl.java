package com.arnan.realization_of_knowledge.dao.impl;

import com.arnan.realization_of_knowledge.dao.PaymentDao;
import com.arnan.realization_of_knowledge.pojo.Payment;
import com.arnan.realization_of_knowledge.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {
    @Override
    public Integer savePayment(Payment payment) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "insert into t_payment values(?,?,?,?,?,?,?)";
        try {
            return qr.update(sql, payment.getPid(), payment.getAid(), payment.getUid(),
                    payment.getResponder(), payment.getState(),payment.getCreate_time(), payment.getPay_time());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getNonPaymentCountByUid(Integer uid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select count(*) from t_payment where uid = ? and state = 1";
        try {
            Object count = qr.query(sql,new ScalarHandler<>(),uid);
            return Integer.valueOf(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getCountByUid(Integer uid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select count(*) from t_payment where uid = ?";
        try {
            Object count = qr.query(sql,new ScalarHandler<>(),uid);
            return Integer.valueOf(String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Payment> selectAllByUid(Integer pageNum, Integer pageSize, Integer uid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from t_payment where uid = ? limit ? , ?";
        try{
            return qr.query(sql,new BeanListHandler<>(Payment.class), uid,(pageNum - 1) * pageSize,pageSize);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer deleteByAid(Long aid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "delete from t_payment where aid = ?";
        try{
            return qr.update(sql,aid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer deleteByPid(Long pid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "delete from t_payment where pid = ?";
        try{
            return qr.update(sql,pid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Payment selectByPid(Long pid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from t_payment where pid = ?";
        try{
            return qr.query(sql,new BeanHandler<>(Payment.class), pid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateStateAndPayTimeByPid(Long pid, Integer state, LocalDateTime payTime) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "update t_payment set state = ?, pay_time = ? where pid = ?";
        try{
            return qr.update(sql,state,payTime,pid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
