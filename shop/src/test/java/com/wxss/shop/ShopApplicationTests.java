package com.wxss.shop;

import com.alibaba.fastjson.JSON;
import com.wxss.shop.mapper.GoodsMapper;
import com.wxss.shop.pojo.Goods;
import com.wxss.shop.pojo.User;
import com.wxss.shop.service.GoodsServiceI;
import com.wxss.shop.utils.MyHttpClientUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopApplicationTests {

    @Autowired
    GoodsServiceI goodsService;

    @Test
    public void testJson(){
        Map<String,Object> map = new HashMap<>();

        User user = new User();
        user.setUsername("测试");
        user.setId(1);
        user.setCreateTime(new Date());

        Goods goods = new Goods();
        goods.setGoodsName("雪碧");
        user.setGoods(goods);

        map.put("errCode", 111);
        map.put("errMsg","失败");
        map.put("data",user );

        String mapJson = JSON.toJSONString(map);

        System.out.println(mapJson);
        String pojoJson = JSON.toJSONString(user);

        System.out.println(pojoJson);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user);
        String listjson = JSON.toJSONString(userList);
        System.out.println(listjson);


        User user1 = JSON.parseObject("", User.class);
        System.out.println(user1);

    }
    @Test
    public void testInputStream() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response = null;
        try {
            User user = new User();
            user.setId(1);
            user.setUsername("德玛");
            user.setCreateTime(new Date());
            byte[] bytes = JSON.toJSONBytes(user);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            HttpEntity entity = new InputStreamEntity(inputStream);
            String url = "http://localhost:9999/testInputStream";

            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json; charset=UTF-8"); //
            post.setEntity(entity);
            response = httpClient.execute(post);
            if (response != null){
                HttpEntity entity1 = response.getEntity();
                String result = EntityUtils.toString(entity1, "UTF-8");
                System.out.println(result);
            }else {
                System.out.println("响应报文为null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            response.close();
            httpClient.close();
        }
    }

    @Test
    public void testTime(){
        Date date = new Date();
        long l = System.currentTimeMillis();
        System.out.println(l);
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            Integer integer = Integer.valueOf(timestamp.substring(0, length - 3));
            System.out.println(integer);
            String s = String.valueOf(integer);
            System.out.println(s);
        }

        String s = String.valueOf(new Date().getTime());
        System.out.println(s);
    }
    @Test
    public void testStringEntity(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:9999/testPostByStringEntity2";

        User user = new User();
        user.setId(1);
        user.setUsername("德玛");
        user.setCreateTime(new Date());

        String jsonString = JSON.toJSONString(user);
        StringEntity entity = new StringEntity(jsonString, "UTF-8"); // content-type:text/plain;charset=UTF-8
        entity.setContentType("application/json");
//        List<NameValuePair> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("name","德玛"));
//        nameValuePairs.add(new BasicNameValuePair("time",String.valueOf(new Date().getTime())));
//
//        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs,Charset.forName("UTF-8"));
//        urlEncodedFormEntity.setContentType("application/json; charset=UTF-8");
//        post.setEntity(urlEncodedFormEntity);

        HttpPost post = new HttpPost(url);
        post.setEntity(entity);
        post.setHeader("Content-Type", "application/json; charset=UTF-8"); //
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null){
            Header[] responseHeaders = response.getAllHeaders();
            System.out.println("响应头:");
            for (Header responseHeader : responseHeaders) {
                System.out.println(responseHeader.getName()+":"+responseHeader.getValue());

            }
            System.out.println("响应体:");
            HttpEntity responseEntity = response.getEntity();
            try {
                String result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
                System.out.println(result);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("响应报文为null");
        }

    }

    @Test
    public void testUrlEncodedFormEntity(){
        String url = "http://localhost:9999/testUrlEncodedFormEntity1";
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("name","德玛"));
        nameValuePairs.add(new BasicNameValuePair("time",String.valueOf(new Date().getTime())));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs,Charset.forName("UTF-8"));
        urlEncodedFormEntity.setContentType("application/json; charset=UTF-8");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setEntity(urlEncodedFormEntity);
//        post.setHeader("Content-Type", "application/json; charset=UTF-8"); // 加上就接收不到
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null){
            Header[] responseHeaders = response.getAllHeaders();
            System.out.println("响应头:");
            for (Header responseHeader : responseHeaders) {
                System.out.println(responseHeader.getName()+":"+responseHeader.getValue());

            }
            System.out.println("响应体:");
            HttpEntity responseEntity = response.getEntity();
            try {
                String result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
                System.out.println(result);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("响应报文为null");
        }

    }


    @Test
    public void contextLoads() {
        goodsService.updateCount();
    }
    @Test
    public void testPostWithField() throws UnsupportedEncodingException {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
        HttpPost httpPost = new HttpPost("http://localhost:9999/testPostWithField");
        User user = new User();
        user.setId(1);
        user.setUsername("德玛");
        user.setCreateTime(new Date());
        // (需要导入com.alibaba.fastjson.JSON包)
        String jsonString = JSON.toJSONString(user);

        System.out.println("json串"+jsonString);
        List<NameValuePair> formParms = new ArrayList<>();
        formParms.add(new BasicNameValuePair("data", jsonString));
        formParms.add(new BasicNameValuePair("Data", "测试数据Data"));
//        httpPost.setHeader("Content-Type", "application/json"); // 加上就接收不到

//        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formParms);
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formParms,Charset.forName("UTF-8"));
//        urlEncodedFormEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8"); // 乱码
//        urlEncodedFormEntity.setContentEncoding("ISO-8859-1");// 无效
//        urlEncodedFormEntity.setContentEncoding("utf-8");// 无效
        httpPost.setEntity(urlEncodedFormEntity);

        Header[] allHeaders1 = httpPost.getAllHeaders();
        for (Header header : allHeaders1) {
            System.out.println(header.getName()+"-----"+header.getValue());
        }
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            Header[] allHeaders = response.getAllHeaders();
            for (Header allHeader : allHeaders) {

                System.out.println(allHeader.getName());
                System.out.println(allHeader.getValue());
            }
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应行为:" + response.getStatusLine());
            System.out.println("响应类型:" + responseEntity.getContentType());
            System.out.println("响应编码:" + responseEntity.getContentEncoding());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testPostWithoutField() throws IOException {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Post请求
//        HttpPost httpPost = new HttpPost("http://localhost:9999/testPostWithoutField");
        HttpPost httpPost = new HttpPost("http://localhost:9999/testStringEntityOfReturnPojo");
        User user = new User();
        user.setId(1);
        user.setUsername("德玛1");
        user.setCreateTime(new Date());
        // (需要导入com.alibaba.fastjson.JSON包)
        String jsonString = JSON.toJSONString(user);
        StringEntity entity = new StringEntity(jsonString, "UTF-8"); // content-type:text/plain;charset=UTF-8
//        StringEntity entity = new StringEntity(jsonString);// 默认使用：ISO-8859-1,中文乱码
//        entity.setContentType("application/json");
        entity.setContentType("application/json; charset=UTF-8"); // 此处的UTF-8无法解决中文乱码，application/json有效
        entity.setContentEncoding("UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
//        List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(entity);
//        List<NameValuePair> nameValuePairs = new ArrayList<>();
//
//        nameValuePairs.add(new BasicNameValuePair("Data", jsonString));
//        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
//        urlEncodedFormEntity.setContentType("application/json");
        httpPost.setEntity(entity);

//        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
//        httpPost.setHeader("Content-Type", "application/json");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            System.out.println("响应类型:" + responseEntity.getContentType());
            System.out.println("响应编码:" + responseEntity.getContentEncoding());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testGetWithParams(){
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 参数
        StringBuffer params = new StringBuffer();
        try {
            // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
            params.append("name=" + URLEncoder.encode("&", "utf-8"));
            params.append("&");
            params.append("age=24");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String url = "http://localhost:9999/getWithParams4" + "?" + params;
        System.out.println(url);

        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
//            httpGet.setConfig(requestConfig);

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            System.out.println("响应类型:" + responseEntity.getContentType());
            System.out.println("响应编码:" + responseEntity.getContentEncoding());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}



    @Test
    public void testGet(){
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://localhost:9999/get2");

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            System.out.println("响应类型:" + responseEntity.getContentType());
            System.out.println("响应编码:" + responseEntity.getContentEncoding());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void  postFile()  throws ClientProtocolException, IOException {
        String serverUrl = "http://127.0.0.1:9999/file3";
        File file = new File("D:/Snipaste_2019-07-30_08-19-25.png");
        HttpPost httpPost = new HttpPost(serverUrl);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("UTF-8"));
//        普通文本
//        builder.addPart("age", new StringBody("测试", ContentType.TEXT_PLAIN));// 不设置UTF-8，会中文乱码
        builder.addPart("age", new StringBody("测试",ContentType.create("text/plain", Consts.UTF_8))); // 无中文乱码
        builder.addTextBody("name", "你的名字", ContentType.create("text/plain", Consts.UTF_8));// 无中文乱码
        // json串
        User user = new User();
        user.setId(1);
        user.setUsername("德玛1");
        user.setCreateTime(new Date());
        String jsonString = JSON.toJSONString(user);
//        StringEntity entity = new StringEntity(jsonString, "UTF-8"); // content-type:text/plain;charset=UTF-8
        builder.addTextBody("jsonBody", jsonString, ContentType.create("application/json", Consts.UTF_8));
        builder.addPart("jsonPart", new StringBody(jsonString, ContentType.create("text/plain", Consts.UTF_8)));

        // 上传的文件
        builder.addBinaryBody("file", file);
        HttpEntity httpEntity = builder.build();
        httpPost.setEntity(httpEntity);
//        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        // 请求获取数据的超时时间 、 设置从connect
        // Manager获取Connection超时时间（因为目前版本是可以共享连接池的）、设置连接超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000).setConnectionRequestTimeout(3000)
                .setConnectTimeout(10000).build();
        httpPost.setConfig(requestConfig);
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String resJsonStr = EntityUtils.toString(responseEntity);
        User user1 = JSON.parseObject(resJsonStr, User.class);

        System.out.println(user1);

        System.out.println(resJsonStr);
        System.out.println("*************************************");
        System.out.println(responseEntity.getContentType());
        System.out.println(responseEntity.getContentEncoding());

    }

}
