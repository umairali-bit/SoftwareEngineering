package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime lastUsedAt;

    @ManyToOne(optional = false)
    private UserEntity user;

    @PrePersist
    public void prePersist() {
        if (lastUsedAt == null) lastUsedAt = LocalDateTime.now();
    }
}
