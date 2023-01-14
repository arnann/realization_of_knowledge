package com.arnan.realization_of_knowledge.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultInfo<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    public ResultInfo(){}
    public ResultInfo(Integer code,T data){
        this.code = code;
        this.data = data;
    }
    public ResultInfo(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
