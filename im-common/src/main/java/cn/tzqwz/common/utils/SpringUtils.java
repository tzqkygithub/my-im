package cn.tzqwz.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringBean工具类
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    //Spring容器的上下文
    private static ApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> c){
        return context.getBean(c);
    }


    public static <T> T getBean(String name,Class<T> clazz){
        return context.getBean(name,clazz);
    }

}
