package top.gabin.demo.pay.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.Session;

@Configuration
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    /**
     * 连接工厂
     * @param redeliveryPolicy
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory connectionFactory(RedeliveryPolicy redeliveryPolicy){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin","admin",brokerUrl);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }

    /**
     * 重发配置
     * @return
     */
    @Bean
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        return redeliveryPolicy;
    }

    /**
     * 设置消息队列 确认机制
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory jmsListenerContainerFactory(ActiveMQConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        bean.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return bean;
    }

}