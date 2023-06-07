package cn.xiaobaicai.cabbage_ptms_backend.model.vo;


import lombok.Data;

import java.io.Serializable;


/**
 * 实习新闻详情请求
 * @author hetongxue
 */
@Data
public class NewsDetailInfoVo implements Serializable {

    /**
     *  企业老师id
     */
    private Integer entTeaId;

    /**
     *  新闻id
     */
    private Long newsId;

    /**
     *  实习基地id
     */
    private Integer areaId;

    /**
     *  企业老师姓名
     */
    private String entTeaName;

    /**
     * 企业名称
     */
    private String entName ;

    /**
     * 岗位名称
     */
    private String postName ;

    /**
     * 详细信息
     */
    private String message ;

    /**
     * 实习基地图片
     */
    private String picture ;

    /**
     * 实习地址
     */
    private String address ;

    /**
     * 联系电话
     */
    private String phone ;

    /**
     * 联系邮箱
     */
    private String email ;


}
