package com.miniservehub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * 用户更新数据传输对象
 * 
 * @author MiniServeHub Team
 * @version 1.0.0
 */
public class UserUpdateDTO {

    @Size(max = 20, message = "真实姓名长度不能超过20个字符")
    private String realName;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Size(max = 11, message = "手机号长度不能超过11位")
    private String phone;

    private String avatar;

    private Integer gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String remark;

    // 构造函数
    public UserUpdateDTO() {}

    // Getters and Setters
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "UserUpdateDTO{" +
                "realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                '}';
    }
}
