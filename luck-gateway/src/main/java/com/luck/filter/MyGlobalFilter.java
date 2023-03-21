package com.luck.filter;

import com.luck.constant.Constarnt;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(Constarnt.ACCESS_TOKEN);

        String url = request.getPath().toString();
        if(url.contains("/user/login")){
            return chain.filter(exchange);
        }
//        if (StringUtils.isBlank(token)) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);//设置status
//            final ServerHttpResponse response = exchange.getResponse();
//
//            byte[] bytes = new byte[0];
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                R resultInfo =new R();
//                bytes =  JSON.toJSONString(request).getBytes();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("----------------------");
//            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
//            return response.writeWith(Flux.just(buffer));//设置body
//        }
        ServerHttpRequest request1 = exchange.getRequest().mutate().headers(httpHeaders -> {
            if(token!=null){
                httpHeaders.add(Constarnt.ACCESS_TOKEN,token);
            }
        }).build();

        exchange=exchange.mutate().request(request1).build();
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        // 越小越先执行
        return 1;
    }
}
