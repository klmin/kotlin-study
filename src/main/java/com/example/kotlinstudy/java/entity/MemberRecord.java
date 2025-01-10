package com.example.kotlinstudy.java.entity;

import lombok.Builder;

@Builder
public record MemberData(
        Long id,
        String name,
        Integer age,
        String description
) {
}
