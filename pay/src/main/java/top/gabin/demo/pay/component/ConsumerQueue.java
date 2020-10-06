package top.gabin.demo.pay.component;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import top.gabin.demo.pay.entity.OrderEvent;
import top.gabin.demo.pay.service.PayService;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class ConsumerQueue {

    @Autowired
    private PayService payService;

    @JmsListener(destination = "ActiveMQQueue", containerFactory = "jmsListenerContainerFactory")
    public void receive(TextMessage textMessage, Session session) throws JMSException {
        try {
            System.out.println("收到的消息：" + textMessage.getText());
            String content = textMessage.getText();
            OrderEvent orderEvent = (OrderEvent) JSONObject.toBean(JSONObject.fromObject(content), OrderEvent.class);
            payService.pay(orderEvent);
            // 业务完成，确认消息 消费成功
            textMessage.acknowledge();
        } catch (Exception e) {
            // 回滚消息
            System.out.println("异常:" + e.getMessage());
            session.recover();
        }

    }

    /**
     * 补偿 处理（人工，脚本）。自己根据自己情况。
     * 本来destination好像说是上面的队列名加个DLQ.前缀：DLQ.ActiveMQQueue，可是我的mbp brew 安装的版本好像实际上是去掉ActiveMQ.DLQ
     * @param text
     */
    @JmsListener(destination = "ActiveMQ.DLQ", containerFactory = "jmsListenerContainerFactory")
    public void dlq(String text) {
        System.out.println("死信队列:" + text);
        // 这边做补偿机制，保证最终一致性
    }
}
