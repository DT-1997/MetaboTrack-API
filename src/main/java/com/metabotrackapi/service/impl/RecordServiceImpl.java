package com.metabotrackapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.mapper.RecordMapper;
import com.metabotrackapi.service.RecordService;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, DailyMetabolicRecord> implements RecordService {
}
