package com.yoga.medledger.controller;

import com.yoga.medledger.dto.MedicalRecordRequest;
import com.yoga.medledger.entity.MedicalRecord;
import com.yoga.medledger.entity.User;
import com.yoga.medledger.repository.UserRepository;
import com.yoga.medledger.service.MedicalRecordService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/records")
public class MedicalRecordController {


    public MedicalRecordController(MedicalRecordService medicalRecordService,
                                   UserRepository userRepository) {
        this.medicalRecordService = medicalRecordService;
        this.userRepository = userRepository;
    }
    private final UserRepository userRepository;
    private final MedicalRecordService medicalRecordService;

    

    // ✅ Doctor creates a record
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")

    public MedicalRecord createRecord(@RequestBody MedicalRecordRequest request) {

        // TEMP: we will replace this with JWT user later

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        User doctor = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return medicalRecordService.createMedicalRecord(
                doctor,
                patient,
                request.getDiagnosis(),
                request.getTreatment(),
                request.getNotes()
        );
    }

    // ✅ Get record by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT','ADMIN')")
    public String getRecordById(@PathVariable Long id) {

        return "Fetch record by id: " + id;
    }

    // ✅ Get all records for a patient
    @GetMapping("/patient/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT')")
    public String getRecordsByPatient(@PathVariable Long id) {

        return "Fetch records for patient: " + id;
    }
}