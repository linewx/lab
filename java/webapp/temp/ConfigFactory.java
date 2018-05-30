package com.linewx.law.instrument.config;

import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * Created by luganlin on 12/18/16.
 */
public class ConfigFactory {
    public ConfigFactory() {
    }

    public static MyConfig create(InputStream inputStream) throws Exception{
        Properties properties = new Properties();
        properties.load(inputStream);

        MyConfig config = (MyConfig)Proxy.newProxyInstance(MyConfig.class.getClassLoader(),
                new Class[] {MyConfig.class}, new PropertiesMapper(properties));

        return config;
    }

    public static void main(String ...argv) throws Exception{
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("persistence.properties");
        MyConfig config = ConfigFactory.create(is);
        System.out.println(config.getDriver());
    }

    public static class PropertiesMapper implements InvocationHandler {
        private Properties properties;

        public PropertiesMapper(Properties properties) {
            this.properties = properties;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            final Value value = method.getAnnotation(Value.class);
            if (value == null) {
                return null;
            }

            String property = (String)properties.get(value.value());

            final Class<?> returnClazz = method.getReturnType();

            if (returnClazz.isPrimitive()) {
                if (returnClazz.equals(int.class)) {
                    return Integer.valueOf(property);
                }
            }

            return property;

        }
    }

}
