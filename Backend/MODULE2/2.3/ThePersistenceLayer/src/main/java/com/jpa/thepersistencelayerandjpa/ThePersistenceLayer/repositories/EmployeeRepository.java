package com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.repositories;

import com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {


}
