package org.example.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
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
        // 扫描包拿到所有文件集合
        ComponentScan annotation = configClass.getAnnotation(ComponentScan.class);
        String path = annotation.value();// com.example.service
        System.out.println("oldPath:"+path);
        path = path.replace('.','/');
        System.out.println("newPath:"+path);
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(path);
        File file = new File(resource.getFile());
        System.out.println(file);
        //TODO:
        // 1. properties扫描
        // 2. bean包扫描的普适性
        if (file.isDirectory()){
            File[] files = file.listFiles();
            // 遍历每个文件 判断注解，放入beanDefinationMap容器 及相关处理
            for(File e : files){
                String filePath = e.getAbsolutePath();
                System.out.println("filePath:"+filePath);
                if(filePath.endsWith(".class")){
                    //TODO: 普适性
                    String className = filePath.substring(filePath.indexOf("org"), filePath.indexOf(".class"));
                    className = className.replace('\\','.');
                    try {
                        Class<?> beanClass = classLoader.loadClass(className);
                        //TODO：普适性
                        if(beanClass.isAnnotationPresent(Component.class)){
                            // TODO: 如果不指定名称，beanName默认是 类名首字母小写
                            String beanName = beanClass.getAnnotation(Component.class).value();
                            BeanDefination beanDefination = new BeanDefination();
                            beanDefination.setClazz(beanClass);
                            if(beanClass.isAnnotationPresent(Scope.class)){
                                beanDefination.setType(beanClass.getAnnotation(Scope.class).value());
                            } else{
                                beanDefination.setType("singleton");
                            }
                            beanDefinationMap.put(beanName, beanDefination);
                        }
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    //TODO:
                }
            }
        } else{
            // TODO:
        }
        // TODO: （后置处理器处理 添加入map容器
        // 单例bean提前做好初始化，放入beanSingletonMap容器
        for(Map.Entry<String, BeanDefination> e : beanDefinationMap.entrySet()){
            String beanName = e.getKey();
            BeanDefination beanDefination = e.getValue();
            if("singleton".equals(beanDefination.getType())){
                Object bean = createBean(beanName, beanDefination);
                beanSingletonMap.put(beanName, bean);
            }
        }
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
        //1. 构造
        Class clazz = beanDefination.getClazz();
        try {
            Object o = clazz.getConstructor().newInstance();
            return o;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        // 2. 依赖注入
        // 3. 初始化
        // 4. 后置处理器
        // TODO：其他相关操作
    }
}
