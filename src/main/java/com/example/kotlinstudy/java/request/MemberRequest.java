package com.example.kotlinstudy.java.request;

public record MemberRequest(

        Long id,
        String name,
        Integer age,
        String description
) {
}
