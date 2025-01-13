package com.example.kotlinstudy.kt.member.api.service

import com.example.kotlinstudy.kt.member.entity.MemberData

interface ApiService {
    fun getMemberData(id: Long?): MemberData?
}