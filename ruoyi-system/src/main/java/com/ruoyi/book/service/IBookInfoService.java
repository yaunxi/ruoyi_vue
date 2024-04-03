package com.ruoyi.book.service;

import com.ruoyi.book.domain.BookInfo;

import java.util.List;

public interface IBookInfoService {
     List<BookInfo> selectBookList(BookInfo bookInfo);

     BookInfo selectById(String id);

     public int insertBookInfo(BookInfo bookInfo);

     public int updateBookInfo(BookInfo bookInfo);
}
