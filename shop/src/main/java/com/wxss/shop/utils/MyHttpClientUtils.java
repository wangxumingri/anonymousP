package com.wxss.shop.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Author:Created by wx on 2019/7/31
 * Desc:
 */
public class MyHttpClientUtils {
      private static CloseableHttpClient client = HttpClients.createDefault();

      public static HttpResponse post(String url, StringEntity requestEntity,String contentType){
          HttpPost httpPost = new HttpPost(url);
          httpPost.setEntity(requestEntity);
          if (contentType!=null){
            httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
          }
          HttpEntity responseEntity = null;
          CloseableHttpResponse closeableHttpResponse = null;
          try {
              closeableHttpResponse = client.execute(httpPost);
//              responseEntity = closeableHttpResponse.getEntity();
          } catch (IOException e) {
              e.printStackTrace();
          }finally {
              try {
                  assert closeableHttpResponse != null;
                  closeableHttpResponse.close();
                  client.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
          return closeableHttpResponse;
      }
}
