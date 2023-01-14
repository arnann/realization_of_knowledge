package com.arnan.realization_of_knowledge.dao;

import com.arnan.realization_of_knowledge.pojo.User;

public interface UserDao {
    Integer saveUser(User user);
    User selectByUsername(String username);
    User selectByUid(Integer uid);
    Integer updatePassword(String username,String password);
    Integer updateUserInfo(User user,String username);
    Integer updateIntegration(String username,Integer integration);
    Integer updateIntegrationByUid(Integer uid, Integer integration);
}
