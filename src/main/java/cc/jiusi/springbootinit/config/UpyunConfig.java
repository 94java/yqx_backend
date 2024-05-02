package cc.jiusi.springbootinit.config;

import com.upyun.RestManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02  13:45
 * @Description: 又拍云 Config
 */
@Configuration
@ConfigurationProperties(prefix = "upyun")
@Data
public class UpyunConfig {
    private String bucketName;
    private String userName;
    private String password;
    @Bean
    public RestManager restManager(){
        return new RestManager(bucketName,userName,password);
    }
}
