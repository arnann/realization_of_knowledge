package com.arnan.realization_of_knowledge.service;

import com.arnan.realization_of_knowledge.pojo.Payment;
import com.arnan.realization_of_knowledge.vo.PageInfo;
import com.arnan.realization_of_knowledge.vo.ResultInfo;

public interface PaymentService {
    PageInfo<Payment> showMyPayments(Integer pageNum,Integer pageSize,Integer uid);
    ResultInfo pay(Integer uid, Long pid, Integer payOption);
    ResultInfo deleteByPid(Long pid);
}
