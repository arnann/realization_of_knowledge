package com.arnan.realization_of_knowledge.dao.impl;

import com.arnan.realization_of_knowledge.dao.UserDao;
import com.arnan.realization_of_knowledge.pojo.User;
import com.arnan.realization_of_knowledge.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;


public class UserDaoImpl implements UserDao {

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 成功则返回1
     */
    @Override
    public Integer saveUser(User user) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "insert into t_user(username,password,salt,gender,avatar,classnum,integration) values(?,?,?,?,?,?,?)";
        try {
            return qr.update(sql, user.getUsername(), user.getPassword(), user.getSalt(),
                    user.getGender(), user.getAvatar(), user.getClassnum(), user.getIntegration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 根据用户名查询用户对象
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public User selectByUsername(String username) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from t_user where username = ?";
        try {
            return qr.query(DBUtils.getConnection(), sql, new BeanHandler<>(User.class), username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户id查询用户对象
     *
     * @param uid 用户id
     * @return 用户对象
     */
    @Override
    public User selectByUid(Integer uid) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from t_user where uid = ?";
        try {
            return qr.query(DBUtils.getConnection(), sql, new BeanHandler<>(User.class), uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改密码
     *
     * @param username    用户名
     * @param newPassword 新密码
     * @return
     */
    @Override
    public Integer updatePassword(String username, String newPassword) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "update t_user set password = ? where username = ?";
        try {
            return qr.update(sql, newPassword, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param user 修改后的user对象
     * @param username session中的用户名
     * @return 修改成功则返回1
     */
    @Override
    public Integer updateUserInfo(User user,String username) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "update t_user set username = ? , age = ? , gender = ? , avatar = ? , classnum = ? , introduction = ? where username = ?";
        try {
            return qr.update(sql, user.getUsername(), user.getAge(), user.getGender(),
                    user.getAvatar(), user.getClassnum(), user.getIntroduction(),username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer updateIntegration(String username, Integer integration) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "update t_user set integration = ? where username = ?";
        try{
            return qr.update(sql,integration,username);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 支付积分
     * @param uid 用户id
     * @param integration 积分
     * @return JSO对象
     */
    @Override
    public Integer updateIntegrationByUid(Integer uid, Integer integration) {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "update t_user set integration = ? where uid = ?";
        try{
            return qr.update(sql,integration,uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
