package com.example.kotlinstudy.kt


import com.example.kotlinstudy.kt.entity.Member
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KotlinTests {

    private val objectMapper = jacksonObjectMapper();
    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("코틀린 객체 테스트")
    fun 코틀린_객체_테스트() {

        var id : Long = 200L
        var name : String = "testName"
        var age : Int = 30
        var description : String? = null

        val idChangeEntity = Member(id=id, name = name, age=age, description = description)
        Assertions.assertEquals(id, idChangeEntity.id)
        Assertions.assertEquals(name, idChangeEntity.name)
        Assertions.assertEquals(age, idChangeEntity.age)
        Assertions.assertNull(idChangeEntity.description)

        val writeAsString = objectMapper.writeValueAsString(idChangeEntity);
        Assertions.assertNotNull(writeAsString)

        val readValueMember = objectMapper.readValue(writeAsString, Member::class.java)

        assertThat(idChangeEntity).usingRecursiveComparison().isEqualTo(readValueMember)

        val convertValueMember = objectMapper.convertValue(idChangeEntity, Member::class.java)

        assertThat(convertValueMember).usingRecursiveComparison().isEqualTo(readValueMember)

        id = 1
        name = "testName2"
        age = 20
        description = null

        val idNameChangeEntity = Member(id, name, age, description)

        Assertions.assertNotEquals(convertValueMember.id, idNameChangeEntity.id)
        Assertions.assertNotEquals(convertValueMember.name, idNameChangeEntity.name)
        Assertions.assertNotEquals(convertValueMember.age, idNameChangeEntity.age)

        Assertions.assertEquals(id, idNameChangeEntity.id)
        Assertions.assertEquals(name, idNameChangeEntity.name)
        Assertions.assertEquals(age, idNameChangeEntity.age)
        Assertions.assertEquals(convertValueMember.description, idNameChangeEntity.description)

        id = 2
        name = "testName3"
        description = "test_description"

        val copyAndNameChangeEntity = idNameChangeEntity.copy(id=id, name=name, description = description)
        Assertions.assertNotEquals(idNameChangeEntity.id, copyAndNameChangeEntity.id)
        Assertions.assertNotEquals(idNameChangeEntity.name, copyAndNameChangeEntity.name)
        Assertions.assertNotEquals(idNameChangeEntity.description, copyAndNameChangeEntity.description)

        Assertions.assertEquals(id, copyAndNameChangeEntity.id)
        Assertions.assertEquals(name, copyAndNameChangeEntity.name)
        Assertions.assertEquals(idNameChangeEntity.age, copyAndNameChangeEntity.age)

        id = 3L
        val copyAndIdChangeEntity = copyAndNameChangeEntity.copy(id=id)
        Assertions.assertEquals(id, copyAndIdChangeEntity.id)
        Assertions.assertNotEquals(copyAndNameChangeEntity.id, copyAndIdChangeEntity.id)
        Assertions.assertEquals(copyAndNameChangeEntity.name, copyAndIdChangeEntity.name)
        Assertions.assertEquals(copyAndNameChangeEntity.age, copyAndIdChangeEntity.age)
        Assertions.assertEquals(copyAndNameChangeEntity.description, copyAndIdChangeEntity.description)

        age = 10
        val copyAndAgeChangeEntity = copyAndIdChangeEntity.copy(age=age)
        Assertions.assertEquals(copyAndIdChangeEntity.id, copyAndAgeChangeEntity.id)
        Assertions.assertEquals(copyAndIdChangeEntity.name, copyAndAgeChangeEntity.name)
        Assertions.assertNotEquals(copyAndIdChangeEntity.age, copyAndAgeChangeEntity.age)
        Assertions.assertEquals(age, copyAndAgeChangeEntity.age)
        Assertions.assertEquals(copyAndIdChangeEntity.description, copyAndAgeChangeEntity.description)

    }

    @Test
    fun 널러블_타입_테스트() {

        val nullableName: String? = null // ✅ Null을 허용

        // ❌ 컴파일 에러 발생 (개발시점에서 발견 가능)
      //  var nonNullableName: String = null

        var nonNullableString: String = "Hello"  // Null 허용 안됨
        var nullableString: String? = "Hello"  // Null 허용 가능
        nullableString = null  // ✅ 가능
       // nonNullableString = null  // ❌ 오류 발생

    }

    @Test
    fun 안전_호출_연산자_테스트(){

        val name: String? = null
        Assertions.assertNull(name?.length)

        var str: String? = "Hello"

        //println(str.length)   // ❌ 컴파일 오류 (Nullable 타입)
        Assertions.assertEquals(5, str?.length)

    }

}