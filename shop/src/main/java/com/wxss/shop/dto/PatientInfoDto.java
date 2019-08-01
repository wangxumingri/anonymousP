package com.wxss.shop.dto;

/**
 * 患者基本信息
 */
public class PatientInfoDto {
    private String patName; // 患者姓名（必填）
    private String patienClassNo;// 病人类型(住院/门诊/体检)编号 （必填）
    private String patientClass;// 病人类型指定值(E:体检,I:住院,0:门诊)（必填）
    private String orderdoctor;// 送检医生（必填）
    private String orderdepartment;// 送检科室（必填）
    private String patientId;// 病人id（必填）
    private String hisId;// 申请单标识（必填）

    private String patBirthday;// 患者生日
    private String patSexId;// 性别 M/F/O
    private String patAge;//患者年龄
    private String patNation;// 民族(附件2)
    private String patPhoneNumber;// 患者手机号
    private String patIdCard;// 患者身份证

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getPatienClassNo() {
        return patienClassNo;
    }

    public void setPatienClassNo(String patienClassNo) {
        this.patienClassNo = patienClassNo;
    }

    public String getPatientClass() {
        return patientClass;
    }

    public void setPatientClass(String patientClass) {
        this.patientClass = patientClass;
    }

    public String getOrderdoctor() {
        return orderdoctor;
    }

    public void setOrderdoctor(String orderdoctor) {
        this.orderdoctor = orderdoctor;
    }

    public String getOrderdepartment() {
        return orderdepartment;
    }

    public void setOrderdepartment(String orderdepartment) {
        this.orderdepartment = orderdepartment;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    public String getPatBirthday() {
        return patBirthday;
    }

    public void setPatBirthday(String patBirthday) {
        this.patBirthday = patBirthday;
    }

    public String getPatSexId() {
        return patSexId;
    }

    public void setPatSexId(String patSexId) {
        this.patSexId = patSexId;
    }

    public String getPatAge() {
        return patAge;
    }

    public void setPatAge(String patAge) {
        this.patAge = patAge;
    }

    public String getPatNation() {
        return patNation;
    }

    public void setPatNation(String patNation) {
        this.patNation = patNation;
    }

    public String getPatPhoneNumber() {
        return patPhoneNumber;
    }

    public void setPatPhoneNumber(String patPhoneNumber) {
        this.patPhoneNumber = patPhoneNumber;
    }

    public String getPatIdCard() {
        return patIdCard;
    }

    public void setPatIdCard(String patIdCard) {
        this.patIdCard = patIdCard;
    }

    @Override
    public String toString() {
        return "PatientInfoDto{" +
                "patName='" + patName + '\'' +
                ", patienClassNo='" + patienClassNo + '\'' +
                ", patientClass='" + patientClass + '\'' +
                ", orderdoctor='" + orderdoctor + '\'' +
                ", orderdepartment='" + orderdepartment + '\'' +
                ", patientId='" + patientId + '\'' +
                ", hisId='" + hisId + '\'' +
                ", patBirthday='" + patBirthday + '\'' +
                ", patSexId='" + patSexId + '\'' +
                ", patAge='" + patAge + '\'' +
                ", patNation='" + patNation + '\'' +
                ", patPhoneNumber='" + patPhoneNumber + '\'' +
                ", patIdCard='" + patIdCard + '\'' +
                '}';
    }
}
