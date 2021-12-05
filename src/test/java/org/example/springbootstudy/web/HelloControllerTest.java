package org.example.springbootstudy.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
                            // SpringRunner 스프링 실행자 사용
                            // 스프링 부트 테스트와 JUnit 사이에 연결자 역할
@WebMvcTest(controllers = HelloController.class) //Web(Spring MVC)에 집중할 수 있는 어노테이션
                                                // 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있다.
                                                // 단, @Service, @Component, @Repository 등은 사용 불가
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name) // API 테스트할때 사용될 요청 파라미터를 설정합니다. //단, 값은 String만 허용 //숫자/날짜 등의 데이터도 등록할떄는 문자열로 변경해야만 가능
                        .param("amount", String.valueOf(amount)))
                                        .andExpect(status().isOk())
                                        .andExpect(jsonPath("$.name", is(name))) //jsonPaht : JSON 응닶값을 필드별로 검증할 수 있는 메소드 // $를 기준으로 필드명 명시
                                        .andExpect(jsonPath("$.amount", is(amount)));
    }
}
