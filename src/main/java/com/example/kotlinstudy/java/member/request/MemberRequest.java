package com.example.kotlinstudy.java.member.request;

public record MemberRequest(

        Long id,
        String name,
        Integer age,
        String description
) {
}
