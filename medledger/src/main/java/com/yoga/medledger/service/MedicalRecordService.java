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

    private MedicalRecord fetchLatestVersion(User patient) {

        Optional<MedicalRecord> latestRecord =
                medicalRecordRepository.findTopByPatientOrderByVersionDesc(patient);

        return latestRecord.orElse(null);
    }

    private int nextVersion(MedicalRecord latestRecord) {

        if (latestRecord == null) {
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
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }
    public MedicalRecord createMedicalRecord(
            User doctor,
            User patient,
            String diagnosis,
            String treatment,
            String notes
    ) {

        // 1️⃣ Get latest record for this patient
        MedicalRecord latestRecord = fetchLatestVersion(patient);

        // 2️⃣ Determine next version
        int version = nextVersion(latestRecord);

        // 3️⃣ Determine previous hash
        String previousHash = latestRecord == null
                ? "GENESIS"
                : latestRecord.getCurrentHash();

        // 4️⃣ Create new record
        MedicalRecord record = new MedicalRecord();

        record.setDoctor(doctor);
        record.setPatient(patient);
        record.setDiagnosis(diagnosis);
        record.setTreatment(treatment);
        record.setNotes(notes);
        record.setVersion(version);
        record.setPreviousHash(previousHash);

        // timestamp already auto-set
        record.setTimestamp(java.time.LocalDateTime.now());
        // 5️⃣ Generate hash
        String currentHash = generateHash(record);
        record.setCurrentHash(currentHash);

        // 6️⃣ Save to DB
        return medicalRecordRepository.save(record);
    }
}