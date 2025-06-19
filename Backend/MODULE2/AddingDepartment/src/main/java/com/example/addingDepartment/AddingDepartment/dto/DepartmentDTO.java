package com.example.addingDepartment.AddingDepartment.dto;

import com.example.addingDepartment.AddingDepartment.annotations.PrimeNumberInput;
import com.example.addingDepartment.AddingDepartment.annotations.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    @Null(message = "ID must be null when creating a new department")
    private Long id;

    @NotNull(message = "Department title is required")
    @Size(min = 2, max = 30, message = "Title must be between 2 and 30 characters")
    @NotBlank(message = "Title can't be blank")
    private String title;

    @Pattern(regexp = "^[A-Z]{3}-\\d{3}$", message = "Code must match pattern e.g. ABC-123")
    private String code;

    @URL(message = "Website must be a valid URL")
    private String website;

    private boolean isActive;

    @PastOrPresent(message = "Created date must be in the past or today")
    private LocalDate createdAt;

    @NotNull
    @PrimeNumberInput(message = "Please enter a prime number for tokenId")
    private Integer tokenId;

    @NotBlank(message = "Password is required")
    @ValidPassword
    private String passwords;


}
