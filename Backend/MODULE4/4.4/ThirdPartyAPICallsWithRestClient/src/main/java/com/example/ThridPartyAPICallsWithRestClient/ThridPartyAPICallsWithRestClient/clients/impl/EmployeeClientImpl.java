package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.clients.impl;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.clients.EmployeeClient;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;


@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employeeDTO = restClient.get()
                .uri("employees")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        return employeeDTO;
    }
}
