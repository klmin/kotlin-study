package com.example.kotlinstudy.java.controller;


import com.example.kotlinstudy.java.request.MemberRequest;
import com.example.kotlinstudy.java.response.MemberResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/java/test")
@RestController("javaMemberController")
public class MemberController {

    @PostMapping
    public ResponseEntity<MemberResponse> test(@RequestBody MemberRequest request){
        var response = new ObjectMapper().convertValue(request, MemberResponse.class);
        return ResponseEntity.ok(response);
    }

}
