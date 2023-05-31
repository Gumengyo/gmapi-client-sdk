package com.gumeng.gmapiclientsdk.model;

import lombok.Data;
import java.io.Serializable;

/**
 * @author 顾梦
 * @create 2023/5/31
 */
@Data
public class Api implements Serializable {
    /**
     * 用户id
     */
    Long id;
    /**
     * 接口id
     */
    String interfaceId;
    /**
     * 请求地址
     */
    String url;
    /**
     * 请求体
     */
    Object body;
    /**
     * 请求方法
     */
    String method;
}