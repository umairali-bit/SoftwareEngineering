package com.umair.ecommerce.shipping.entity;

import com.umair.ecommerce.shipping.entity.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingId;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;

    private String trackingNumber;
}
