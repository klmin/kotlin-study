package com.example.kotlinstudy.java.api.service;

import com.example.kotlinstudy.java.entity.Member;
import org.springframework.stereotype.Service;

@Service("javaApiService")
public class ApiServiceImpl implements ApiService {
    @Override
    public Member getMemberData(Long id) {
        return null;
    }
}
