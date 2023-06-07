package cn.xiaobaicai.cabbage_ptms_backend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求
 *
 * @author yupi
 */
@Data
public class PageRequest implements Serializable {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 12;

}
