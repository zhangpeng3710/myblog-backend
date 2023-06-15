package com.roc.blog.agent;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.namedOneOf;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description
 * @Author: Zhang Peng
 * @Date: 2023/6/15
 */
class ByteBuddyTest {
    @Test
    public void test() throws Exception {
        String toString = "hello ByteBuddy";
        String name = "com.joe.ByteBuddyObject";
        String[] methods = {"toString","say"};


        Class<? extends People> clazz;
        try (DynamicType.Unloaded<People> unloaded = new ByteBuddy()
                .subclass(People.class)
                .name("com.joe.ByteBuddyObject")
                .method(namedOneOf(methods))
                .intercept(MethodDelegation.to(new JoeKerouac()))
                .make();) {

            clazz = unloaded
                    .load(ByteBuddyTest.class.getClassLoader())
                    .getLoaded();
        }
        System.out.println(clazz.getDeclaredConstructor().newInstance().toString());
        assertEquals(clazz.getDeclaredConstructor().newInstance().toString(), toString);
        assertEquals(clazz.getName(), name);
        assertSame(clazz.getInterfaces()[0], People.class);
        assertEquals(clazz.getDeclaredConstructor().newInstance().say(), "hello JoeKerouac");
    }

    public interface People{
        String say();
        String toString();
    }

    public class JoeKerouac {
        public String say() {
            return "hello JoeKerouac";
        }
        public String toString() {
            return "hello ByteBuddy";
        }
    }

}