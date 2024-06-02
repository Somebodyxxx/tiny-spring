package org.example.service;

import org.example.spring.Configuration;

/**
 * 因第三方组件是不可能带有@Component注解的，
 * 引入第三方Bean只能通过工厂模式，
 * 即在@Configuration工厂类中定义带@Bean的工厂方法
 */
@Configuration
public class DbConfig {
    //TODO：
}
