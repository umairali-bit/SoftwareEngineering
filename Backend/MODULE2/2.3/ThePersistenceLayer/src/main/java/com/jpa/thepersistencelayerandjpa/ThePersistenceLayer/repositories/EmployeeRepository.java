package com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.repositories;

import com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    


}
