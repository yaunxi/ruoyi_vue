package com.ruoyi.book.mapper;

import com.ruoyi.book.domain.BookInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *图书信息
 *  */
@Mapper
public interface BookInfoMapper {
    //查询图书信息列表
    public List<BookInfo> selectBookList(BookInfo bookInfo);

    //图书信息详情
    public BookInfo selectById(String id);

    //新增
    public int insertBookInfo(BookInfo bookInfo);

    //修改
    public int updateBookInfo(BookInfo bookInfo);
}
