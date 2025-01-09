package com.example.kotlinstudy.java.controller;


import com.example.kotlinstudy.java.request.MemberRequest;
import com.example.kotlinstudy.java.response.MemberResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class MemberController {

    @PostMapping
    public ResponseEntity<MemberResponse> test(@RequestBody MemberRequest request){
        var response = new ObjectMapper().convertValue(request, MemberResponse.class);
        return ResponseEntity.ok(response);
    }

}
