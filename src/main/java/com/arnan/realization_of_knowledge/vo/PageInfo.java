package com.arnan.realization_of_knowledge.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageInfo<T> implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Integer pageCount;
    private Integer totalRecorder;
    private List<T> data;
}
