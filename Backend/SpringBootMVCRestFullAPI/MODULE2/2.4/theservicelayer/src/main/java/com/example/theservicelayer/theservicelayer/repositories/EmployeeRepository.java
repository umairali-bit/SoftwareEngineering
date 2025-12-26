package com.example.theservicelayer.theservicelayer.repositories;
import com.example.theservicelayer.theservicelayer.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    


}
