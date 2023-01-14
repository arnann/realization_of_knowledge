package com.arnan.realization_of_knowledge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {
    private Integer qid;
    private Long aid;
    private Integer uid;
    private String title;
    private String content;
    private String type;
    private Integer state;
    private String integration;
}
