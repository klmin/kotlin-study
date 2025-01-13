package com.example.kotlinstudy.kt.member.controller

import com.example.kotlinstudy.kt.member.request.MemberRequest
import com.example.kotlinstudy.kt.member.response.MemberResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/kotlin/test")
@RestController
class MemberController {

    @PostMapping
    fun test(@RequestBody request: MemberRequest): ResponseEntity<MemberResponse> {
        val response = ObjectMapper().convertValue(request, MemberResponse::class.java)
        return ResponseEntity.ok(response);
    }
}

