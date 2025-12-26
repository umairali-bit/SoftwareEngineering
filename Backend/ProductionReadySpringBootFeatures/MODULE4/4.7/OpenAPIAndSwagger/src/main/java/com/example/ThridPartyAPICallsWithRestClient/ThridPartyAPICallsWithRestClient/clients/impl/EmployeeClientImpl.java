package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.clients.impl;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.advices.ApiResponse;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.clients.EmployeeClient;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.EmployeeDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    @Override
    public List<EmployeeDTO> getAllEmployees() {
//
//        log.error("error log");
//        log.info("info log");
//        log.warn("warn log");
//        log.debug("debug log");
//        log.trace("trace log");

        log.trace("getAllEmployees()");

        try{
            ApiResponse<List<EmployeeDTO>> employeeDTO = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                        log.error(new String(response.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("Employees could not be retrieved");
                    }))
                    .body(new ParameterizedTypeReference<ApiResponse<List<EmployeeDTO>>>() {});

            log.debug("employee list retrieved");
            log.trace("employee list retrieved : {}", employeeDTO.getData());
            return employeeDTO.getData();

        } catch (Exception e) {
            log.error("Exception in EmployeeClientImpl.getAllEmployees", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        log.info("getEmployeeById({})", employeeId);
        try{
            ApiResponse<EmployeeDTO> employeeDTOApiResponse = restClient.get()
                    .uri("employees/{employeeId}", employeeId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                        log.error(new String(response.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("Employee could not be found");
                    }))

                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {
                    });
            return employeeDTOApiResponse.getData();
        } catch (Exception e) {
            log.error("Exception in EmployeeClientImpl.getAllEmployees", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.info("createEmployee({})", employeeDTO);
        try{
            ResponseEntity<ApiResponse<EmployeeDTO>> employeeDTOApiResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                        log.debug("4xxClientError {}", request.getURI());
                        log.error(new String(response.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("Employee could not be created");
                    }))
                    .toEntity(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {});

            log.trace("employee create {}", employeeDTOApiResponse.getBody().getData());
            return employeeDTOApiResponse.getBody().getData();

        }
        catch (Exception e){
            log.error("Exception in EmployeeClientImpl.getAllEmployees", e);
            throw new RuntimeException(e);
        }
    }
}
