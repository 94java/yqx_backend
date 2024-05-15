package cc.jiusi.yqx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-04-28  16:54
 * @Description: 主类（项目启动入口）
 */
@SpringBootApplication
@MapperScan("cc.jiusi.yqx.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
