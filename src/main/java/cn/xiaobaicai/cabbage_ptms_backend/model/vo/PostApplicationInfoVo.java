package cn.xiaobaicai.cabbage_ptms_backend.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询实习岗位申请情况
 * @author hetongxue
 */
@Data
public class PostApplicationInfoVo implements Serializable {

    /**
     * 实习申请表id
     */
    private Integer id;

    /**
     * 申请人
     */
    private String name;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 院系
     */
    private String faculty;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级
     */
    private String classes;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 详细信息
     */
    private String message;

    /**
     * 基地风光
     */
    private String picture;

    /**
     * 实习地址
     */
    private String address;

    /**
     * 自我简介
     */
    private String introduction;

    /**
     * 申请状态
     */
    private Byte state;

}
