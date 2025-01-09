package com.example.kotlinstudy.kotlin.controller

import com.example.kotlinstudy.kotlin.request.MemberRequest
import com.example.kotlinstudy.kotlin.response.MemberResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/test")
@RestController
class MemberController {

    @PostMapping
    fun test(@RequestBody request: MemberRequest): ResponseEntity<MemberResponse> {
        val response = ObjectMapper().convertValue(request, MemberResponse::class.java)
        return ResponseEntity.ok(response);
    }
}