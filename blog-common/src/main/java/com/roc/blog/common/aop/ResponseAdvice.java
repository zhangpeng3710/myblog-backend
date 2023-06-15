package com.roc.blog.common.aop;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.roc.blog.common.constant.ResponseData;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.annotation.Resource;

/**
 * global response advice
 *
 * filter -> interceptor -> controllerAdvice -> @Aspect -> controller
 *
 * @author Zhang Peng
 * @since 2023/3/27 16:00
 */

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Resource
    private ObjectMapper mapper;

    /**
     * 判断是否要执行 beforeBodyWrite 方法，true为执行，false不执行，有注解标记的时候处理返回值
     * 这里整合swagger出现了问题，swagger相关的不拦截
     */
    @NonNull
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
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {
        if (body instanceof String) {
            return mapper.writeValueAsString(ResponseData.success(body));
        }
        if (body instanceof ResponseData) {
            return body;
        }
        return ResponseData.success(body);
    }
}

