package com.arnan.realization_of_knowledge.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyQuestionsVo implements Serializable {
    private Long qid;
    private String question;
    private String answer;
    private Integer state;
}
