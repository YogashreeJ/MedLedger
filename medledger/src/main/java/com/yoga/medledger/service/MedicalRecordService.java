package com.yoga.medledger.service;

import com.yoga.medledger.entity.MedicalRecord;
import com.yoga.medledger.entity.User;
import com.yoga.medledger.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    private MedicalRecord fetchLatestVersion(User patient){

        Optional<MedicalRecord> latestRecord =
                medicalRecordRepository.findTopByPatientOrderByVersionDesc(patient);

        return latestRecord.orElse(null);
    }

    private int nextVersion(MedicalRecord latestRecord){

        if(latestRecord == null){
            return 1;
        }

        return latestRecord.getVersion() + 1;
    }

    private String generateHash(MedicalRecord record) {

        try {

            String data =
                    record.getPatient().getId() +
                            record.getDoctor().getId() +
                            record.getDiagnosis() +
                            record.getTreatment() +
                            record.getNotes() +
                            record.getTimestamp() +
                            record.getPreviousHash();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }
}