package cn.jishuqin.gmapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.jishuqin.gmapiclientsdk.model.Api;
import cn.jishuqin.gmapiclientsdk.utils.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static cn.jishuqin.gmapiclientsdk.utils.Constant.GATEWAY_HOST;
import static cn.jishuqin.gmapiclientsdk.utils.Constant.GET_BODY;
import static cn.jishuqin.gmapiclientsdk.utils.SignUtils.getSign;


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
        if ("get".equals(api.getMethod()) || "GET".equals(api.getMethod())) {
            Map<String, Object> params = api.getParams();
            return HttpUtil.createGet(GATEWAY_HOST +
                            api.getUrl())
                    .header("Accept", "application/json;charset=UTF-8")
                    .addHeaders(getHeaderByGet(params))
                    .form(params)
                    .charset("UTF-8")
                    .execute().body();

        } else {
            String json = JSONUtil.toJsonStr(api.getBody());
            return HttpRequest.post(GATEWAY_HOST + api.getUrl())
                    .header("Accept", "application/json; charset=utf-8")
                    .addHeaders(getheaderMapByPost(json))
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
    private Map<String,String> getheaderMapByPost(String body) {
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        try {
            hashMap.put("body", URLEncoder.encode(body,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
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
    private Map<String, String> getHeaderByGet(Map<String,Object> params){
        Map<String, String> hashMap = new HashMap<>();

        if (!params.isEmpty()){
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                sb.append(key).append("=").append(value).append(", ");
            }

            // 移除最后的逗号和空格
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 2);
            }
            try {
                hashMap.put("body", URLEncoder.encode(sb.toString(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }else {
            hashMap.put("body",null);
        }

        hashMap.put("accessKey", accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("timesTamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtils.getSign(GET_BODY, secretKey));
        return hashMap;
    }
}
