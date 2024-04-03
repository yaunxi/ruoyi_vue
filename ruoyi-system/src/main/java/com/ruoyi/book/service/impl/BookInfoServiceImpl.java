package com.ruoyi.book.service.impl;

import com.ruoyi.book.domain.BookInfo;
import com.ruoyi.book.mapper.BookInfoMapper;
import com.ruoyi.book.service.IBookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
@Service
public class BookInfoServiceImpl implements IBookInfoService {
    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Override
    public List<BookInfo> selectBookList(BookInfo bookInfo){

        return bookInfoMapper.selectBookList(bookInfo);
    }
    @Override
    public BookInfo selectById(String id){
        return bookInfoMapper.selectById(id);
    }
    @Override
    public int insertBookInfo(BookInfo bookInfo){
        return bookInfoMapper.insertBookInfo(bookInfo);
    }

    @Override
    public int updateBookInfo(BookInfo bookInfo){
        return bookInfoMapper.updateBookInfo(bookInfo);
    }
}
