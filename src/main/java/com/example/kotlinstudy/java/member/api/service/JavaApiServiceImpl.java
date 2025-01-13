package com.example.kotlinstudy.java.member.api.service;

import com.example.kotlinstudy.java.member.entity.MemberRecord;
import org.springframework.stereotype.Service;

@Service
public class JavaApiServiceImpl implements JavaApiService {
    @Override
    public MemberRecord getMemberData(Long id) {
        return id == null ? null : new MemberRecord(id, null, null, null);
    }
}
