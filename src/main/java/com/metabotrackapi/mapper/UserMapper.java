package com.metabotrackapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metabotrackapi.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserAccount> {
}
