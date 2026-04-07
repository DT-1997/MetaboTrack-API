package com.metabotrackapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<DailyMetabolicRecord> {
}
