package org.example.spring;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MyApplicationContext {

    private final ConcurrentMap<String, BeanDefination> beanDefinationMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> beanSingletonMap = new ConcurrentHashMap<>();
    // TODO: 后置处理器集合属性
    //

    // TODO: 后改为单例模式
    public MyApplicationContext(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(ComponentScan.class)) {
            throw new RuntimeException("传入入口配置类错误");
        }
        // TODO: 扫描包拿到所有文件集合
        // TODO：遍历每个文件 判断注解，放入beanDefinationMap容器 及相关处理
        // TODO: （后置处理器处理 添加入map容器
        // TODO: 单例bean提前做好初始化，放入beanSingletonMap容器
    }

    public Object getBean(String beanName){
        BeanDefination beanDefination = beanDefinationMap.get(beanName);
        if ("singleton".equals(beanDefination.getType())) {
            Object o = beanSingletonMap.get(beanName);
            if(o == null) {
                o = createBean(beanName, beanDefination);
                beanSingletonMap.put(beanName, o);
            }
            return o;
        }
        return createBean(beanName, beanDefination);
    }

    private Object createBean(String beanName, BeanDefination beanDefination){
        // TODO: 构造、依赖注入、初始化、后置处理器；
        // TODO：其他相关操作
        return null;
    }
}
