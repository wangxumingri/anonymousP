package com.wxss.shop.dto;

import java.util.Arrays;

/**
 * Author:Created by wx on 2019/7/31
 * Desc:
 */
public class ReportResultDto {
    private String reportDoctor; // 报告医生
    private Long facid; // 医院编号
    private String[] lable; // 阴阳性
    private String finding; // 检查所见
    private String result; // 诊断结果
    private String createReportTime; // 报告时间

    public String getReportDoctor() {
        return reportDoctor;
    }

    public void setReportDoctor(String reportDoctor) {
        this.reportDoctor = reportDoctor;
    }

    public Long getFacid() {
        return facid;
    }

    public void setFacid(Long facid) {
        this.facid = facid;
    }

    public String[] getLable() {
        return lable;
    }

    public void setLable(String[] lable) {
        this.lable = lable;
    }

    public String getFinding() {
        return finding;
    }

    public void setFinding(String finding) {
        this.finding = finding;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateReportTime() {
        return createReportTime;
    }

    public void setCreateReportTime(String createReportTime) {
        this.createReportTime = createReportTime;
    }

    @Override
    public String toString() {
        return "UltrasoundReportResultDto{" +
                "reportDoctor='" + reportDoctor + '\'' +
                ", facid=" + facid +
                ", lable=" + Arrays.toString(lable) +
                ", finding='" + finding + '\'' +
                ", result='" + result + '\'' +
                ", createReportTime='" + createReportTime + '\'' +
                '}';
    }
}
