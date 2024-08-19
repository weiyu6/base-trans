package com.wybase.trans.serve.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * TransApplicationContext 类实现了 BeanFactoryPostProcessor 接口，用于在 Spring 应用上下文中进行转换操作。
 * 它允许在 BeanFactoryPostProcessor 生命周期中对 BeanFactory 进行自定义处理。
 * @author weiyu
 * @date 2024/8/19
 */
@Component
public class TransApplicationContext implements BeanFactoryPostProcessor {
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 根据 Bean 的名称从 BeanFactory 中获取一个 Bean 的实例。
     * @param beanName String 类型的参数，表示要获取的 Bean 的名称。
     * @return 返回类型为 T 的 Bean 实例，其中 T 是泛型，表示可以是任何类型。
     */
    public static <T> T getBean(String beanName) {
        return (T) beanFactory.getBean(beanName);
    }
}
