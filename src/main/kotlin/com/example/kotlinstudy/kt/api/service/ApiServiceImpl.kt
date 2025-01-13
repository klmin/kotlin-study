package com.example.kotlinstudy.kt.api.service

import com.example.kotlinstudy.java.entity.MemberRecord
import com.example.kotlinstudy.kt.entity.MemberData
import org.springframework.stereotype.Service

@Service
class ApiServiceImpl: ApiService{

    override fun getMemberData(id: Long?): MemberData? {
        return if (id == null) null else MemberData(id, null, null, null)
    }
}