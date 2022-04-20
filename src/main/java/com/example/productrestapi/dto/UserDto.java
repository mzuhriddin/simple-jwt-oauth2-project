package com.example.productrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    @NotBlank(message = "username kiritilishi shart")
    private String username;
    @NotBlank(message = "password kiritilishi shart")
    private String password;
    private String role;
}
