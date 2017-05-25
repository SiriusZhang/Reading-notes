package com.piaolink.admin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpAdmin implements Serializable{
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_admin.Id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_admin.username
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_admin.real_name
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String realName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_admin.email
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_admin.mobile_phone_no
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String mobilePhoneNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_admin.password
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_admin.position
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String position;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_admin.role
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String role;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_admin.remark
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_admin.create_time
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_admin.Id
     *
     * @return the value of op_admin.Id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_admin.Id
     *
     * @param id the value for op_admin.Id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_admin.username
     *
     * @return the value of op_admin.username
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_admin.username
     *
     * @param username the value for op_admin.username
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_admin.real_name
     *
     * @return the value of op_admin.real_name
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_admin.real_name
     *
     * @param realName the value for op_admin.real_name
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_admin.email
     *
     * @return the value of op_admin.email
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_admin.email
     *
     * @param email the value for op_admin.email
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_admin.mobile_phone_no
     *
     * @return the value of op_admin.mobile_phone_no
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getMobilePhoneNo() {
        return mobilePhoneNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_admin.mobile_phone_no
     *
     * @param mobilePhoneNo the value for op_admin.mobile_phone_no
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setMobilePhoneNo(String mobilePhoneNo) {
        this.mobilePhoneNo = mobilePhoneNo == null ? null : mobilePhoneNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_admin.password
     *
     * @return the value of op_admin.password
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_admin.password
     *
     * @param password the value for op_admin.password
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_admin.position
     *
     * @return the value of op_admin.position
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getPosition() {
        return position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_admin.position
     *
     * @param position the value for op_admin.position
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_admin.role
     *
     * @return the value of op_admin.role
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_admin.role
     *
     * @param role the value for op_admin.role
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_admin.remark
     *
     * @return the value of op_admin.remark
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_admin.remark
     *
     * @param remark the value for op_admin.remark
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_admin.create_time
     *
     * @return the value of op_admin.create_time
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_admin.create_time
     *
     * @param createTime the value for op_admin.create_time
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}