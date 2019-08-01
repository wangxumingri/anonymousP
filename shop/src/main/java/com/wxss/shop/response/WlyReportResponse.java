package com.wxss.shop.response;

import com.wxss.shop.dto.ReportResultDto;

/**
 * Author:Created by wx on 2019/7/31
 * Desc:
 */
public class WlyReportResponse {
    private Integer errCode;
    private String errMsg;

    private ReportResultDto data;

    public ReportResultDto getData() {
        return data;
    }

    public void setData(ReportResultDto data) {
        this.data = data;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
