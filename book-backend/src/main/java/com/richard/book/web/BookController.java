package com.richard.book.web;

import com.richard.book.domain.Book;
import com.richard.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody Book book){
        return new ResponseEntity<>(bookService.저장하기(book), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/book")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(bookService.모두가져오기(), HttpStatus.OK);
    }
    @GetMapping("/book/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(bookService.한건가져오기(id), HttpStatus.OK);
    }
    @PutMapping("/book/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Book book){
        return new ResponseEntity<>(bookService.수정하기(id, book), HttpStatus.OK);
    }
    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return new ResponseEntity<>(bookService.삭제하기(id), HttpStatus.OK);
    }



}
