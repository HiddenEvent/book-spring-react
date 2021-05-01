package com.richard.book.web;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.richard.book.domain.Book;
import com.richard.book.domain.BookRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
- 통합 테스트 (모든 Bean들을 똑같이 IOC에 올리고 테스트 하는 것)
 WebEnvironment.MOCK = 실제 톰켓을 올리는게 아니라, 다른 톰켓으로 테스트(가짜)
 WebEnvironment.RANDOM_POR = 실제 톰켓으로 테스트
 @AutoConfigureMockMvc = MockMvc를 ioc에 등록해줌.
 @Transactional = 각 각의 테스트함수가 종료될 때마다 트랜잭션을 rollback 해주는 어노테이션!
 */

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BookControllerIntegreTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach // 하나의 테스트가 끝날때 마다 액션.
    public void init() { // AUTO_INCREMENT 1로 초기화
//        entityManager.createNativeQuery("ALTER TABLE book AUTO_INCREMENT=1").executeUpdate();  // mysql용
        entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }
    @AfterEach
    public void end() {
//        bookRepository.deleteAll();
    }

    // BDDMockito 패턴 => given, when, then 으로 구성되어 있음
    @Test
    public void save_테스트() throws Exception {
        // given ( 테스트를 하기 위한 준비) => 받을 데이터 준비
        Book book = Book.builder().title("스프링 따라하기").author("코스").build();
        String content = new ObjectMapper().writeValueAsString(book);

        // when(테스트 실행)
        ResultActions resultActions = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8) // contentType: 던지는 데이터 타입이 무엇이냐?
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8)); //accept: 응답객체가 무엇이냐?

        // then(검증)
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("스프링 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findAll_테스트() throws Exception {
        //given
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().title("스프링부트 따라하기").author("코스").build());
        books.add(Book.builder().title("리엑트 따라하기").author("코스").build());
        books.add(Book.builder().title("Junit 따라하기").author("코스").build());
        bookRepository.saveAll(books);

        //when
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[0].title").value("스프링부트 따라하기"))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void findById_테스트() throws  Exception {
        //given
        Long id = 2L;
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().title("스프링부트 따라하기").author("코스").build());
        books.add(Book.builder().title("리엑트 따라하기").author("코스").build());
        books.add(Book.builder().title("Junit 따라하기").author("코스").build());
        bookRepository.saveAll(books);


        //when
        ResultActions resultActions = mockMvc.perform(get("/book/{id}",id)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("리엑트 따라하기"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void update_테스트() throws  Exception {
        //given
        Long id = 1L;
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().title("스프링부트 따라하기").author("코스").build());
        books.add(Book.builder().title("리엑트 따라하기").author("코스").build());
        books.add(Book.builder().title("Junit 따라하기").author("코스").build());
        bookRepository.saveAll(books);

        Book book = Book.builder().title("C++ 따라하기").author("코스").build();
        String content = new ObjectMapper().writeValueAsString(book);

        //when
        ResultActions resultActions = mockMvc.perform(put("/book/{id}",id)
                .contentType(MediaType.APPLICATION_JSON_UTF8) // contentType: 던지는 데이터 타입이 무엇이냐?
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8)); //accept: 응답객체가 무엇이냐?

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("C++ 따라하기"))
                .andDo(MockMvcResultHandlers.print());

    }


}
