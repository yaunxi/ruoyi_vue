package com.ruoyi.web.controller.book;

import com.ruoyi.book.domain.BookInfo;
import com.ruoyi.book.service.IBookInfoService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/book/bookInfo")
//@DataSource(value = DataSourceType.SLAVE)
public class BookInfoController extends BaseController {
    @Autowired
    private IBookInfoService bookInfoService;

    /**
     * 获取图书信息列表
     */
    @PreAuthorize("@ss.hasPermi('book:bookInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookInfo bookInfo)
    {
        startPage();
        List<BookInfo> list = bookInfoService.selectBookList(bookInfo);
        return getDataTable(list);
    }
    /**
     * 导出图书信息列表
     */
    @Log(title = "图书管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('book:bookInfo:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookInfo bookInfo)
    {
        List<BookInfo> list = bookInfoService.selectBookList(bookInfo);
        ExcelUtil<BookInfo> util = new ExcelUtil<BookInfo>(BookInfo.class);
        util.exportExcel(response, list, "书籍数据");
    }
    /**
     * 导入图书信息列表
     */
    @Log(title = "图书管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('book:bookInfo:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        return success();
    }

    /**
     * 获取图书信息详情
     */
    @PreAuthorize("@ss.hasPermi('book:bookInfo:detail')")
    @GetMapping("/detail/{id}")
    public BookInfo detail(@PathVariable String id)
    {
        BookInfo bookInfo =  bookInfoService.selectById(id);
        return bookInfo;
    }



    /**
     * 新增图书信息
     */
    @PreAuthorize("@ss.hasPermi('book:bookInfo:add')")
    @Log(title = "图书信息管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody BookInfo bookInfo)
    {
        return toAjax(bookInfoService.insertBookInfo(bookInfo));
    }

    /**
     * 修改保存图书信息
     */
    @PreAuthorize("@ss.hasPermi('book:bookInfo:edit')")
    @Log(title = "图书信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody  BookInfo bookInfo)
    {
        return toAjax(bookInfoService.updateBookInfo(bookInfo));
    }
}
