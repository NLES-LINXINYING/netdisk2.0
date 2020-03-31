package cn.edu.scau.lxy.netdisk.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linxinying
 * @description 令牌认证全局过滤器
 * @date 2020/3/16 9:59
 */
public class TokenFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //让部分请求跳过该路由器
        String url = exchange.getRequest().getURI().toString();
        if (url.startsWith("http://localhost:8763/user/user/login")
                || url.startsWith("http://localhost:8763/user/user/register")
                || url.startsWith("http://localhost:8763/netdisk/v1")
                || url.startsWith("http://localhost:8763/user/user/getPicture")
                || url.startsWith("http://localhost:8763/file/download/downloadFile")
        ) {
            return chain.filter(exchange);
        }


        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token == null || token.isEmpty()) {
            /*ServerHttpResponse response = exchange.getResponse();
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("code", 401);
            responseData.put("message", "非法请求");
            responseData.put("cause", "Token is empty");

            try {
                //将信息转换为JSON
                ObjectMapper objectMapper = new ObjectMapper();
                byte[] data = objectMapper.writeValueAsBytes(responseData);

                //输出错误信息到页面
                DataBuffer buffer = response.bufferFactory().wrap(data);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }*/
            String url2 = "http://localhost:8040/netdisk/v1/login";
            ServerHttpResponse response = exchange.getResponse();
            //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
            response.setStatusCode(HttpStatus.SEE_OTHER);
            response.getHeaders().set(HttpHeaders.LOCATION, url2);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
