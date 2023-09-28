package cn.jishuqin.gmapiclientsdk.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 顾梦
 * @create 2023/5/31
 */
@Data
@NoArgsConstructor
public class Api implements Serializable {

    /**
     * 请求地址
     */
    String url;
    /**
     * 请求体
     */
    Object body;
    /**
     * 请求
     */
    Map<String,Object> params;
    /**
     * 请求方法
     */
    String method;


    public Api(String method, String url) {
        this.method = method;
        this.url = url;
    }
}