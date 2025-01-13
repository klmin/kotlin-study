package com.example.kotlinstudy.java.entity;

import lombok.Builder;

@Builder(toBuilder = true)
public record MemberRecord(
        Long id,
        String name,
        Integer age,
        String description
) {
}
