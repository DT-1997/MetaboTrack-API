package com.metabotrackapi.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult implements Serializable {

    /**
     * Total Records
     */
    private Long total;

    /**
     * Records List
     */
    private List<?> records;
}

