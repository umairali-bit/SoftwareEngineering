package com.nestigo.systemdesign.nestigo.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hotel")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String city;

    @Column(columnDefinition = "TEXT[]")
    private String[] photos;

    @Column(columnDefinition = "TEXT[]")
    private String[] amenities;

    @Column(nullable = false)
    private boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAT;

    @Embedded
    private HotelContactInfo contactInfo;

    @OneToMany(mappedBy = "hotel")
    private List<RoomEntity> rooms;





}
