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
public class Answer implements Serializable {
    private Long aid;
    private Integer uid;
    private Integer qid;
    private String question;
    private String answer;
}
