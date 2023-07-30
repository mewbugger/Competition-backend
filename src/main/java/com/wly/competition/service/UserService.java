package com.wly.competition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wly.competition.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 *
 */
public interface UserService extends IService<User> {


    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);



    /**
     * 根据标签搜索用户
     * @param tagList
     * @return
     */
    List<User> searchUsersByTags(List<String> tagList);



    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user, User loginUser);


    /**
     * 获取当前登录用户信息
     * @return
     */
    User getLoginUser(HttpServletRequest request);


    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param loginUser
     * @return
     */
    boolean isAdmin(User loginUser);


    /**
     * 匹配用户
     * @param num
     * @param LoginUser
     * @return
     */
    List<User> matchUsers(Long num, User LoginUser);




}
