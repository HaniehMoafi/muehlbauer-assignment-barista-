package de.muehlbauer.assignment.barista.entity;

import de.muehlbauer.assignment.barista.api.enumeration.OrderStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "BARISTA_ORDER")
@Data
public class BaristaOrderEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ORDER_NUMBER", unique = true)
    private Integer orderNumber;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
