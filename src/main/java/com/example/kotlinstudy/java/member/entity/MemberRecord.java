package com.example.kotlinstudy.java.member.entity;

import lombok.Builder;

@Builder(toBuilder = true)
public record MemberRecord(
        Long id,
        String name,
        Integer age,
        String description
) {
}
