package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.clients.EmployeeClient;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;

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

}
