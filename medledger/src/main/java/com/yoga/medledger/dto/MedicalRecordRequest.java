package com.yoga.medledger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalRecordRequest {

    @NotNull
    private Long patientId;

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String treatment;

    private String notes;
}