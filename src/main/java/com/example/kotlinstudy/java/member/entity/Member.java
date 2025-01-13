package com.example.kotlinstudy.java.member.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
@ToString
public class Member {

    private Long id;
    private String name;
    private Integer age;
    private String description;

}
