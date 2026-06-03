package com.tecsup.petclinic.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VetDTO {
    private Integer id;
    private String firstName;
    private String lastName;
}