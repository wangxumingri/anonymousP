package com.wxss.shop.dto;

public class ExamineInfoDto {
    private String examineId;// 检查项目id
    private String modility;// 设备类型(附件1)(必填)
    private String examineItem;// 检查项目
    private String bodypart;// 部位(必填)
    private String description;// 检查描述
    private String startTime;// 检查时间
    private String imageno;//  影像标识(必填)

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public String getModility() {
        return modility;
    }

    public void setModility(String modility) {
        this.modility = modility;
    }

    public String getExamineItem() {
        return examineItem;
    }

    public void setExamineItem(String examineItem) {
        this.examineItem = examineItem;
    }

    public String getBodypart() {
        return bodypart;
    }

    public void setBodypart(String bodypart) {
        this.bodypart = bodypart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getImageno() {
        return imageno;
    }

    public void setImageno(String imageno) {
        this.imageno = imageno;
    }

    @Override
    public String toString() {
        return "ExamineInfoDto{" +
                "examineId='" + examineId + '\'' +
                ", modility='" + modility + '\'' +
                ", examineItem='" + examineItem + '\'' +
                ", bodypart='" + bodypart + '\'' +
                ", description='" + description + '\'' +
                ", startTime='" + startTime + '\'' +
                ", imageno='" + imageno + '\'' +
                '}';
    }
}
