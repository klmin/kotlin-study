package com.example.kotlinstudy.kt


import com.example.kotlinstudy.kt.member.api.service.ApiService
import com.example.kotlinstudy.kt.member.entity.Member
import com.example.kotlinstudy.kt.member.entity.MemberData
import com.example.kotlinstudy.kt.user.entity.UserData
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KotlinTests @Autowired constructor(
    private val ApiService: ApiService
){

    private val objectMapper = jacksonObjectMapper();
    private val logger = KotlinLogging.logger {}

    @Test
    @DisplayName("코틀린 객체 테스트")
    fun 코틀린_객체_테스트() {

        var id : Long = 200L
        var name : String = "testName"
        var age : Int = 30
        var description : String? = null

        val memberData = MemberData(id=id, name = name, age=age, description = description)
        Assertions.assertEquals(id, memberData.id)
        Assertions.assertEquals(name, memberData.name)
        Assertions.assertEquals(age, memberData.age)
        Assertions.assertNull(memberData.description)

        val member = Member(id=id, name = name, age=age, description = description)
        Assertions.assertNotEquals(memberData.hashCode(), member.hashCode())

        val writeAsStringMemberData = objectMapper.writeValueAsString(memberData);
        Assertions.assertNotNull(writeAsStringMemberData)

        val writeAsStringMember = objectMapper.writeValueAsString(member);
        Assertions.assertNotNull(writeAsStringMember)

        val readValueMemberData = objectMapper.readValue(writeAsStringMemberData, MemberData::class.java)
        Assertions.assertEquals(memberData.hashCode(), readValueMemberData.hashCode())
        Assertions.assertEquals(memberData, readValueMemberData)
        Assertions.assertTrue(memberData == readValueMemberData)
        Assertions.assertFalse(memberData === readValueMemberData)
        Assertions.assertNotEquals(System.identityHashCode(memberData), System.identityHashCode(readValueMemberData))

        val readValueMember = objectMapper.readValue(writeAsStringMember, Member::class.java)
        Assertions.assertNotEquals(member.hashCode(), readValueMember.hashCode())
        Assertions.assertNotEquals(member, readValueMember)
        Assertions.assertFalse(member == readValueMember)
        Assertions.assertFalse(member === readValueMember)
        Assertions.assertNotEquals(System.identityHashCode(member), System.identityHashCode(readValueMember))

        val convertValueMemberData = objectMapper.convertValue(readValueMemberData, MemberData::class.java)
        Assertions.assertEquals(readValueMemberData.hashCode(), convertValueMemberData.hashCode())
        Assertions.assertEquals(readValueMemberData, convertValueMemberData)
        Assertions.assertTrue(readValueMemberData == convertValueMemberData)
        Assertions.assertFalse(readValueMemberData === convertValueMemberData)
        Assertions.assertNotEquals(System.identityHashCode(readValueMemberData), System.identityHashCode(convertValueMemberData))

        val convertValueMember = objectMapper.convertValue(readValueMember, Member::class.java)
        Assertions.assertNotEquals(readValueMember.hashCode(), convertValueMember.hashCode())
        Assertions.assertNotEquals(readValueMember, convertValueMember)
        Assertions.assertFalse(readValueMember == convertValueMember)
        Assertions.assertFalse(readValueMember === convertValueMember)
        Assertions.assertNotEquals(System.identityHashCode(readValueMember), System.identityHashCode(convertValueMember))

        id = 1
        name = "testName2"
        age = 20
        description = null

        val idNameAgeChangeMemberDataData = MemberData(id, name, age, description)

        Assertions.assertNotEquals(convertValueMember.id, idNameAgeChangeMemberDataData.id)
        Assertions.assertNotEquals(convertValueMember.name, idNameAgeChangeMemberDataData.name)
        Assertions.assertNotEquals(convertValueMember.age, idNameAgeChangeMemberDataData.age)

        Assertions.assertEquals(id, idNameAgeChangeMemberDataData.id)
        Assertions.assertEquals(name, idNameAgeChangeMemberDataData.name)
        Assertions.assertEquals(age, idNameAgeChangeMemberDataData.age)
        Assertions.assertEquals(convertValueMember.description, idNameAgeChangeMemberDataData.description)

        id = 2
        name = "testName3"
        description = "test_description"

        val copyAndIdNameDescriptionChangeMemberData = idNameAgeChangeMemberDataData.copy(id=id, name=name, description = description)
        Assertions.assertNotEquals(idNameAgeChangeMemberDataData.id, copyAndIdNameDescriptionChangeMemberData.id)
        Assertions.assertNotEquals(idNameAgeChangeMemberDataData.name, copyAndIdNameDescriptionChangeMemberData.name)
        Assertions.assertNotEquals(idNameAgeChangeMemberDataData.description, copyAndIdNameDescriptionChangeMemberData.description)

        Assertions.assertEquals(id, copyAndIdNameDescriptionChangeMemberData.id)
        Assertions.assertEquals(name, copyAndIdNameDescriptionChangeMemberData.name)
        Assertions.assertEquals(idNameAgeChangeMemberDataData.age, copyAndIdNameDescriptionChangeMemberData.age)

        id = 3L
        val copyAndIdChangeMemberData = copyAndIdNameDescriptionChangeMemberData.copy(id=id)
        Assertions.assertEquals(id, copyAndIdChangeMemberData.id)
        Assertions.assertNotEquals(copyAndIdNameDescriptionChangeMemberData.id, copyAndIdChangeMemberData.id)
        Assertions.assertEquals(copyAndIdNameDescriptionChangeMemberData.name, copyAndIdChangeMemberData.name)
        Assertions.assertEquals(copyAndIdNameDescriptionChangeMemberData.age, copyAndIdChangeMemberData.age)
        Assertions.assertEquals(copyAndIdNameDescriptionChangeMemberData.description, copyAndIdChangeMemberData.description)

        age = 10
        val copyAndAgeChangeMemberData = copyAndIdChangeMemberData.copy(age=age)
        Assertions.assertEquals(copyAndIdChangeMemberData.id, copyAndAgeChangeMemberData.id)
        Assertions.assertEquals(copyAndIdChangeMemberData.name, copyAndAgeChangeMemberData.name)
        Assertions.assertNotEquals(copyAndIdChangeMemberData.age, copyAndAgeChangeMemberData.age)
        Assertions.assertEquals(age, copyAndAgeChangeMemberData.age)
        Assertions.assertEquals(copyAndIdChangeMemberData.description, copyAndAgeChangeMemberData.description)

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

    @Test
    fun 엘비스_연산자_테스트(){

        var name: String? = null
        val replacement = "Default Name"
        val result = name ?: replacement // ✅ name이 null이면 "Default Name" 반환
        Assertions.assertEquals(replacement, result)

        var memberData = MemberData(id=1L, name=name, age=10, description=null)

        Assertions.assertEquals("Guest", getUserName(memberData)) // 출력: "Guest"

        name = "test"
        memberData = memberData.copy(name=name)
        Assertions.assertEquals(name, getUserName(memberData))

    }

    fun getUserName(user: MemberData?): String {
        return user?.name ?: "Guest"
    }

    @Test
    fun 널_아님_단언_연산자(){

        val name: String? = getValueAndReplacement(null)
        val result:String = name!!
        Assertions.assertNotNull(result)

        val memberData = ApiService.getMemberData(1)!!
        Assertions.assertNotNull(memberData)
        Assertions.assertThrows(NullPointerException::class.java) { ApiService.getMemberData(null)!! }
    }

    fun getValueAndReplacement(replacement:String?): String? {
        return replacement ?: "test"
    }

    @Nested
    inner class 함수형_인터페이스_지원{

        @Test
        fun 람다_함수() {
            val square: (Int) -> Int = { x -> x * x }
            Assertions.assertEquals(25, square(5))
            val sum = fun(a: Int, b: Int): Int = a + b
            println(sum);
        }

        @Test
        fun 컨슈머() {
            val consumer: (String) -> Unit = { println(it) }

            consumerTest("Kotlin!", consumer)
            consumerTest("Kotlin"){ println(it)}
        }

        fun consumerTest(str:String, consumer: (String) -> Unit) {
            consumer("Hello $str")
        }

        @Test
        fun 서플라이어(){
            val supplier: () -> String = { "Kotlin!" }
            Assertions.assertEquals("Hello Kotlin!", supplierTest(supplier))
            Assertions.assertEquals("Hello Kotlin", supplierTest{"Kotlin"})
        }

        fun supplierTest(supplier: () -> String): String {
            return "Hello ${supplier()}"
        }

        @Test
        fun stream() {

            // startsWith 판별
            val names = listOf("Alice", "Bob", "Charlie")
            val filtered = names.filter { it.startsWith("A") }
            Assertions.assertEquals(1, filtered.size)

            // uppercase 판별
            val upperCases = listOf("ALICE", "BOB", "CHARLIE")
            val upperCaseNames = names.map { it.uppercase() }
            Assertions.assertEquals(upperCases, upperCaseNames)

            val numbers = listOf(1, 2, 3, 4, 5, 6)
            val result = numbers.filter{it % 2 == 0}
                                .map { it * 10 }

            println(result)

            val expected = listOf(20, 40, 60)
            Assertions.assertEquals(expected, result)

        }

        @Test
        fun 고차함수(){
            Assertions.assertEquals(100, applyFunction(10){it * it})
        }

        private fun applyFunction(x: Int, func: (Int) -> Int): Int {
            return func(x)
        }

        @Test
        fun 인라인함수(){
            val func = { x: Int -> x * 2 }
            Assertions.assertEquals(10, applyInlineFunction(5, func))
        }

        private inline fun applyInlineFunction(x: Int, func: (Int) -> Int): Int {
            return func(x)
        }

        @Test
        fun 기본값(){

            var result = sum(3)
            Assertions.assertEquals(5, result)

            result = sum(3, 3)
            Assertions.assertEquals(6, result)
        }

        private fun sum(a: Int, b: Int = 2) = a + b

        @Test
        fun 네임드_파라미터(){
            var result = greet()
            Assertions.assertEquals("Hello, Guest! Welcome!", result)

            result = greet(name="Lim")
            Assertions.assertEquals("Hello, Lim! Welcome!", result)

            result = greet(msg="Good Morning!")
            Assertions.assertEquals("Hello, Guest! Good Morning!", result)
        }

        private fun greet(name: String = "Guest", msg: String = "Welcome!") = "Hello, $name! $msg"

    }

    @Nested
    inner class 스코프_함수_지원{

        @Test
        fun let(){

            val name: String? = "Alice"

            val nameLength = name?.let {
                println("Hello, $it") // ✅ `name`이 `null`이 아니면 실행
                it.length
            }
            Assertions.assertEquals(5, nameLength)

        }

        @Test
        fun apply(){

            val initId = 1L
            val initName = "testName"
            val initAge = 10

            val changeName = "changeName"
            val changeAge = 30

            val user = UserData(initId, initName, initAge).apply {
                println("init : $this")
                name = "changeName"
                println("name change : $this")
            }.apply{
                age = changeAge
                println("age change : $this")
            }

            Assertions.assertEquals(initId, user.id)
            Assertions.assertNotEquals(initAge, user.age)
            Assertions.assertEquals(changeAge, user.age)
            Assertions.assertEquals(changeName, user.name)
        }

        @Test
        fun run(){

            val numbers = listOf(1, 2, 3, 4, 5, 6)
            val numbersSum = numbers.run{
                println("numbers : $numbers")
                sum()
            }

            Assertions.assertEquals(21, numbersSum)
        }

        @Test
        fun also(){

            val userData = UserData(1L, "testName", 20).also {
                println("user : $it")
                it.age++
            }.also{
                require(it.age >= 20){
                    println("20세 이상 이여야 함.")
                }
            }.also{
                println("after : $it")
            }

            Assertions.assertEquals(21, userData.age)

        }

        @Test
        fun with(){

            val user = UserData(1L, "testName", 25)

            val age = with(user) {
                println("Name: $name, Age: $age")
                age + 5
            }

            println("age : $age")
            Assertions.assertEquals(30, age)

        }

    }

    @Test
    fun 확장함수(){
        val test = "ABCD"
        println(test.test())
        println(test.appendHello())
        println(test.greet())

    }

    fun String.test(): String {
        return this.substring(1)
    }

    fun String.appendHello(): String {
        return "Hello $this"
    }

    fun String.greet() = "Hello, $this"

    fun getLengthIf(obj: Any): Int{
        if(obj is String){
            return obj.length
        }
        if(obj is Int){
            return obj.toString().length
        }
        return 0
    }

    fun getLength(obj: Any): Int = when(obj){
        is String -> obj.length
        is Int -> obj.toString().length
        else -> 0
    }

    @Test
    fun 스마트캐스팅(){
        var obj: Any = "Alice"
        Assertions.assertEquals(5, getLength(obj))
        Assertions.assertEquals(5, getLengthIf(obj))
        obj = 1
        Assertions.assertEquals(1, getLength(obj))
        Assertions.assertEquals(1, getLengthIf(obj))
    }

    @Test
    fun 타입추론_및_불변성_제공(){
        val name = "Alice"  // 불변 (Immutable)
        var age = 25        // 가변 (Mutable)

       // name = "John" // ❌ 컴파일 오류
        age = 20 // ✅ 가능
    }

    @Test
    fun for문(){

        // 범위를 활용한 for 문
        for (i in 0..4) {
            println("Index: $i")
        }

        // 리스트를 직접 순회
        val names = listOf("Alice", "Bob", "Charlie")
        for (name in names) {
            println(name)
        }

        // 인덱스와 함께 요소 접근
        for ((index, name) in names.withIndex()) {
            println("Index: $index, Name: $name")
        }

    }

    @Test
    fun 구조_분해_할당(){

        // 구조 분해 할당
        val (first, second, third) = listOf("Alice", "Bob", "Charlie")
        println(first)  // Alice
        println(second) // Bob
        println(third)  // Charlie

        Assertions.assertEquals("Alice", first)
        Assertions.assertEquals("Bob", second)
        Assertions.assertEquals("Charlie", third)

        // 🔹 특정 값만 추출 (첫 번째와 세 번째 값 무시)
        val (_, name, _) = listOf("Alice", "Bob", "Charlie")

        println(name) // Bob

        Assertions.assertEquals("Bob", name)

        val map = mapOf(1 to "One", 2 to "Two")

        for ((key, value) in map) {
            println("Key: $key, Value: $value")
        }

    }


}