package org.example.spring;

public class BeanDefination {
    /**
     * bean的类型
     */
    private Class clazz;

    /**
     * 标记Bean的类型：singleton、prototype
     */
    private String type;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
