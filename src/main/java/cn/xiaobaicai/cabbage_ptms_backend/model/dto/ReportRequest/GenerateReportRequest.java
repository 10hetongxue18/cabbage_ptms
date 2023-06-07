package cn.xiaobaicai.cabbage_ptms_backend.model.dto.ReportRequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 实习报告生成请求
 * @author hetongxue
 */
@Data
public class GenerateReportRequest implements Serializable {

    /**
     * 实习单位基本情况
     */
    private String oneTextArea;

    /**
     * 实习岗位与实习任务
     */
    private String twoTextArea;

    /**
     * 实习体会
     */
    private String threeTextArea;

}
