package com.ruoyi.book.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class BookInfo {
    /** ID */
    private String id;

    /** 书名 */
    private String bookName;

    /** 总数量 */
    private int number;

    /** 价格 */
    private BigDecimal bookPrice;

    /** 存放位置 */
    private String bookPlace;

    /** 在馆册数 */
    private int inLibraryNum;

    /** 作者*/
    private String author;

    /** 出版社 */
    private String publishing;

    /** 书籍类别 */
    private String bookType;

    /** 创建人 */
    private String createBy;

    /** 创建时间*/
    private Date createTime;

    /** 修改人 */
    private String updateBy;

    /** 修改时间 */
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookPlace() {
        return bookPlace;
    }

    public void setBookPlace(String bookPlace) {
        this.bookPlace = bookPlace;
    }

    public int getInLibraryNum() {
        return inLibraryNum;
    }

    public void setInLibraryNum(int inLibraryNum) {
        this.inLibraryNum = inLibraryNum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
