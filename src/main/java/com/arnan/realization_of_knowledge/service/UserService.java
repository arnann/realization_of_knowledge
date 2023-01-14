package com.arnan.realization_of_knowledge.service;

import com.arnan.realization_of_knowledge.pojo.User;
import com.arnan.realization_of_knowledge.vo.ResultInfo;


public interface UserService {
    ResultInfo<User> register(String username, String password);

    ResultInfo<User> login(String username, String password);

    ResultInfo changePassword(String username, String oldPassword, String newPassword, String newPassword2);

    ResultInfo updateUserInfo(User user, String username);

    ResultInfo<User> selectUserInfo(String username);

    ResultInfo updateIntegration(String username,Integer integration);
}
