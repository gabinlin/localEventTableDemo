package top.gabin.demo.pay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.gabin.demo.pay.constant.EventType;
import top.gabin.demo.pay.dao.OrderEventDao;
import top.gabin.demo.pay.entity.OrderEvent;

@Service
public class PayService {

    @Autowired
    private OrderEventDao orderEventDao;

    @Transactional
    public void pay(OrderEvent orderEvent) {
        // 异常
        Integer a = null;
        System.out.println(a.toString());
        orderEvent.setEventType(EventType.COMPLETE);
        orderEventDao.save(orderEvent);
    }


}
