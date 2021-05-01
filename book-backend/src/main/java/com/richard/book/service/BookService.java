package com.richard.book.service;

import com.richard.book.domain.Book;
import com.richard.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public Book 저장하기(Book book){
        return bookRepository.save(book);
    }
    
    @Transactional(readOnly = true) // JPA 변경감지라는 내부 기능 활성화 X, update시의 정합성을 유지해줌. insert의 유령데이터현상(팬텀현상) 못막음
    public Book 한건가져오기(Long id){
        return bookRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("id를 확인해주세요"));
    }
    @Transactional(readOnly = true)
    public List<Book> 모두가져오기(){
        return bookRepository.findAll();
    }
    @Transactional
    public Book 수정하기(Long id, Book book){
        Book bookEntity = bookRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요"));
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        return bookEntity;
    } // 함수 종료 -> 트랜잭션 종료 -> 영속화되엉 있는 데이터 DB로 갱신

    @Transactional
    public String 삭제하기(Long id) {
        bookRepository.deleteById(id);
        return "ok";
    }
}
