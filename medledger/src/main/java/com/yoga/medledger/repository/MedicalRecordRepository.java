package com.yoga.medledger.repository;

import com.yoga.medledger.entity.User;
import com.yoga.medledger.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatient(User patient);
    Optional<MedicalRecord> findTopByPatientOrderByVersionDesc(User patient);
}
