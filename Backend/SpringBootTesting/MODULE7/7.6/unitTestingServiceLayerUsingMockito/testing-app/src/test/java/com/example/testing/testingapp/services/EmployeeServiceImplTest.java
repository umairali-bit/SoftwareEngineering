package com.example.testing.testingapp.services;

import com.example.testing.testingapp.TestContainerConfiguration;
import com.example.testing.testingapp.dto.EmployeeDto;
import com.example.testing.testingapp.entities.Employee;
import com.example.testing.testingapp.exceptions.ResourceNotFoundException;
import com.example.testing.testingapp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@Import(TestContainerConfiguration.class)
//@DataJpaTest (only for repositories)
//@SpringBootTest (for integration testing)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Spy
    private ModelMapper modelMapper;

    private Employee mockEmployee;
    private EmployeeDto mockEmployeeDto;

    @BeforeEach
    void setUp() {
        mockEmployee = Employee.builder()
                .id(1L)
                .email("mockEmployee@xyz.com")
                .name("mockEmployee")
                .salary(80_000L)
                .build();

        mockEmployeeDto = EmployeeDto.builder()
                .id(mockEmployee.getId())
                .name(mockEmployee.getName())
                .salary(mockEmployee.getSalary())
                .email(mockEmployee.getEmail())
                .build();



        //or
      //  mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);

    }

    @Test
    void testGetEmployeeById_whenEmployeeIsNotPresent_thenThrowException()
    {
//  assign
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
//  act and assert
        assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");

        verify(employeeRepository).findById(1L);


    }



    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto() {

//      assign
        Long id = mockEmployee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee)); // stubbing

//      act
        EmployeeDto employeeDto = employeeService.getEmployeeById(1L);


//      assert
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getId()).isEqualTo(id);
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());

        verify(employeeRepository).findById(id);

        /*
        verify(employeeRepository, times(2)).findById(id);
        verify(employeeRepository, atLeast(1)).findById(id);
        verify(employeeRepository, atMost(1)).findById(id);
        verify(employeeRepository, only()).findById(id);
         */
    }

    @Test
    void testCreateEmployee_IfValidEmployee_ThenCreateNewEmployee() {

//         assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);


//         act
        EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto);

//         assert
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());

        //verify(employeeRepository).save(any(Employee.class));

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());

        Employee capturedEmployee = captor.getValue();
        assertThat(capturedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());



    }

    @Test
    void testCreateEmployee_whenEmployeeIsPresent_thenThrowException()
    {
 //  assign
        when(employeeRepository.findByEmail(mockEmployeeDto.getEmail())).thenReturn(List.of(mockEmployee));

//   act and assert

        assertThatThrownBy(() -> employeeService.createNewEmployee(mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: " + mockEmployeeDto.getEmail());

        verify(employeeRepository).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepository,never()).save(any());
    }



}