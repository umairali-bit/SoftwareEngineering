package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.clients.EmployeeClient;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.EmployeeDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdReadyFeaturesApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

	@Test
   @Order(1)
	void getAllEmployees() {
		List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
		System.out.println(employeeDTOList);
	}

	@Test
    @org.junit.jupiter.api.Order(2)
	void getEmployeeById() {
		EmployeeDTO employeeDTO = employeeClient.getEmployeeById(1L); {
			System.out.println(employeeDTO);

		}
	}

    @Test
    @org.junit.jupiter.api.Order(1)
    void createEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Saul");
        employeeDTO.setAge(3);
        employeeDTO.setEmail("test@gmail.com");
        employeeDTO.setRole("USER");
        employeeDTO.setIsActive(true);
        EmployeeDTO employeeDTO1 = employeeClient.createEmployee(employeeDTO);
        System.out.println(employeeDTO1);

    }

}
