package top.gabin.demo.order.service;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.gabin.demo.order.constant.EventType;
import top.gabin.demo.order.dao.OrderEventDao;
import top.gabin.demo.order.entity.OrderEvent;

import javax.jms.Queue;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderEventDao orderEventDao;
    @Autowired
    private Queue queue;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Transactional
    public void createOrder() {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventType(EventType.CREATE);
        orderEventDao.save(orderEvent);
    }

    public List<OrderEvent> findCreateEvent() {
        return orderEventDao.findByEventType(EventType.CREATE);
    }

    public void syncPay(OrderEvent event) {
        // 这边可以理解为mysql innoDB的redo log，先保存操作到日志（到本地事件表），实际上支付模块还未同步到支付状态
        event.setEventType(EventType.PAY);
        orderEventDao.save(event);
        System.out.println("同步支付状态到事件表，并发送通知给支付模块");

        // 发送消息到消息中间件，会被支付模块监听到
        jmsMessagingTemplate.convertAndSend(queue, JSONObject.fromObject(event).toString());
    }
}
