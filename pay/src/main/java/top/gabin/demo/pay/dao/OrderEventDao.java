package top.gabin.demo.pay.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.gabin.demo.pay.entity.OrderEvent;

@Repository
public interface OrderEventDao extends JpaRepository<OrderEvent, Long> {
}
