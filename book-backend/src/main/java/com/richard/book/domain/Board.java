package com.richard.book.domain;

import com.richard.book.common.BoardType;
import lombok.Data;

@Data
public class Board {
    private int boardSeq;
    private BoardType boardType;
    private String titile;
    private String contents;
    private Data regData;

}
