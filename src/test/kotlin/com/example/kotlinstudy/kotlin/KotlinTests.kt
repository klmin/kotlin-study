package com.example.kotlinstudy.kotlin

import com.example.kotlinstudy.kotlin.entity.Member
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import lombok.extern.slf4j.Slf4j
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Slf4j
class KotlinTests {

    private val objectMapper = jacksonObjectMapper();
    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("코틀린 객체 테스트")
    fun 코틀린_객체_테스트() {

        val idChangeEntity = Member(id=200L, name = "testName", age=30, description = null)
        logger.info { "idChangeEntity : $idChangeEntity" }

        val writeAsString = objectMapper.writeValueAsString(idChangeEntity);
        logger.info { "writeAsString : $writeAsString" }

        val readValue = objectMapper.readValue(writeAsString, Member::class.java)
        logger.info { "readValue : $readValue" }

        val convertValue = objectMapper.convertValue(idChangeEntity, Member::class.java)
        logger.info { "convertValue : $convertValue" }

        val idNameChangeEntity = Member(1, "testName2", 20, null)
        logger.info { "idNameChangeEntity : $idNameChangeEntity" }

        val copyAndNameChangeEntity = idNameChangeEntity.copy(id=2, name="testName3", description = "test_description")
        logger.info { "copyAndNameChangeEntity : $copyAndNameChangeEntity" }

        val copyAndIdChangeEntity = copyAndNameChangeEntity.copy(id=3L)
        logger.info { "copyAndIdChangeEntity : $copyAndIdChangeEntity" }

        val copyAndAgeChangeEntity = copyAndIdChangeEntity.copy(age=10)
        logger.info { "copyAndAgeChangeEntity : $copyAndAgeChangeEntity" }

    }
}