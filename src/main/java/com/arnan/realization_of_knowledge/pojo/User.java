package com.arnan.realization_of_knowledge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private Integer age;
    private int gender;
    private String avatar;
    private String classnum;
    private String introduction;
    private Integer integration;
}
