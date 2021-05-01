package com.richard.book.domain;




import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

// 단위 테스트 (DB 관련된 Bean이 IOC에 등록되면 됨)

@Transactional  // 메서드 실행시 마다 롤백
@AutoConfigureTestDatabase(replace = Replace.ANY) // Replace.ANY - 가짜 디비로 테스트, Replace.NONE - 실제 DB로 테스트
@DataJpaTest  // Repository들을 다 IoC에 등록해 둔다.
public class BookRepositoryUnitTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void save_테스트(){
        // given
        Book book = new Book(null,"책제목1","책저자1");

        // when
        Book bookEntity = bookRepository.save(book);

        // then
        assertEquals("책제목1", bookEntity.getTitle());

    }


}
