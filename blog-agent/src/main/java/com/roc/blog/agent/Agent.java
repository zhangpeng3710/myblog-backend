package com.roc.blog.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @Description
 * @Author: Zhang Peng
 * @Date: 2023/6/15
 */
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("MyAgent init...");

        AgentBuilder.Transformer transformer = (
                builder,
                typeDescription,
                classLoader,
                javaModule,
                protectionDomain) -> builder
                //拦截任意方法
                .method(ElementMatchers.any())
                //委托
                .intercept(MethodDelegation.to(MethodCostTime.class));

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

        };

        new AgentBuilder
                .Default()
                //指定需要拦截的类
                .type(ElementMatchers.nameStartsWith("com.roc.blog.server"))
                .transform(transformer)
                .with(listener)
                .installOn(inst);

        new Thread(
                () -> {
                    GrpcServer server = new GrpcServer();
                    server.initGrpcServer();
                }
        ).start();

    }

}
