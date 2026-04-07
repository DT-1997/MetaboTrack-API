package com.metabotrackapi.converter;

import com.metabotrackapi.dto.RecordCreateDTO;
import com.metabotrackapi.entity.DailyMetabolicRecord;
import com.metabotrackapi.vo.RecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecordConverter {

    /**
     * Entity -> VO
     */
    RecordVO toVO(DailyMetabolicRecord entity);

    /**
     * DTO -> Entity
     */
    DailyMetabolicRecord toEntity(RecordCreateDTO dto);
}
