package com.metabotrackapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.vo.EfficiencyDistributionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<DailyMetabolicRecord> {
}
