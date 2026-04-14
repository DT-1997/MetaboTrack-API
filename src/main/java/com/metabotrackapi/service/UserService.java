package com.metabotrackapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.metabotrackapi.dto.UserLoginDTO;
import com.metabotrackapi.dto.UserRegisterDTO;
import com.metabotrackapi.entity.UserAccount;

public interface UserService extends IService<UserAccount> {

    /**
     * Register a new user
     * @param userRegisterDTO Registration info
     * @return boolean success status
     */
    boolean register(UserRegisterDTO userRegisterDTO);

    /**
     * Authenticate user and generate JWT
     * @param userLoginDTO Login credentials
     * @return JWT Token string
     */
    String login(UserLoginDTO userLoginDTO);
}
