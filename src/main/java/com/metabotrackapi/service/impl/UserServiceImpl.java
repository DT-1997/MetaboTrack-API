package com.metabotrackapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metabotrackapi.dto.UserLoginDTO;
import com.metabotrackapi.dto.UserRegisterDTO;
import com.metabotrackapi.entity.UserAccount;
import com.metabotrackapi.exception.BusinessException;
import com.metabotrackapi.mapper.UserMapper;
import com.metabotrackapi.properties.JwtProperties;
import com.metabotrackapi.service.UserService;
import com.metabotrackapi.util.JwtUtil;
import com.metabotrackapi.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserAccount> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    @Override
    public boolean register(UserRegisterDTO userRegisterDTO) {
        LambdaQueryWrapper<UserAccount> queryWrapper = new LambdaQueryWrapper<UserAccount>()
                .eq(UserAccount::getUsername, userRegisterDTO.getUsername());

        if (this.count(queryWrapper) > 0) {
            throw new BusinessException("Username already exists");
        }

        UserAccount userAccount = UserAccount.builder()
                .username(userRegisterDTO.getUsername())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .role("USER")
                .status(1)
                .build();

        return this.save(userAccount);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {

        UserAccount user = this.getOne(new LambdaQueryWrapper<UserAccount>()
                .eq(UserAccount::getUsername, userLoginDTO.getUsername()));

        if (user == null || !passwordEncoder.matches(user.getPassword(), userLoginDTO.getPassword())) {
            throw new BusinessException("Invalid username or password");
        }

        if (user.getStatus() == 0) {
            throw new BusinessException("Account is currently disabled");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());

        return JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims
        );
    }
}
