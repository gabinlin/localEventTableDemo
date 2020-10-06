package top.gabin.demo.order.entity;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;
import top.gabin.demo.order.constant.EventType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table
@Entity
@Data
public class OrderEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private EventType eventType;

    /**
     * 事件环节（new,published,processed)
     */
    private String process;

    /**
     * 事件内容，保存事件发生时需要传递的数据
     */
    private String content;

    private Date createTime;

    private Date updateTime;


}