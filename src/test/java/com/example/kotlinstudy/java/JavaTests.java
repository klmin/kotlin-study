package com.example.kotlinstudy.java;

import com.example.kotlinstudy.java.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class JavaTests {

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void 자바_객체_테스트() throws JsonProcessingException {

        Long id = 200L;
        String name = "testName";
        Integer age = 30;

        var idChangeEntity = Member.builder()
                                   .id(id)
                                   .name(name)
                                   .age(age)
                                   .build();

        Assertions.assertEquals(id, idChangeEntity.getId());
        Assertions.assertEquals(name, idChangeEntity.getName());
        Assertions.assertEquals(age, idChangeEntity.getAge());
        Assertions.assertNull(idChangeEntity.getDescription());

        String writeAsString = objectMapper.writeValueAsString(idChangeEntity);
        Assertions.assertNotNull(writeAsString);

        var readValueMember = objectMapper.readValue(writeAsString, Member.class);

        assertThat(idChangeEntity).usingRecursiveComparison().isEqualTo(readValueMember);

        var convertValueMember = objectMapper.convertValue(idChangeEntity, Member.class);
        assertThat(readValueMember).usingRecursiveComparison().isEqualTo(convertValueMember);

        id = 1L;
        name = "testName2";
        age = 20;

        var idNameChangeEntity = new Member(1L, "testName2", 20, null);
        Assertions.assertEquals(id, idNameChangeEntity.getId());
        Assertions.assertEquals(name, idNameChangeEntity.getName());
        Assertions.assertEquals(age, idNameChangeEntity.getAge());
        Assertions.assertNull(idNameChangeEntity.getDescription());

        id = 2L;
        name = "testName3";
        String description = "test_description";

        var copyAndNameChangeEntity = idNameChangeEntity.toBuilder()
                                                        .id(id)
                                                        .name(name)
                                                        .description(description)
                                                        .build();

        Assertions.assertEquals(id, copyAndNameChangeEntity.getId());
        Assertions.assertEquals(name, copyAndNameChangeEntity.getName());
        Assertions.assertEquals(age, copyAndNameChangeEntity.getAge());
        Assertions.assertEquals(description, copyAndNameChangeEntity.getDescription());

        id = 3L;

        var copyAndIdChangeEntity = copyAndNameChangeEntity.toBuilder().id(3L).build();
        Assertions.assertEquals(id, copyAndIdChangeEntity.getId());
        Assertions.assertEquals(copyAndNameChangeEntity.getName(), copyAndIdChangeEntity.getName());
        Assertions.assertEquals(copyAndNameChangeEntity.getAge(), copyAndIdChangeEntity.getAge());
        Assertions.assertEquals(copyAndNameChangeEntity.getDescription(), copyAndIdChangeEntity.getDescription());

        age = 10;

        var copyAndAgeChangeEntity = copyAndIdChangeEntity.toBuilder().age(10).build();
        Assertions.assertEquals(copyAndIdChangeEntity.getId(), copyAndAgeChangeEntity.getId());
        Assertions.assertEquals(copyAndIdChangeEntity.getName(), copyAndAgeChangeEntity.getName());
        Assertions.assertEquals(age, copyAndAgeChangeEntity.getAge());
        Assertions.assertEquals(copyAndIdChangeEntity.getDescription(), copyAndAgeChangeEntity.getDescription());


    }

    @Test
    void 널러블_타입_테스트(){

        String name = null; // ✅ Null을 허용

        // ✅ 컴파일 성공
        // ❌ 런타임에서 실행시 NPE 발생
        Assertions.assertThrows(NullPointerException.class, () -> name.length());
    }

    @Test
    void 안전_호출_연산자_테스트(){

        String name = null; // ✅ Null을 허용

        // ✅ 컴파일 성공
        // ❌ 런타임에서 실행시 NPE 발생
        Assertions.assertThrows(NullPointerException.class, () -> name.length());
        Assertions.assertNull(name == null ? null : name.length());

    }

}
