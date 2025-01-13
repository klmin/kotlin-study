package com.example.kotlinstudy.kt.member.api.service

import com.example.kotlinstudy.kt.member.entity.MemberData
import org.springframework.stereotype.Service

@Service
class ApiServiceImpl: ApiService {

    override fun getMemberData(id: Long?): MemberData? {
        return if (id == null) null else MemberData(id, null, null, null)
    }
}