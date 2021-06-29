package com.yoa.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author liulei
 * @since 2021-05-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "id",orderNum = "0")
    public String id;

    /**
     * 员工姓名
     */
    @Excel(name = "姓名",orderNum = "1")
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别",orderNum = "2")
    private String gender;

    /**
     * 工号
     */
    @Excel(name = "工号",orderNum = "3")
    private String jobNumber;

    /**
     * 出生日期
     */
    @Excel(name = "出生日期",orderNum = "4")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱",orderNum = "5")
    private String email;

    /**
     * 电话号码
     */
    @Excel(name = "电话",orderNum = "6")
    @TableField("phoneNum")
    private String phonenum;

    /**
     * 地址
     */
    @Excel(name = "地址",orderNum = "7")
    private String address;

    /**
     * 民族
     */
    @Excel(name = "民族",orderNum = "8")
    private String nation;

    /**
     * 毕业院校
     */
    @Excel(name = "毕业院校",orderNum = "9")
    private String university;

    /**
     * 学历
     */
    @Excel(name = "学历",orderNum = "10")
    private String education;

    /**
     * 入职时间
     */
    @Excel(name = "入职时间",orderNum = "11")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryTime;

    /**
     * 部门
     */
    @Excel(name = "所属部门",orderNum = "12")
    private String department;

    /**
     * 职位
     */
    @Excel(name = "职位",orderNum = "13")
    private String position;

    /**
     * 合同开始时间
     */
    @Excel(name = "合同开始时间",orderNum = "14")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate contractKstime;

    /**
     * 合同结束时间
     */
    @Excel(name = "合同结束时间",orderNum = "15")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate contractJstime;

    /**
     * 合同期限
     */
    @Excel(name = "合同期限(年)",orderNum = "16")
    private Integer timeLimit;


    /**
     * 是否创建系统账号
     * */
    @TableField(exist = false)
    private Integer accountNumber;

}
