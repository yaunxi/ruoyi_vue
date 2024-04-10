package com.ruoyi.web.controller.book;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.book.domain.UserInfo;
import com.ruoyi.book.service.IUserInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户信息管理Controller
 * 
 * @author ruoyi
 * @date 2024-04-03
 */
@RestController
@RequestMapping("/book/userInfo")
public class UserInfoController extends BaseController
{
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 查询用户信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('book:userInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserInfo userInfo)
    {
        startPage();
        List<UserInfo> list = userInfoService.selectUserInfoList(userInfo);
        return getDataTable(list);
    }

    /**
     * 导出用户信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('book:userInfo:export')")
    @Log(title = "用户信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserInfo userInfo)
    {
        List<UserInfo> list = userInfoService.selectUserInfoList(userInfo);
        ExcelUtil<UserInfo> util = new ExcelUtil<UserInfo>(UserInfo.class);
        util.exportExcel(response, list, "用户信息管理数据");
    }

    /**
     * 获取用户信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('book:userInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userInfoService.selectUserInfoById(id));
    }

    /**
     * 新增用户信息管理
     */
    @PreAuthorize("@ss.hasPermi('book:userInfo:add')")
    @Log(title = "用户信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserInfo userInfo)
    {
        userInfo.setPassword(SecurityUtils.encryptPassword(userInfo.getPassword()));
        userInfo.setCreateBy("system");
        return toAjax(userInfoService.insertUserInfo(userInfo));
    }
    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('book:userInfo:resetPwd')")
    @Log(title = "用户信息管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody JSONObject jsonObject)
    {
        Long id = Long.valueOf(jsonObject.get("id").toString()) ;
        String password = (String)jsonObject.get("password");
        String newPassword = (String)jsonObject.get("newPassword");
        UserInfo user = userInfoService.selectUserInfoById(id);
        if(user.getPassword().equals(password)){
            user.setPassword(SecurityUtils.encryptPassword(newPassword));
            user.setUpdateBy("system");
            return toAjax(userInfoService.resetPwd(user));
        }else {
           return AjaxResult.error("原密码错误！！");
        }

    }

    /**
     * 修改用户信息管理
     */
    @PreAuthorize("@ss.hasPermi('book:userInfo:edit')")
    @Log(title = "用户信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserInfo userInfo)
    {
        userInfo.setUpdateBy("system");
        return toAjax(userInfoService.updateUserInfo(userInfo));
    }

    /**
     * 删除用户信息管理
     */
    @PreAuthorize("@ss.hasPermi('book:userInfo:remove')")
    @Log(title = "用户信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userInfoService.deleteUserInfoByIds(ids));
    }
}
