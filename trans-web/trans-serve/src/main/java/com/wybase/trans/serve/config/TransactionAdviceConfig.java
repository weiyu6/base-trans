package com.wybase.trans.serve.config;

import com.wybase.trans.common.consts.TransConsts;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 事务管理配置类
 * @author weiyu
 * @date 2023/7/29
 */
@Aspect
@Configuration
public class TransactionAdviceConfig {

    @Autowired
    private TransactionManager transactionManager;

    /**
     * 事务管理配置
     */
    @Bean
    public TransactionInterceptor interceptor() {
        // 事务管理规则，承载需要进行事务管理的方法名（模糊匹配）及设置的事务管理属性
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        // 当抛出设置的对应异常后，进行事务回滚（此处设置为“Exception”级别）
        transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        // 设置传播行为(支持当前交易； 如果不存在，则创建一个新的。)
        transactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置隔离级别(表示防止脏读； 可能会发生不可重复读和幻读。)
        transactionAttribute.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

        // 建立一个map，用来储存要需要进行事务管理的方法名（模糊匹配）
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("*", transactionAttribute);
        // 注入设置好的map
        source.setNameMap(txMap);
        // 实例化事务拦截器
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }

    /**
     * 利用AspectJExpressionPointcut设置切面
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        // 声明切点要切入的面
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // 设置需要被拦截的路径
        pointcut.setExpression(TransConsts.AOP_POINTCUT_EXPRESSION);
        // 设置切面和配置好的事务管理
        return new DefaultPointcutAdvisor(pointcut, interceptor());
    }
}
