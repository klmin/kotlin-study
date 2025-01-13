package com.example.kotlinstudy.kt.api.service

import com.example.kotlinstudy.kt.entity.MemberData

interface ApiService {
    fun getMemberData(id: Long?): MemberData?
}