package com.wxss.shop.controller;

import com.alibaba.fastjson.JSON;
import com.wxss.shop.pojo.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Author:Created by wx on 2019/7/29
 * Desc:
 */
@Controller
public class HttpClientController {

    @RequestMapping("/get")
    @ResponseBody
    public String testGet() {
        return "中文123";
    }

    @RequestMapping("/get2")

    public String testPost() {
        return "中文123";
    }


    @RequestMapping("/getWithParams1")
    @ResponseBody
    public String testGetWithParams1(HttpServletRequest request) {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        return "通过HttpServletRequest获取请求参数:[" + name + "],[" + age + "]";
    }


    @RequestMapping("/getWithParams2")
    @ResponseBody

    public String testGetWithParams2(String name, String age, HttpServletRequest request) {
        String name1 = request.getParameter("name");
        System.out.println(name1);
        return "直接通过参数映射获取请求参数:[" + name + "],[" + age + "]";
    }

    /**
     * 测试是否忽略大小写:不忽略
     * name：可以传入
     * Name：不可以传入
     *
     * @param
     * @param age
     * @param request
     * @return
     */
    @RequestMapping("/getWithParams3")
    @ResponseBody

    public String testGetWithParams3(String name, String age, HttpServletRequest request) {
        String name1 = request.getParameter("name"); // 可以接收
        System.out.println(name1);
        System.out.println("参数" + name); // 也可以接收
        return "直接通过参数映射获取请求参数:[" + name + "],[" + age + "]";
    }

    //
    @RequestMapping("/getWithParams4")
    @ResponseBody

    public String testGetWithParams4(String peoplename, String age, HttpServletRequest request) {
        String name1 = request.getParameter("name");
        System.out.println(name1);
        return "直接通过参数映射获取请求参数:[" + peoplename + "],[" + age + "]";
    }

    @GetMapping("/getWithParams5")
    @ResponseBody

    public String testGetWithParams5(String name, String age) {
        return "直接通过参数映射获取请求参数:[" + name + "],[" + age + "]";
    }

    @RequestMapping("/post1")
    @ResponseBody
    public String testPost1(@RequestBody User user) {
        return user.toString();
    }

    @RequestMapping("/testPostWithField")
    @ResponseBody
    public String testPostWithField(String data, String Data, HttpServletRequest request) {
        System.out.println("testPostWithField start");
        System.out.println(data);
        User user = JSON.parseObject(data, User.class);
        System.out.println("反序列化:" + user);
        System.out.println(Data);

        String byRequest = request.getParameter("data");
        System.out.println("byRequest：" + byRequest);
        System.out.println("testPostWithField end");

        return byRequest;
    }

    @RequestMapping("/testStringEntityOfReturnPojo")
    @ResponseBody
    public User testStringEntityOfReturnPojo(@RequestBody User user, HttpServletRequest request) {
        System.out.println("testPostWithoutField start");
        System.out.println("" + user);
        // 通过request是否可以获取，序列化表单数据
        String name = request.getParameter("name");
        System.out.println("name：" + name);
        System.out.println("testPostWithoutField end");
        return user;
    }

    @RequestMapping("/testStringEntityOfReturnString")
    @ResponseBody
    public String testStringEntityOfReturnString(@RequestBody User user, HttpServletRequest request) {
        System.out.println("byParamer:" + user);
        String data = request.getParameter("Data");
        System.out.println("byRequest:" + data);

        return user.getUsername();
    }


    @RequestMapping("/testPostByStringEntity1")
    @ResponseBody
    public String testPostByStringEntity1(@RequestBody String data, HttpServletRequest request) {
        System.out.println("参数:" + data);

        System.out.println("request:");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter("name");
            System.out.println(name + "---" + value);
        }
        return data;
    }

    @RequestMapping("/testPostByStringEntity2")
    @ResponseBody
    public String testPostByStringEntity2(@RequestBody String data, HttpServletRequest request) {
        System.out.println("参数:" + data);

        System.out.println("request:");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter("name");
            System.out.println(name + "---" + value);
        }
        return data;
    }


    @RequestMapping("/testUrlEncodedFormEntity1")
    @ResponseBody
    public String testPostFile1(String name, String time, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("请求头:");
        while (headerNames.hasMoreElements()) {
            String headerKey = headerNames.nextElement();
            String headerValue = request.getHeader(headerKey);
            System.out.println(headerKey + ":" + headerValue);
        }
        System.out.println("请求参数:");
        System.out.println(name);
        System.out.println(time);
        return name;
    }

    @RequestMapping("/file2")
    @ResponseBody

    public String testPostFile2(HttpServletRequest request) {
        String name = request.getParameter("name");
        String file = request.getParameter("file");

        System.out.println(name);
        System.out.println(file);
        return "成功上传";
    }

    /**
     * 请求参数共三个，全部在请求体中
     * 两个普通参数：name,age
     * 一个文件上传：file
     *
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/file3")
    @ResponseBody
    protected User doPost(HttpServletRequest request) throws ServletException, IOException {
        User user = null;
        try {
            String name = request.getParameter("name");
            String age = request.getParameter("age");
            String file = request.getParameter("file"); //null---> HttpServletRequest.getParameter(String name)无法获取 上传文件
            String jsonBody = request.getParameter("jsonBody");
            String jsonPart = request.getParameter("jsonPart");

            user = JSON.parseObject(jsonBody, User.class);
            System.out.println("request 获取 start ");
            System.out.println(name);
            System.out.println(age);
            System.out.println(file);
            System.out.println(jsonBody);
            System.out.println(jsonPart);
            System.out.println("request 获取 end ");

            if (request instanceof StandardMultipartHttpServletRequest) {
                StandardMultipartHttpServletRequest standardMultipartHttpServletRequest = (StandardMultipartHttpServletRequest) request;
                Enumeration<String> parameterNames = standardMultipartHttpServletRequest.getParameterNames();// 获取的也是普通请求参数：name和age
                while (parameterNames.hasMoreElements()) {
                    System.out.println(parameterNames.nextElement());
                }


                Map<String, MultipartFile> fileMap = standardMultipartHttpServletRequest.getFileMap();// 可以获取文件： key为文件的filename,value是MultipartFile
                MultipartFile name1 = fileMap.get("file");
                String name2 = name1.getName();// key的名字：file
                String originalFilename = name1.getOriginalFilename();// 文件的原始名字:Snipaste_2019-07-30_08-19-25.png
                System.out.println("originalFilename" + originalFilename);

                name1.transferTo(new File("E:/" + originalFilename));// 通过接收到的文件，生成一个新文件
                String file1 = standardMultipartHttpServletRequest.getMultipartContentType("file");// file字段的contentType:....

                String file2 = standardMultipartHttpServletRequest.getMultipartContentType("name");// name字段的contentType：text/plain

            }

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            if (ServletFileUpload.isMultipartContent(request)) {
                System.out.println("isMultipartContent");
            }
            // 无法解析
            // the request doesn't contain a multipart/form-data or multipart/mixed stream, content type header is application/json; charset=UTF-8
            List<org.apache.commons.fileupload.FileItem> fileItems = fileUpload.parseRequest(request);
            for (FileItem item : fileItems) {
                String filename = item.getName();
                System.out.println("服务端:" + filename);
                File newFile = new File("F:/eclipseOutput/PIC/" + filename);
                item.write(newFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    @RequestMapping("/testInputStream")
    @ResponseBody
    public String testInputStream(HttpServletRequest request) {
        String a = null;
        try {
            // Caused by: org.apache.tomcat.util.http.fileupload.FileUploadBase$InvalidContentTypeException: the request doesn't contain a multipart/form-data or multipart/mixed stream, content type header is null
            Collection<Part> parts = request.getParts();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String s = parameterNames.nextElement();
                String parameter = request.getParameter(s);
                System.out.println(s + "----" + parameter);
            }
            ServletInputStream inputStream = request.getInputStream();
            byte[] bytes = readInputStream(inputStream);
             a = new String(bytes, "UTF-8");

            System.out.println("解析后的请求:"+a);

//            while (inputStream.readLine())
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return a;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
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
