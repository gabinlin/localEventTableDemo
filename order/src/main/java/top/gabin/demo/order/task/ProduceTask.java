package top.gabin.demo.order.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.gabin.demo.order.entity.OrderEvent;
import top.gabin.demo.order.service.OrderService;

import java.util.List;

/**
 * @author 马士兵教育:chaopengfei
 * @date 2020/7/30
 */
@Component
public class ProduceTask {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void task() {
        System.out.println("定时任务");
        List<OrderEvent> orderEventList = orderService.findCreateEvent();
        for (OrderEvent event : orderEventList) {
            orderService.syncPay(event);
        }
    }

}
