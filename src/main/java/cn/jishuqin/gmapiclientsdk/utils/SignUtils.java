package cn.jishuqin.gmapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import org.apache.commons.lang3.StringUtils;

import static cn.jishuqin.gmapiclientsdk.utils.Constant.GET_BODY;


/**
 * 签名工具
 * @author 顾梦
 * @create 2023/4/26
 */
public class SignUtils {

    /**
     * 生成签名
     * @param body
     * @param secretKey
     * @return
     */
    public static String getSign(String body, String secretKey){
        Digester digester = new Digester(DigestAlgorithm.SHA256);

        if (StringUtils.isBlank(body)){

            String content = GET_BODY + "." + secretKey;

            return digester.digestHex(content);

        }

        String content = body + "." + secretKey;
        return digester.digestHex(content);
    }
}
