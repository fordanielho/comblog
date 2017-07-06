package com.blog.common.base.util;

import com.blog.common.base.ResultModal;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * Created by lenovo on 2017/4/6.
 */
public class HttpClientJsonUtil {

    public static final int cache = 10 * 1024;
    private static final int STATUSCODE_200 = 200;
    private static final int TIME = 60*30;

    private static final int THOUSAND = 1000;

    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpclient = getHttpClientBuilder().build();
        return httpclient;
    }

    public static HttpClientBuilder getHttpClientBuilder() {

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.addInterceptorFirst(new RequestAcceptEncoding());
        httpClientBuilder.addInterceptorFirst(new ResponseContentEncoding());
        //
        // RequestConfig
        RequestConfig.Builder custom = RequestConfig.custom();
        custom = custom.setSocketTimeout(TIME * THOUSAND);
        custom = custom.setConnectTimeout(TIME * THOUSAND);
        custom = custom.setConnectionRequestTimeout(TIME * THOUSAND);
        custom = custom.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY);
        RequestConfig requestConfig = custom.build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        httpClientBuilder.setUserAgent("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)");
        CookieStore cookieStore = new BasicCookieStore();
        httpClientBuilder.setDefaultCookieStore(cookieStore);
        return httpClientBuilder;
    }

    /**
     * 模拟发送json请求
     * @param url
     * @param json
     * @return
     */
    public static ResultModal doResultModalCall(String url, String json){
        ResultModal  resultModal = new ResultModal();
        CloseableHttpClient closeableHttpClient = getHttpClient();
        try {
            //创建post请求
            HttpPost httppost = new HttpPost(url);
            //json
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httppost.setEntity(entity);
            //执行post请求
            System.out.println("HttpClientJsonUtil----doResultModalCall----doPost----------- " + url);
            System.out.println("HttpClientJsonUtil----doResultModalCall---json----------- " + json);
            try {
                //状态
                CloseableHttpResponse response = closeableHttpClient.execute(httppost);
                int code = response.getStatusLine().getStatusCode();
                if (code == HttpStatus.SC_OK) {
                    //响应实体
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity);
                        System.out.println("HttpClientJsonUtil----doResultModalCall---result----------- " + result);
                        resultModal.setReturnCode("T");
                        resultModal.setReturnMsg(result);

                    }
                    //关闭HttpEntity流
                    EntityUtils.consume(resEntity);
                }else{
                    resultModal.setReturnCode("F");
                    resultModal.setReturnMsg("{\"status\":0,\"message\":\"远程调用失败：+"+code+",无响应,请与管理员联系\"}");

                }
            }catch (Exception e){
                resultModal.setReturnCode("F");
                resultModal.setReturnMsg("{\"status\":0,\"message\":\"远程调用失败：+"+e.getMessage()+",无响应,请与管理员联系\"}");
                e.printStackTrace();
            }
        }catch (Exception e){
            resultModal.setReturnCode("F");
            resultModal.setReturnMsg("{\"status\":0,\"message\":\"远程调用失败：+"+e.getMessage()+",无响应,请与管理员联系\"}");
            e.printStackTrace();
        }
        return resultModal;
    }
}
