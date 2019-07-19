//package com.wxss.itext;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.*;
//import com.mdiaf.api.dto.request.healthservice.QueryPersonalRecordRequest;
//import com.mdiaf.api.dto.response.healthservice.MedicalAdditionalInfo;
//import com.mdiaf.api.dto.response.healthservice.NameValueStatus;
//import com.mdiaf.api.dto.response.healthservice.PersonalRecordDto;
//import com.mdiaf.api.dto.response.healthservice.QueryPersonalRecordResponse;
//import com.mdiaf.api.service.McsPublicHealthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
///**
// * 个人基本信息模板生成PDF
// */
//@Component
//public class PDFReportPersonalBasicInformationHelper {
//
//    @Autowired
//    private McsPublicHealthService mcsPublicHealthService;
//
//    private static PDFReportPersonalBasicInformationHelper helper;
//
//
//    @PostConstruct
//    public void init(){
//        helper = this;
//        helper.mcsPublicHealthService = this.mcsPublicHealthService;
//    }
//
//    //利用模板，生成PDF
//    public void  fillPDFTemplate(String personId){
//        //模板路径
//        String templatePath = "E:/lsh_work/模板文档/表格/健康档案/个人基本信息表模板.pdf";
//        //生成新的pdf的路径
//        String newPDFPath = "E:/lsh_work/模板文档/表格/健康档案/个人基本信息表.pdf";
//        PdfReader reader;
//        FileOutputStream out;
//        ByteArrayOutputStream bos = null;
//        PdfStamper stamper;
//        try {
//            out = new FileOutputStream(newPDFPath);//输出流
//            reader = new PdfReader(templatePath);//读取pdf模板
//            bos = new ByteArrayOutputStream();
//            stamper = new PdfStamper(reader,bos);
//            AcroFields fields = stamper.getAcroFields();
//            PersonalRecordDto personalRecordDto = null;
//            QueryPersonalRecordRequest queryPersonalRecordRequest = new QueryPersonalRecordRequest();
//            queryPersonalRecordRequest.setPersonUniqueId(personId);
//            QueryPersonalRecordResponse queryPersonalRecordResponse = helper.mcsPublicHealthService.queryPersonalRecord(queryPersonalRecordRequest);
//            personalRecordDto =  queryPersonalRecordResponse.getPersonalRecordDto();
//
//            fields.setField("patientName",personalRecordDto.getName());//姓名
//            fields.setField("serialNumber",personalRecordDto.getPersonDocumentId().toString());//编号
//            fields.setField("sex",personalRecordDto.getGender());//性别
//
//            //出生日期
//            long birthdayLong = personalRecordDto.getBirthday();
//            Date date = new Date(birthdayLong);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String birthDay = sdf.format(date);
//            fields.setField("birthDay",birthDay);//出生日期
//
//            fields.setField("idNumber",personalRecordDto.getIdNumber());//身份证号
//            fields.setField("workUnit",personalRecordDto.getWorkUnits());//工作单位
//            fields.setField("phone",personalRecordDto.getPhoneNumber());//本人电话
//            fields.setField("linkManName",personalRecordDto.getContactName());//联系人姓名
//            fields.setField("linkManPhone",personalRecordDto.getContactNumber());//联系人电话
//
//            //常住类型
//            String permanentType = "户籍".equals(personalRecordDto.getResidentType())? "1" : "2";//户籍，对应数字为1；非户籍，对应数字为2
//            fields.setField("permanentType",permanentType);
//
//            //民族
//            String nation = "汉族".equals(personalRecordDto.getNationality())? "01" : "99";//汉族，对应数字为01；少数民族，对应数字为99
//            fields.setField("nation",nation);
//
//            //血型
//            String bloodType = personalRecordDto.getBloodType();
//            String bloodTypeNumber = null;//血型对应的数字
//            switch (bloodType){
//                case "A型":
//                    bloodTypeNumber = "1";
//                    break;
//                case "B型":
//                    bloodTypeNumber = "2";
//                    break;
//                case "O型":
//                    bloodTypeNumber = "3";
//                    break;
//                case "AB型":
//                    bloodTypeNumber = "4";
//                    break;
//            }
//            fields.setField("bloodType",bloodTypeNumber);//血型
//
//            //RH
//            String rh = personalRecordDto.getRH();
//            String rhNumber = null;//RH对应的数字
//            switch (rh){
//                case "阴性":
//                    rhNumber = "1";
//                    break;
//                case "阳性":
//                    rhNumber = "2";
//                    break;
//                case "不详":
//                    rhNumber = "3";
//                    break;
//            }
//            fields.setField("RH",rhNumber);//RH
//
//            fields.setField("educationalLevel",personalRecordDto.getEducationalLevel());//文化程度
//            fields.setField("career",personalRecordDto.getCareer());//职业
//
//            //婚姻状况
//            String maritalStatus = personalRecordDto.getMaritalStatus();
//            String maritalStatusNumber = null;//婚姻状况对应的数字
//            switch (maritalStatus){
//                case "未婚":
//                    maritalStatusNumber = "1";
//                    break;
//                case "已婚":
//                    maritalStatusNumber = "2";
//                    break;
//                case "丧偶":
//                    maritalStatusNumber = "3";
//                    break;
//                case "离婚":
//                    maritalStatusNumber = "4";
//                    break;
//                case "未说明的婚姻状况":
//                    maritalStatusNumber = "5";
//                    break;
//            }
//            fields.setField("maritalStatus",maritalStatusNumber);//婚姻状况
//
//            //医疗费用支付方式
//            List<String> paymentMethodList = personalRecordDto.getPaymentMethod();
//            for(int i=0; i<paymentMethodList.size(); i++){
//                String paymentMethod = "paymentMethod" + i;//这里用支付方式和数组下标的拼接来区分，可以给医疗费用支付方式下多个表单域赋值
//                String paymentMethodStr = paymentMethodList.get(i);//支付方式中文
//                String paymentMethodNumber = null;//支付方式对应的数字
//                switch (paymentMethodStr){
//                    case "城镇职工基本医疗保险\n":
//                        paymentMethodNumber = "1";
//                        break;
//                    case "城镇居民基本医疗保险\n":
//                        paymentMethodNumber = "2";
//                        break;
//                    case "新型农村合作医疗\n":
//                        paymentMethodNumber = "3";
//                        break;
//                    case "贫困救助\n":
//                        paymentMethodNumber = "4";
//                        break;
//                    case "商业医疗保险\n":
//                        paymentMethodNumber = "5";
//                        break;
//                    case "全公费\n":
//                        paymentMethodNumber = "6";
//                        break;
//                    case "全自费\n":
//                        paymentMethodNumber = "7";
//                        break;
//                    case "其他\n":
//                        paymentMethodNumber = "8";
//                        break;
//                }
//                fields.setField(paymentMethod,paymentMethodNumber);//医疗费用支付方式
//            }
//
//            //药物过敏史
//            List<String> drugAllergyHistoryList = personalRecordDto.getDrugAllergyHistory();
//            for(int i=0; i<drugAllergyHistoryList.size(); i++){
//                String drugAllergyHistory = "drugAllergyHistory" + i;//这里用药物过敏史和数组下标的拼接来区分，可以给药物过敏史下多个表单域赋值
//                String drugAllergyHistoryStr = drugAllergyHistoryList.get(i);//药物过敏史中文
//                String drugAllergyHistoryNumber = null;//药物过敏史对应的数字
//                switch (drugAllergyHistoryStr){
//                    case "无\n":
//                        drugAllergyHistoryNumber = "1";
//                        break;
//                    case "青霉素\n":
//                        drugAllergyHistoryNumber = "2";
//                        break;
//                    case "磺胺\n":
//                        drugAllergyHistoryNumber = "3";
//                        break;
//                    case "链霉素\n":
//                        drugAllergyHistoryNumber = "4";
//                        break;
//                    case "其他\n":
//                        drugAllergyHistoryNumber = "5";
//                        break;
//                }
//                fields.setField(drugAllergyHistory,drugAllergyHistoryNumber);
//            }
//
//            //暴露史
//            List<String> exposureHistoryList = personalRecordDto.getExposureHistory();
//            for(int i=0; i<exposureHistoryList.size(); i++){
//                String exposureHistory = "exposureHistory" + i;//这里用暴露史和数组下标的拼接来区分，可以给暴露史下多个表单域赋值
//                String exposureHistoryStr = exposureHistoryList.get(i);//暴露史中文
//                String exposureHistoryNumber = null;//暴露史对应的数字
//                switch (exposureHistoryStr){
//                    case "无\n":
//                        exposureHistoryNumber = "1";
//                        break;
//                    case "化学品\n":
//                        exposureHistoryNumber = "2";
//                        break;
//                    case "毒物\n":
//                        exposureHistoryNumber = "3";
//                        break;
//                    case "射线\n":
//                        exposureHistoryNumber = "4";
//                        break;
//                }
//                fields.setField(exposureHistory,exposureHistoryNumber);
//            }
//
//
//            MedicalAdditionalInfo medicalAdditionalInfo = personalRecordDto.getMedicalAdditionalInfo();
//            //既往史
//            MedicalAdditionalInfo.PastHistory pastHistory = medicalAdditionalInfo.getPastHistory();
//            //疾病（既往史）
//            List<MedicalAdditionalInfo.PastHistory.SubBean> diseaseList =  pastHistory.getDiseaseList();
//            for(int i=0; i<diseaseList.size(); i++){
//                //判断name属性是否为无？ 为无则疾病的表单域只需要无前面的checkBox传个true， 不是无，则给相应的表单域一一赋值
//                 if("无".equals(diseaseList.get(i).getName())){
//                     fields.setField("disease","true");
//                 }else{
//                     String disease = "disease" + i;//这里用疾病和数组下标的拼接来区分，可以给表单中疾病下多个小方框赋值
//                     String confirmedTimeYear = "confirmedTimeYear" + i;//这里用确诊时间（年）和数组下标的拼接来区分，可以给表单中疾病下确诊时间中的年赋值
//                     String confirmedTimeMonth="confirmedTimeMonth" + i;//这里用确诊时间（月）和数组下标的拼接来区分，可以给表单中疾病下确诊时间中的月赋值
//                     String strName = diseaseList.get(i).getName();//疾病名称
//                     String strTime = diseaseList.get(i).getTime();//疾病确诊时间（yyyy-MM-dd）
//                     String yearOfTime = strTime.substring(0,4);//这里截取确诊时间中的年份
//                     String monthOfTime = strTime.substring(5,7);//这里截取确诊时间中的月份
//                     fields.setField(confirmedTimeYear,yearOfTime);//给表单中疾病下确诊时间中的年赋值
//                     fields.setField(confirmedTimeMonth,monthOfTime);//给表单中疾病下确诊时间中的月赋值
//                     switch (strName){
//                         case "高血压":
//                             String illness2 = "2";
//                             fields.setField(disease,illness2);//将高血压对应的数字赋值给表单中的小方框
//                             break;
//                         case "糖尿病":
//                             String illness3 = "3";
//                             fields.setField(disease,illness3);//将糖尿病对应的数字赋值给表单中的小方框
//                             break;
//                         case "冠心病":
//                             String illness4 = "4";
//                             fields.setField(disease,illness4);//将冠心病对应的数字赋值给表单中的小方框
//                             break;
//                         case "慢性阻塞性肺疾病":
//                             String illness5 = "5";
//                             fields.setField(disease,illness5);//将慢性阻塞性肺疾病对应的数字赋值给表单中的小方框
//                             break;
//                         case "恶性肿瘤":
//                             String illness6 = "6";
//                             fields.setField(disease,illness6);//将恶性肿瘤对应的数字赋值给表单中的小方框
//                             break;
//                         case "脑卒中":
//                             String illness7 = "7";
//                             fields.setField(disease,illness7);//将脑卒中对应的数字赋值给表单中的小方框
//                             break;
//                         case "严重精神障碍":
//                             String illness8 = "8";
//                             fields.setField(disease,illness8);//将严重精神障碍对应的数字赋值给表单中的小方框
//                             break;
//                         case "结核病":
//                             String illness9 = "9";
//                             fields.setField(disease,illness9);//将结核病对应的数字赋值给表单中的小方框
//                             break;
//                         case "肝炎":
//                             String illness10 = "10";
//                             fields.setField(disease,illness10);//将糖尿病对应的数字赋值给表单中的小方框
//                             break;
//                         case "其他法定传染病":
//                             String illness11 = "11";
//                             fields.setField(disease,illness11);//将肝炎对应的数字赋值给表单中的小方框
//                             break;
//                         case "职业病":
//                             String illness12 = "12";
//                             fields.setField(disease,illness12);//将职业病对应的数字赋值给表单中的小方框
//                             break;
//                         default:
//                             String illness13 = "13";
//                             fields.setField(disease,illness13);//将其他对应的数字赋值给表单中的小方框
//                     }
//                 }
//            }
//
//            //手术（既往史）
//            String surgeryExist = pastHistory.getSurgeryExist();//手术有无
//            if("有".equals(surgeryExist)){
//                //有手术
//                fields.setField("operation","2");//给手术下的小方框赋值2，2为“有”的数字代号
//                List<MedicalAdditionalInfo.PastHistory.SubBean> surgeryList = pastHistory.getSurgeryList();//手术（既往史）
//                for(int i=0; i<surgeryList.size(); i++){
//                    String operationName = "operationName" + i;
//                    String operationTime = "operationTime" + i;
//                    fields.setField(operationName,surgeryList.get(i).getName());//手术名称
//                    fields.setField(operationTime,surgeryList.get(i).getTime());//手术时间
//                }
//            }else{
//                //无手术
//                fields.setField("operation","1");//给手术下的小方框赋值1，1为“无”的数字代号
//            }
//
//            //外伤（既往史）
//            String traumaExist = pastHistory.getTraumaExist();//外伤有无
//            if("有".equals(traumaExist)){
//                //有外伤
//                fields.setField("trauma","2");//给外伤下的小方框赋值2，2为“有”的数字代号
//                List<MedicalAdditionalInfo.PastHistory.SubBean> traumaList = pastHistory.getTraumaList();//外伤（既往史）
//                for(int i=0; i<traumaList.size(); i++){
//                    String traumaName = "traumaName" + i;
//                    String traumaTime = "traumaTime" + i;
//                    fields.setField(traumaName,traumaList.get(i).getName());//外伤名称
//                    fields.setField(traumaTime,traumaList.get(i).getTime());//外伤时间
//                }
//            }else{
//                //无外伤
//                fields.setField("trauma","1");//给外伤下的小方框赋值1，1为“无”的数字代号
//            }
//
//            //输血（既往史）
//            String bloodTransfusionExist = pastHistory.getBloodTransfusionExist();//输血有无
//            if("有".equals(bloodTransfusionExist)){
//                //有输血
//                fields.setField("transfusion","2");//给输血下的小方框赋值2，2为“有”的数字代号
//                List<MedicalAdditionalInfo.PastHistory.SubBean> bloodTransfusionList = pastHistory.getBloodTransfusionList();//输血（既往史）
//                for(int i=0; i<bloodTransfusionList.size(); i++){
//                    String transfusionReason = "transfusionReason" + i;
//                    String transfusionTime = "transfusionTime" + i;
//                    fields.setField(transfusionReason,bloodTransfusionList.get(i).getName());//输血原因
//                    fields.setField(transfusionTime,bloodTransfusionList.get(i).getTime());//输血时间
//                }
//            }else{
//                //无输血
//                fields.setField("transfusion","1");//给输血下的小方框赋值1，1为“无”的数字代号
//            }
//
//            //家族史
//            MedicalAdditionalInfo.FamilyHistory familyHistory= medicalAdditionalInfo.getFamilyHistory();
//            List<MedicalAdditionalInfo.FamilyHistorySub> familyHistoryList = familyHistory.getList();
//            String illness = null;//家族史中疾病前对应的数字
//            for(int i=0; i<familyHistoryList.size(); i++){
//                String diseaseName = familyHistoryList.get(i).getName();//家族史中的疾病名称
//                String familyHistoryFather = "familyHistoryFather" + i;//这里用家人关系称呼和数组下标的拼接来区分，给同一角色（父亲）下多个小方框赋值
//                String familyHistoryMother = "familyHistoryMother" + i;//这里用家人关系称呼和数组下标的拼接来区分，给同一角色（母亲）下多个小方框赋值
//                String familyHistoryBrotherSister = "familyHistoryBrotherSister" + i;//这里用家人关系称呼和数组下标的拼接来区分，给同一角色（兄弟姐妹）下多个小方框赋值
//                String familyHistoryChildren = "familyHistoryChildren" + i;//这里用家人关系称呼和数组下标的拼接来区分，给同一角色（子女）下多个小方框赋值
//                switch (diseaseName){
//                    case "无":
//                        illness = "1";
//                        break;
//                    case "高血压":
//                        illness = "2";
//                        break;
//                    case "糖尿病":
//                        illness = "3";
//                        break;
//                    case "冠心病":
//                        illness = "4";
//                        break;
//                    case "慢性阻塞性肺疾病":
//                        illness = "5";
//                        break;
//                    case "恶性肿瘤":
//                        illness = "6";
//                        break;
//                    case "脑卒中":
//                        illness = "7";
//                        break;
//                    case "严重精神障碍":
//                        illness = "8";
//                        break;
//                    case "结核病":
//                        illness = "9";
//                        break;
//                    case "肝炎":
//                        illness = "10";
//                        break;
//                    case "先天畸形":
//                        illness = "11";
//                        break;
//                    default:
//                        illness = "12";
//                }
//                List<NameValueStatus> valueList = familyHistoryList.get(i).getValue();
//                for (int j=0; j<valueList.size(); j++){
//                    String familyName = valueList.get(j).getName();//家族史中的家人称呼（父亲、母亲、兄弟姐妹、子女）
//                    if("父亲".equals(familyName)){
//                        //判断status的值，为true则父亲一栏中的小方框内填入疾病相对应的数字
//                        String status = valueList.get(j).getStatus();
//                        if("true".equals(status)){
//                            fields.setField(familyHistoryFather,illness);
//                        }
//                    }
//                    if("母亲".equals(familyName)){
//                        //判断status的值，为true则母亲一栏中的小方框内填入疾病相对应的数字
//                        String status = valueList.get(j).getStatus();
//                        if("true".equals(status)){
//                            fields.setField(familyHistoryMother,illness);
//                        }
//                    }
//                    if("兄弟姐妹".equals(familyName)){
//                        //判断status的值，为true则兄弟姐妹一栏中的小方框内填入疾病相对应的数字
//                        String status = valueList.get(j).getStatus();
//                        if("true".equals(status)){
//                            fields.setField(familyHistoryBrotherSister,illness);
//                        }
//                    }
//                    if("子女".equals(familyName)){
//                        //判断status的值，为true则子女一栏中的小方框内填入疾病相对应的数字
//                        String status = valueList.get(j).getStatus();
//                        if("true".equals(status)){
//                            fields.setField(familyHistoryChildren,illness);
//                        }
//                    }
//                }
//            }
//
//            //遗传病史
//            String geneticHistoryExist = medicalAdditionalInfo.getGeneticHistoryExist();//遗传病史有无
//            List<String> geneticHistoryList = medicalAdditionalInfo.getGeneticHistory();//遗传病史
//            if("有".equals(geneticHistoryExist)){
//                //有遗传病史
//                fields.setField("geneticHistory","2");//给遗传病史下的小方框赋值2，2为“有”的数字代号
//                for(int i=0; i<geneticHistoryList.size(); i++){
//                    fields.setField("geneticHistoryIllNessName",geneticHistoryList.get(i));//遗传疾病名称
//                }
//            }else{
//                //无遗传病史
//                fields.setField("geneticHistory","1");//给遗传病史下的小方框赋值1，1为“无”的数字代号
//            }
//
//            //残疾情况
//            List<String> disabilityList = personalRecordDto.getDisability();
//            for(int i=0; i<disabilityList.size(); i++){
//                String disability = "disability" + i;//这里用残疾情况和数组下标的拼接来区分，可以给残疾情况下多个表单域赋值
//                String disabilityStr = disabilityList.get(i);//残疾情况中文
//                String disabilityNumber = null;//残疾情况对应的数字
//                switch (disabilityStr){
//                    case "无残疾\n":
//                        disabilityNumber = "1";
//                        break;
//                    case "视力残疾\n":
//                        disabilityNumber = "2";
//                        break;
//                    case "听力残疾\n":
//                        disabilityNumber = "3";
//                        break;
//                    case "言语残疾\n":
//                        disabilityNumber = "4";
//                        break;
//                    case "肢体残疾\n":
//                        disabilityNumber = "5";
//                        break;
//                    case "智力残疾\n":
//                        disabilityNumber = "6";
//                        break;
//                    case "精神残疾\n":
//                        disabilityNumber = "7";
//                        break;
//                    case "其他残疾\n":
//                        disabilityNumber = "8";
//                        break;
//                }
//                fields.setField(disability,disabilityNumber);
//            }
//
//            //生活环境
//            MedicalAdditionalInfo.LivingEnvironment livingEnvironment = medicalAdditionalInfo.getLivingEnvironment();
//            //厨房排风设施
//            String kitchenExhaust = livingEnvironment.getKitchenExhaust();
//            String kitchenExhaustNumber = null;//厨房排风设施中对应的数字
//            switch (kitchenExhaust){
//                case "无":
//                    kitchenExhaustNumber = "1";
//                    break;
//                case "油烟机":
//                    kitchenExhaustNumber = "2";
//                    break;
//                case "换气扇":
//                    kitchenExhaustNumber = "3";
//                    break;
//                case "烟囱":
//                    kitchenExhaustNumber = "4";
//                    break;
//            }
//            fields.setField("kitchenExhaust",kitchenExhaustNumber);
//
//            //燃料类型
//            String fuelType = livingEnvironment.getFuelType();
//            String fuelTypeNumber = null;//燃料类型中对应的数字
//            switch (fuelType){
//                case "液化气":
//                    fuelTypeNumber = "1";
//                    break;
//                case "煤":
//                    fuelTypeNumber = "2";
//                    break;
//                case "天然气":
//                    fuelTypeNumber = "3";
//                    break;
//                case "沼气":
//                    fuelTypeNumber = "4";
//                    break;
//                case "柴火":
//                    fuelTypeNumber = "5";
//                    break;
//                case "其他":
//                    fuelTypeNumber = "6";
//                    break;
//            }
//            fields.setField("fuelType",fuelTypeNumber);
//
//            //饮水
//            String water = livingEnvironment.getWaterType();
//            String waterNumber = null;//饮水中对应的数字
//            switch (water){
//                case "自来水":
//                    waterNumber = "1";
//                    break;
//                case "经净化过滤的水":
//                    waterNumber = "2";
//                    break;
//                case "井水":
//                    waterNumber = "3";
//                    break;
//                case "河湖水":
//                    waterNumber = "4";
//                    break;
//                case "塘水":
//                    waterNumber = "5";
//                    break;
//                case "其他":
//                    waterNumber = "6";
//                    break;
//            }
//            fields.setField("water",waterNumber);
//
//            //厕所
//            String toilet = livingEnvironment.getToiletType();
//            String toiletNumber = null;//厕所中对应的数字
//            switch (toilet){
//                case "卫生厕所":
//                    toiletNumber = "1";
//                    break;
//                case "一格或二格粪池式":
//                    toiletNumber = "2";
//                    break;
//                case "马桶":
//                    toiletNumber = "3";
//                    break;
//                case "露天粪坑":
//                    toiletNumber = "4";
//                    break;
//                case "简易棚厕":
//                    toiletNumber = "5";
//                    break;
//            }
//            fields.setField("toilet",toiletNumber);
//
//            //禽畜栏
//            String liveStockBar = livingEnvironment.getPoultryPen();
//            String liveStockBarNumber = null;//禽畜栏中对应的数字
//            switch (liveStockBar){
//                case "无":
//                    liveStockBarNumber = "1";
//                    break;
//                case "单设":
//                    liveStockBarNumber = "2";
//                    break;
//                case "室内":
//                    liveStockBarNumber = "3";
//                    break;
//                case "室外":
//                    liveStockBarNumber = "4";
//                    break;
//            }
//            fields.setField("liveStockBar", liveStockBarNumber);
//
//            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，这里一定要设为true
//            stamper.close();
//            Document document = new Document();
//            PdfCopy pdfCopy = new PdfCopy(document,out);
//            document.open();
//            PdfImportedPage pdfImportedPage = pdfCopy.getImportedPage(new PdfReader(bos.toByteArray()),1);
//            pdfCopy.addPage(pdfImportedPage);
//            document.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }catch (DocumentException e){
//            e.printStackTrace();
//        } finally {
//            if (bos != null) {
//                try {
//                    bos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//    }
//
//
//
//
//
//
//}
