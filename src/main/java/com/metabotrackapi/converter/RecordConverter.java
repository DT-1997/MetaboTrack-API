package com.metabotrackapi.converter;

import com.metabotrackapi.dto.RecordCreateDTO;
import com.metabotrackapi.dto.RecordUpdateDTO;
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
     * createDTO -> Entity
     */
    DailyMetabolicRecord toEntity(RecordCreateDTO dto);

    /**
     * updateDTO -> Entity
     */
    DailyMetabolicRecord toEntity(RecordUpdateDTO dto);


}
