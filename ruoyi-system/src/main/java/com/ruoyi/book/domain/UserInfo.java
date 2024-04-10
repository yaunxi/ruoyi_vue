package com.ruoyi.book.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户信息管理对象 user_info
 * 
 * @author ruoyi
 * @date 2024-04-03
 */
public class UserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 出生日期 */
    @Excel(name = "出生日期")
    private String birth;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String mobile;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String identity;

    /** 登录账号 */
    @Excel(name = "登录账号")
    private String accontNo;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long balance;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }
    public void setBirth(String birth) 
    {
        this.birth = birth;
    }

    public String getBirth() 
    {
        return birth;
    }
    public void setMobile(String mobile) 
    {
        this.mobile = mobile;
    }

    public String getMobile() 
    {
        return mobile;
    }
    public void setIdentity(String identity) 
    {
        this.identity = identity;
    }

    public String getIdentity() 
    {
        return identity;
    }
    public void setAccontNo(String accontNo) 
    {
        this.accontNo = accontNo;
    }

    public String getAccontNo() 
    {
        return accontNo;
    }
    public void setBalance(Long balance) 
    {
        this.balance = balance;
    }

    public Long getBalance() 
    {
        return balance;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userName", getUserName())
            .append("sex", getSex())
            .append("age", getAge())
            .append("birth", getBirth())
            .append("mobile", getMobile())
            .append("identity", getIdentity())
            .append("accontNo", getAccontNo())
            .append("balance", getBalance())
            .append("password", getPassword())
            .toString();
    }
}
