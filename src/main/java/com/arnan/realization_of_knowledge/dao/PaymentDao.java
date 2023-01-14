package com.arnan.realization_of_knowledge.dao;

import com.arnan.realization_of_knowledge.pojo.Payment;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentDao {
    Integer savePayment(Payment payment);
    Integer getNonPaymentCountByUid(Integer uid);
    Integer getCountByUid(Integer uid);
    List<Payment> selectAllByUid(Integer pageNum, Integer pageSize, Integer uid);
    Integer deleteByAid(Long aid);
    Integer deleteByPid(Long pid);
    Payment selectByPid(Long pid);
    Integer updateStateAndPayTimeByPid(Long pid, Integer state, LocalDateTime payTime);
}
