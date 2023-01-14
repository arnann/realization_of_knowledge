package com.arnan.realization_of_knowledge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    private Long pid;
    private Long aid;
    private Integer uid;
    private String responder;
    private Integer state;
    private LocalDateTime create_time;
    private LocalDateTime pay_time;
}
