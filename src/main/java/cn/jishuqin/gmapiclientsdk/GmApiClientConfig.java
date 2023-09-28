package cn.jishuqin.gmapiclientsdk;

import cn.jishuqin.gmapiclientsdk.client.GmApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 顾梦
 * @create 2023/4/26
 */
@Configuration
@ConfigurationProperties("gmapi.client")
@Data
@ComponentScan
public class GmApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public GmApiClient gmApiClient(){
        return new GmApiClient(accessKey,secretKey);
    }

}







