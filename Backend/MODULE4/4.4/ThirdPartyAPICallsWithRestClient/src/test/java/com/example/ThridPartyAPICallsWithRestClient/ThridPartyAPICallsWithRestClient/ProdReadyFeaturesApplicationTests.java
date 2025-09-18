package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.clients.EmployeeClient;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.EmployeeDTO;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ProdReadyFeaturesApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

	@Test
	void getAllEmployees() {
		List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
		System.out.println(employeeDTOList);
	}

	@Test
	void getEmployeeById() {
		EmployeeDTO employeeDTO = employeeClient.getEmployeeById(1L); {
			System.out.println(employeeDTO);

		}
	}

    @Test
    void createEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Saul");
        employeeDTO.setAge(30);
        employeeDTO.setEmail("test@gmail.com");
        employeeDTO.setRole("USER");
        employeeDTO.setIsActive(true);
        EmployeeDTO employeeDTO1 = employeeClient.createEmployee(employeeDTO);
        System.out.println(employeeDTO1);

    }

}
