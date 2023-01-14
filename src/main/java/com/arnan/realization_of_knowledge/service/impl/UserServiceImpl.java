package com.arnan.realization_of_knowledge.service.impl;

import com.arnan.realization_of_knowledge.dao.UserDao;
import com.arnan.realization_of_knowledge.dao.impl.UserDaoImpl;
import com.arnan.realization_of_knowledge.pojo.User;
import com.arnan.realization_of_knowledge.service.UserService;
import com.arnan.realization_of_knowledge.utils.MD5Utils;
import com.arnan.realization_of_knowledge.vo.ResultInfo;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @return JSON对象
     */
    @Override
    public ResultInfo<User> register(String username, String password) {
        if (username == null || password == null || username.equals("") || password.equals("")) {
            return new ResultInfo<>(0, "用户名或密码为空！");
        }
        User u = userDao.selectByUsername(username);
        if (userDao.selectByUsername(username) != null) {
            return new ResultInfo<>(0, "该用户已存在！");
        }
        String salt = UUID.randomUUID().toString().toUpperCase();
        User user = new User();
        user.setUsername(username);
        user.setSalt(salt);
        String MD5Pwd = MD5Utils.encrypt(password, salt);
        user.setPassword(MD5Pwd);
        user.setIntegration(30);
        Integer integer = userDao.saveUser(user);
        if (integer != 1) {
            return new ResultInfo<>(0, "注册失败！");
        }
        return new ResultInfo<>(1, user);
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return JSON对象
     */
    public ResultInfo<User> login(String username, String password) {
        if (username == null || password == null || "".equals(username) || "".equals(password)) {
            return new ResultInfo<>(0, "用户名或密码为空！");
        }
        User user = userDao.selectByUsername(username);
        if (user == null) {
            return new ResultInfo<>(0, "该用户不存在！");
        }
        if (MD5Utils.encrypt(password, user.getSalt()).equals(user.getPassword())) {
            return new ResultInfo<>(1, user);
        }
        return new ResultInfo<>(0, "密码错误！");
    }

    /**
     * 修改密码
     *
     * @param username    用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return JSON对象
     */
    @Override
    public ResultInfo changePassword(String username, String oldPassword, String newPassword, String newPassword2) {
        User user = userDao.selectByUsername(username);
        if(!newPassword.equals(newPassword2)){
            return new ResultInfo(0,"俩次密码不一致，请重新输入~");
        }
        if (MD5Utils.encrypt(oldPassword, user.getSalt()).equals(user.getPassword())) {
            newPassword = MD5Utils.encrypt(newPassword, user.getSalt());
            Integer integer = userDao.updatePassword(username, newPassword);
            if (integer == 1) {
                return new ResultInfo(1, "修改成功");
            }
        }
        return new ResultInfo(0, "旧密码不正确，请重新输入~");
    }

    @Override
    public ResultInfo updateUserInfo(User user, String username) {
        Integer rows = userDao.updateUserInfo(user, username);
        if(rows == 1){
            return new ResultInfo(1,"修改成功");
        }
        return new ResultInfo(0,"修改失败！");
    }

    @Override
    public ResultInfo<User> selectUserInfo(String username) {
        User user = userDao.selectByUsername(username);
        if(user != null) {
            return new ResultInfo<>(1, user);
        }
        return new ResultInfo<>(0,"系统异常！请联系攻城狮修复！");
    }

    /**
     *
     * 充值积分
     * @param username 用户名
     * @param integration 充值后积分
     * @return JSON对象
     */
    @Override
    public ResultInfo updateIntegration(String username,Integer integration) {
        Integer rows = userDao.updateIntegration(username, integration);
        if(rows != 0){
            return new ResultInfo(1,"交易成功！");
        }
        return new ResultInfo(0,"交易失败");
    }

}
