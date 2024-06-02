package org.example.spring;

public class BeanDefination {
    /**
     * bean的全类名
     */
    private String clazzName;

    /**
     * 标记Bean的类型：singleton、prototype
     */
    private String type;


    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
