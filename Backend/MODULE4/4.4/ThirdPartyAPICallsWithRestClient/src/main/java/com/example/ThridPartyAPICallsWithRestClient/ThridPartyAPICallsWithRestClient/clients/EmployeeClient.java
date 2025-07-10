package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.clients;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {

    List<EmployeeDTO> getAllEmployees();


}
