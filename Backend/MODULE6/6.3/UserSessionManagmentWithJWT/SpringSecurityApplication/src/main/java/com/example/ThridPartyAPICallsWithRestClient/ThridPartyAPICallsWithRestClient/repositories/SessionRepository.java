package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.repositories;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Long> {

}
