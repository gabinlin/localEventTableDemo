package top.gabin.demo.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.gabin.demo.order.constant.EventType;
import top.gabin.demo.order.entity.OrderEvent;

import java.util.List;

@Repository
public interface OrderEventDao extends JpaRepository<OrderEvent, Long> {
    List<OrderEvent> findByEventType(EventType eventType);
}
