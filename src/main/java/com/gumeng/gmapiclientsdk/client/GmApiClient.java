package com.gumeng.gmapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.gumeng.gmapiclientsdk.model.Api;
import com.gumeng.gmapiclientsdk.utils.SignUtils;


import java.util.HashMap;
import java.util.Map;

import static com.gumeng.gmapiclientsdk.utils.Constant.GATEWAY_HOST;
import static com.gumeng.gmapiclientsdk.utils.Constant.GET_BODY;
import static com.gumeng.gmapiclientsdk.utils.SignUtils.getSign;

/**
 * 调用第三方接口的客户端
 * @author 顾梦
 * @create 2023/4/26
 */
public class GmApiClient {

    private String accessKey;

    private String secretKey;

    public GmApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getResult(Api api) {
        String json = JSONUtil.toJsonStr(api.getBody());
        if ("get".equals(api.getMethod()) || "GET".equals(api.getMethod())) {
            return HttpUtil.createGet(GATEWAY_HOST+
                            api.getUrl())
                    .addHeaders(getHeaderByGet()).execute().body();

        } else {
            return HttpRequest.post(GATEWAY_HOST + api.getUrl())
                    .header("Accept","application/json;charset=UTF-8")
                    .addHeaders(getheaderMap(json))
                    .charset("UTF-8")
                    .body(json)
                    .execute().body();
        }
    }

    /**
     * 生成post请求头信息集合
     *
     * @param body
     * @return
     */
    private Map<String,String> getheaderMap(String body){
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body",body);
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign",getSign(body,secretKey));
        return hashMap;
    }


    /**
     * 生成get请求头信息集合
     *
     * @param
     * @return
     */
    private   Map<String, String> getHeaderByGet(){
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put("accessKey", accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("timesTamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtils.getSign(GET_BODY, secretKey));
        return hashMap;
    }
}
