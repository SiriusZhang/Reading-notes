package com.piaolink.admin.entity;

import java.util.Date;

public class OpAuditLog {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_audit_log.Id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_audit_log.user_id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_audit_log.admin_id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private Integer adminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_audit_log.operation_time
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private Date operationTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_audit_log.operation_type
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String operationType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column op_audit_log.remark
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_audit_log.Id
     *
     * @return the value of op_audit_log.Id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_audit_log.Id
     *
     * @param id the value for op_audit_log.Id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_audit_log.user_id
     *
     * @return the value of op_audit_log.user_id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_audit_log.user_id
     *
     * @param userId the value for op_audit_log.user_id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_audit_log.admin_id
     *
     * @return the value of op_audit_log.admin_id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public Integer getAdminId() {
        return adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_audit_log.admin_id
     *
     * @param adminId the value for op_audit_log.admin_id
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_audit_log.operation_time
     *
     * @return the value of op_audit_log.operation_time
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public Date getOperationTime() {
        return operationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_audit_log.operation_time
     *
     * @param operationTime the value for op_audit_log.operation_time
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_audit_log.operation_type
     *
     * @return the value of op_audit_log.operation_type
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_audit_log.operation_type
     *
     * @param operationType the value for op_audit_log.operation_type
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column op_audit_log.remark
     *
     * @return the value of op_audit_log.remark
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column op_audit_log.remark
     *
     * @param remark the value for op_audit_log.remark
     *
     * @mbggenerated Wed Apr 26 17:43:31 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}