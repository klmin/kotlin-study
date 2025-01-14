package com.example.kotlinstudy.java;

import com.example.kotlinstudy.java.member.api.service.JavaApiService;
import com.example.kotlinstudy.java.member.entity.Member;
import com.example.kotlinstudy.java.member.entity.MemberRecord;
import com.example.kotlinstudy.java.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class JavaTests {

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public JavaApiService javaApiService;

    @Test
    void 자바_객체_테스트() throws JsonProcessingException {

        Long id = 200L;
        String name = "testName";
        Integer age = 30;

        var memberRecord = MemberRecord.builder()
                           .id(id)
                           .name(name)
                           .age(age)
                           .build();

        var member = Member.builder()
                                 .id(id)
                                 .name(name)
                                 .age(age)
                                 .build();

        assertEquals(id, memberRecord.id());
        assertEquals(name, memberRecord.name());
        assertEquals(age, memberRecord.age());
        Assertions.assertNull(memberRecord.description());

        var writeAsStringMemberRecord = objectMapper.writeValueAsString(memberRecord);
        Assertions.assertNotNull(writeAsStringMemberRecord);

        var writeAsStringMember = objectMapper.writeValueAsString(member);
        Assertions.assertNotNull(writeAsStringMember);

        var readValueMemberRecord = objectMapper.readValue(writeAsStringMemberRecord, MemberRecord.class);
        assertEquals(memberRecord, readValueMemberRecord);
        Assertions.assertFalse(memberRecord == readValueMemberRecord);
        assertEquals(memberRecord.hashCode(), readValueMemberRecord.hashCode());

        var readValueMember = objectMapper.readValue(writeAsStringMemberRecord, Member.class);
        Assertions.assertNotEquals(member, readValueMember);
        Assertions.assertFalse(member == readValueMember);
        Assertions.assertNotEquals(member.hashCode(), readValueMember.hashCode());

        var convertValueMemberRecord = objectMapper.convertValue(readValueMemberRecord, MemberRecord.class);
        assertEquals(readValueMemberRecord, convertValueMemberRecord);
        Assertions.assertFalse(readValueMemberRecord == convertValueMemberRecord);
        assertEquals(readValueMemberRecord.hashCode(), convertValueMemberRecord.hashCode());

        var convertValueMember = objectMapper.convertValue(readValueMemberRecord, Member.class);
        Assertions.assertNotEquals(readValueMember, convertValueMember);
        Assertions.assertFalse(readValueMember == convertValueMember);
        Assertions.assertNotEquals(readValueMember.hashCode(), convertValueMember.hashCode());

        id = 1L;
        name = "testName2";
        age = 20;

        var idNameAgeChangeMemberRecord = new MemberRecord(1L, "testName2", 20, null);
        assertEquals(id, idNameAgeChangeMemberRecord.id());
        assertEquals(name, idNameAgeChangeMemberRecord.name());
        assertEquals(age, idNameAgeChangeMemberRecord.age());
        Assertions.assertNull(idNameAgeChangeMemberRecord.description());

        id = 2L;
        name = "testName3";
        String description = "test_description";

        var copyAndIdNameChangeEntity = idNameAgeChangeMemberRecord.toBuilder()
                                                                 .id(id)
                                                                 .name(name)
                                                                 .description(description)
                                                                 .build();

        assertEquals(id, copyAndIdNameChangeEntity.id());
        assertEquals(name, copyAndIdNameChangeEntity.name());
        assertEquals(age, copyAndIdNameChangeEntity.age());
        assertEquals(description, copyAndIdNameChangeEntity.description());

        id = 3L;

        var copyAndIdChangeEntity = copyAndIdNameChangeEntity.toBuilder().id(3L).build();
        assertEquals(id, copyAndIdChangeEntity.id());
        assertEquals(copyAndIdNameChangeEntity.name(), copyAndIdChangeEntity.name());
        assertEquals(copyAndIdNameChangeEntity.age(), copyAndIdChangeEntity.age());
        assertEquals(copyAndIdNameChangeEntity.description(), copyAndIdChangeEntity.description());

        age = 10;

        var copyAndAgeChangeEntity = copyAndIdChangeEntity.toBuilder().age(10).build();
        assertEquals(copyAndIdChangeEntity.id(), copyAndAgeChangeEntity.id());
        assertEquals(copyAndIdChangeEntity.name(), copyAndAgeChangeEntity.name());
        assertEquals(age, copyAndAgeChangeEntity.age());
        assertEquals(copyAndIdChangeEntity.description(), copyAndAgeChangeEntity.description());

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

    @Test
    void 엘비스_연산자_테스트(){

        String name = null;
        String replacement = "Default Name";
        String result = name == null ? replacement : name;
        assertEquals(replacement, result);

        var memberData = MemberRecord.builder().id(1L).name(name).age(10).description(null).build();

        assertEquals("Guest", getUserName(memberData)); // 출력: "Guest"

        name = "test";
        memberData = memberData.toBuilder().name(name).build();
        assertEquals(name, getUserName(memberData));

    }

    public String getUserName(MemberRecord member) {
        return (member!= null && member.name() != null) ? member.name() : "Guest";
    }

    @Test
    void 널_아님_단언_연산자(){

        MemberRecord memberRecord = javaApiService.getMemberData(1L);
        Assertions.assertNotNull(memberRecord);

        MemberRecord exceptionTestMemberRecord = javaApiService.getMemberData(null);
        Assertions.assertThrows(NullPointerException.class, () -> System.out.println(exceptionTestMemberRecord.name()));

    }

    @Nested
    class 함수형_인터페이스_지원{

        @Test
        void 람다_함수(){
            Function<Integer, Integer> square = x -> x * x;
            Assertions.assertEquals(25, square.apply(5));
        }

        @Test
        void 컨슈머(){
            Consumer<String> consumer = System.out::println;
            consumerTest("Java!", consumer);
            consumerTest("Java", System.out::println);
        }

        public void consumerTest(String str, Consumer<String> printMessage) {
            printMessage.accept("Hello "+str);
        }

        @Test
        void 서플라이어(){
            Supplier<String> supplier = () -> "Java!";
            Assertions.assertEquals("Hello Java!", supplierTest(supplier));
            Assertions.assertEquals("Hello Java", supplierTest(() -> "Java"));
        }

        public String supplierTest(Supplier<String> supplier) {
            return "Hello "+supplier.get();
        }

        @Test
        void stream(){

            // startWith 판별
            List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
            List<String> filtered = names.stream()
                                         .filter(name -> name.startsWith("A"))
                                         .toList();

            Assertions.assertEquals(1, filtered.size());

            // uppercase 판별
            List<String> upperCases = names.stream()
                                               .map(String::toUpperCase)
                                               .toList();

            List<String> upperCaseNames = Arrays.asList("ALICE", "BOB", "CHARLIE");
            Assertions.assertEquals(upperCases, upperCaseNames);

            // 짝수 판별
            List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
            List<Integer> result = numbers.stream()
                                          .filter(n -> n % 2 == 0)
                                          .map(n -> n * 10)
                                          .toList();

            List<Integer> expected = List.of(20, 40, 60);

            Assertions.assertEquals(expected, result);

        }

        @Test
        void 익명함수(){
            Function<Integer, Integer> square = x -> x * x;
            System.out.println(square.apply(5));
        }

        @Test
        void 고차함수(){
            int function = applyFunction(10, x -> x * x);
            Assertions.assertEquals(100, function);
        }

        public int applyFunction(int x, Function<Integer, Integer> func) {
            return func.apply(x);
        }

    }

    @Nested
    class 스코프_함수_지원{

        @Test
        void let(){
            String name = "Alice";
            int nameLength = 0;
            if (name != null) {
                System.out.println("Hello, "+name);
                nameLength = name.length();
            }
            Assertions.assertEquals(nameLength, name.length());

        }

        @Test
        void apply(){

            Long initId = 1L;
            String initName = "testName";
            int initAge = 10;

            String changeName = "changeName";
            int changeAge = 30;

            User user = new User(initId, initName, initAge);
            user.setName(changeName);
            user.setAge(changeAge);

            Assertions.assertEquals(initId, user.getId());
            Assertions.assertNotEquals(initAge, user.getAge());
            Assertions.assertEquals(changeAge, user.getAge());
            Assertions.assertEquals(changeName, user.getName());

        }

        @Test
        void run(){

            List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
            System.out.println("numbers : "+numbers);

            int numbersSum = numbers.stream().mapToInt(Integer::intValue).sum();
            System.out.println("numbersSum : "+numbersSum);

            Assertions.assertEquals(21, numbersSum);

        }

        @Test
        void also(){

            User user = new User(1L, "testName", 20);
            System.out.println("user : "+user);

            user.setAge(user.getAge() + 1);

            if(user.getAge() < 20){
                throw new IllegalArgumentException("20세 이상 이어야 합니다.");
            }

            System.out.println("user : "+user);

            Assertions.assertEquals(21, user.getAge());
        }

        @Test
        void with(){

            User user = new User(1L, "testName", 25);

            System.out.println("init user : "+user);
            user.setAge(user.getAge() + 5);
            System.out.println("changeAge user : "+user);

            Assertions.assertEquals(30, user.getAge());


        }
    }

    public int getLength(Object obj) {
        if (obj instanceof String) {
            return ((String) obj).length();
        }
        if(obj instanceof Integer){
            return String.valueOf(obj).length();
        }
        return 0;

    }

    public int getLengthJdk16(Object obj) {
        if (obj instanceof String str) {
            return str.length();
        }
        if(obj instanceof Integer i){
            return String.valueOf(i).length();
        }
        return 0;

    }
    public int getLengthJdk21(Object obj) {
        return switch (obj) {
            case String str -> str.length();
            case Integer i  -> String.valueOf(i).length();
            default         -> 0;
        };
    }

    @Test
    void 스마트캐스팅(){

        Object obj = "Alice";
        Assertions.assertEquals(5, getLength(obj));
        Assertions.assertEquals(5, getLengthJdk16(obj));
        Assertions.assertEquals(5, getLengthJdk21(obj));
        obj = 3;
        Assertions.assertEquals(1, getLength(obj));
        Assertions.assertEquals(1, getLengthJdk16(obj));
        Assertions.assertEquals(1, getLengthJdk21(obj));

    }

    @Test
    void 타입_추론_및_불변성_제공(){
        final String name = "Alice";
        int age = 25;

        // name = "John"; // ❌ 컴파일 오류
        age = 20; // ✅ 가능
    }


}
