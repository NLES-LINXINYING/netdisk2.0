package cn.edu.scau.lxy.netdisk.gateway.config;

import cn.edu.scau.lxy.netdisk.gateway.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author linxinying
 * @description 网关微服务配置类
 * @date 2020/3/16 11:11
 */

@Configuration
public class GatewayConfig {

    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}
