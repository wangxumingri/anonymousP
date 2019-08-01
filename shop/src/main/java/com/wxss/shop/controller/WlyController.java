package com.wxss.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxss.shop.dto.ExamineInfoDto;
import com.wxss.shop.dto.PatientInfoDto;
import com.wxss.shop.enums.WlyResponseCodeEnum;
import com.wxss.shop.response.WlyUploadImageResponse;
import com.wxss.shop.response.WlyUploadPatientInfoResponse;
import org.apache.http.entity.ContentType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

/**
 * Author:Created by wx on 2019/7/31
 * Desc:
 */
@RestController
public class WlyController {

    public static void readInputStream(InputStream inStream) throws Exception {

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("D:/test/aa.mp4"));
            byte[] buffer = new byte[1024];

            int len;
            while ((len = inStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        fileOutputStream.close();
        inStream.close();
    }

    @PostMapping("/uploadPatientInfo2")
    public WlyUploadPatientInfoResponse uploadPatientInfo2(HttpServletRequest request) {
        WlyUploadPatientInfoResponse response = null;
        try {
            System.out.println(request.getClass());
//            System.out.println("接收到病人信息:" + examineInfo.toString());
//            System.out.println("接收到检查信息:" + patientInfoDto.toString());


            // 获取请求头
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                System.out.println("请求头:[" + headerName + "],[" + headerValue + "]");
            }

            // 获取参数
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String parameterValue = request.getParameter(paramName);
                System.out.println("参数:[" + paramName + "],[" + parameterValue + "]");
            }



            String data = request.getParameter("Data");
            System.out.println("request.getParameter：["+data+"]");

            Map<String,Object> map = JSON.parseObject(data, Map.class);

            JSON examineInfoDto = (JSON) map.get("examineInfoDto");
            System.out.println(examineInfoDto);

            response = new WlyUploadPatientInfoResponse();
            response.setErrCode(WlyResponseCodeEnum.SUCCESS.getCode());
            response.setErrMsg(WlyResponseCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            response = new WlyUploadPatientInfoResponse();
            response.setErrCode(WlyResponseCodeEnum.POST_DATA_ERRPR.getCode());
            response.setErrMsg(WlyResponseCodeEnum.POST_DATA_ERRPR.getMessage());
        }

        return response;

    }


    @PostMapping("/uploadPatientInfo")
    public WlyUploadPatientInfoResponse uploadPatientInfo(@RequestBody ExamineInfoDto examineInfo, @RequestBody PatientInfoDto patientInfoDto, HttpServletRequest request) {
        WlyUploadPatientInfoResponse response = null;
        try {
            System.out.println(request.getClass());
            System.out.println("接收到病人信息:" + examineInfo.toString());
            System.out.println("接收到检查信息:" + patientInfoDto.toString());


            // 获取请求头
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                System.out.println("请求头:[" + headerName + "],[" + headerValue + "]");
            }

            // 获取参数
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String parameterValue = request.getParameter(paramName);
                System.out.println("参数:[" + paramName + "],[" + parameterValue + "]");
            }



            String data = request.getParameter("Data");
            System.out.println(data);

            response = new WlyUploadPatientInfoResponse();
            response.setErrCode(WlyResponseCodeEnum.SUCCESS.getCode());
            response.setErrMsg(WlyResponseCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            response = new WlyUploadPatientInfoResponse();
            response.setErrCode(WlyResponseCodeEnum.POST_DATA_ERRPR.getCode());
            response.setErrMsg(WlyResponseCodeEnum.POST_DATA_ERRPR.getMessage());
        }

        return response;

    }

    /**
     * 模拟表单上传文件时，如果设置请求头是 json，无法获取到文件
     * 不设置：会将RequestFacade 转成standardMultipartHttpServletRequest，可以获取
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/uploadImage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public WlyUploadImageResponse uploadImage(HttpServletRequest request) {
        WlyUploadImageResponse wlyResponse = new WlyUploadImageResponse();
        try {
            if (request instanceof StandardMultipartHttpServletRequest) {
                System.out.println("已转换");
                StandardMultipartHttpServletRequest standardMultipartHttpServletRequest = (StandardMultipartHttpServletRequest) request;
                // 获取请求头
                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()){
                    String headerName = headerNames.nextElement();
                    String headerValue = request.getHeader(headerName);
                    System.out.println("请求头:[" + headerName + "],[" + headerValue + "]");
                }

                // 获取参数
                Enumeration<String> parameterNames = request.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String paramName = parameterNames.nextElement();
                    String parameterValue = request.getParameter(paramName);
                    System.out.println("参数:[" + paramName + "],[" + parameterValue + "]");
                }

                // 获取文件
                Map<String, MultipartFile> fileMap = standardMultipartHttpServletRequest.getFileMap();// 可以获取文件： key为文件的filename,value是MultipartFile
                MultipartFile mu = fileMap.get("file");
                String name = mu.getName();// key的名字：file
                String originalFilename = mu.getOriginalFilename();// 文件的原始名字:Snipaste_2019-07-30_08-19-25.png
                System.out.println("name：" + name + "；originalFilename" + originalFilename);
                mu.transferTo(new File("D:/test/" + originalFilename));

            } else {
                System.out.println("未转化");
                // 获取请求头
                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()){
                    String headerName = headerNames.nextElement();
                    String headerValue = request.getHeader(headerName);
                    System.out.println("请求头:[" + headerName + "],[" + headerValue + "]");
                }

                // 获取参数
                Enumeration<String> parameterNames = request.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String paramName = parameterNames.nextElement();
                    String parameterValue = request.getParameter(paramName);
                    System.out.println("参数:[" + paramName + "],[" + parameterValue + "]");
                }

                InputStream requestInputStream = request.getInputStream();
                byte[] bytes = readInputStream2(requestInputStream);

                FileOutputStream outputStream = new FileOutputStream(new File("D:/test/aaaaaa.mp4"));
                outputStream.write(bytes);

                outputStream.flush();

                outputStream.close();
                requestInputStream.close();


                // 获取文件
                if (ContentType.MULTIPART_FORM_DATA.getMimeType().equalsIgnoreCase(request.getHeader("Content-Type"))) {
                    Part file = request.getPart("file");
                    String contentType = file.getContentType();
                    System.out.println("文件的ct:" + contentType);
                    System.out.println("name:" + file.getName());
                    String submittedFileName = file.getSubmittedFileName();
                    System.out.println("subName" + submittedFileName);

                    // 获取上传文件的请求头
                    Collection<String> fileHeaderNames = file.getHeaderNames();
                    for (String headerName : fileHeaderNames) {
                        String headerValue = file.getHeader(headerName);
                        System.out.println("文件的请求头" + headerName + "____" + headerValue);
                    }
                    file.write("D:/test");
                    InputStream inputStream = file.getInputStream();
                    readInputStream(inputStream);
                } else {
                    System.out.println("未转化无法获取文件");
                }
            }
            wlyResponse.setErrCode(0);// 成功
        } catch (Exception e) {
            e.printStackTrace();
            wlyResponse.setErrCode(104);// 系统繁忙
            wlyResponse.setErrMsg("系统繁忙");
        }

        return wlyResponse;
    }

    public static byte[] readInputStream2(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }

        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }
}
