package com.example.kotlinstudy.java;

import com.example.kotlinstudy.java.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class JavaTests {

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void 자바_객체_테스트() throws JsonProcessingException {

        var idChangeEntity = Member.builder()
                                      .id(200L)
                                      .name("testName")
                                      .age(30)
                                      .build();
        log.info("idChangeEntity : {}", idChangeEntity);


        String writeAsString = objectMapper.writeValueAsString(idChangeEntity);
        log.info("writeAsString : {}", writeAsString);

        var readValue = objectMapper.readValue(writeAsString, Member.class);
        log.info("readValue : {}", readValue);

        var convertValue = objectMapper.convertValue(idChangeEntity, Member.class);
        log.info("convertValue : {}", convertValue);

        var idNameChangeEntity = new Member(1L, "testName2", 20, null);
        log.info("idNameChangeEntity : {}", idNameChangeEntity);

        var copyAndNameChangeEntity = idNameChangeEntity.toBuilder()
                                                        .id(2L)
                                                        .name("testName3")
                                                        .description("test_description")
                                                        .build();
        log.info("copyAndNameChangeEntity : {}", copyAndNameChangeEntity);

        var copyAndIdChangeEntity = copyAndNameChangeEntity.toBuilder().id(3L).build();
        log.info("copyAndIdChangeEntity : {}", copyAndIdChangeEntity);


        var copyAndAgeChangeEntity = copyAndIdChangeEntity.toBuilder().age(10).build();
        log.info("copyAndAgeChangeEntity : {}", copyAndAgeChangeEntity);

    }
}
