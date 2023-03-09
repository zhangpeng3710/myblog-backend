package com.roc.blog.server.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roc.blog.server.common.ResultData;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.annotation.Resource;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Resource
    private ObjectMapper mapper;

    /**
     * 判断是否要执行 beforeBodyWrite 方法，true为执行，false不执行，有注解标记的时候处理返回值
     * 这里整合swagger出现了问题，swagger相关的不拦截
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !(returnType.getDeclaringClass().getName().contains("OpenApi")
                || returnType.getDeclaringClass().getName().contains("openapi")
                || returnType.getDeclaringClass().getName().contains("swagger")
                || returnType.getDeclaringClass().getName().contains("Swagger")
        );
    }


    @SneakyThrows
    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        if (body instanceof String) {
            return mapper.writeValueAsString(ResultData.success(body));
        }
        if (body instanceof ResultData) {
            return body;
        }
        return ResultData.success(body);
    }
}

