package io.anggi.citizen.registry.service.services;

import io.anggi.citizen.registry.domain.Citizen;
import io.anggi.citizen.registry.service.repositories.CitizenRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitizenService {

    private final CitizenRepository citizenRepository;

    @Transactional
    public Citizen createCitizen(@Valid Citizen citizen) {
        if (citizenRepository.existsById(citizen.getIdNumber())) {
            throw new IllegalArgumentException("Citizen with ID " + citizen.getIdNumber() + " already exists.");
        }
        return citizenRepository.save(citizen);
    }

    @Transactional
    public Citizen updateCitizen(String idNumber, @Valid Citizen citizenDetails) {
        Citizen existingCitizen = citizenRepository.findById(idNumber)
                .orElseThrow(() -> new EntityNotFoundException("Citizen with ID " + idNumber + " not found"));

        // Partial update logic
        Optional.ofNullable(citizenDetails.getFirstName()).ifPresent(existingCitizen::setFirstName);
        Optional.ofNullable(citizenDetails.getLastName()).ifPresent(existingCitizen::setLastName);
        Optional.ofNullable(citizenDetails.getAddress()).ifPresent(existingCitizen::setAddress);
        Optional.ofNullable(citizenDetails.getAfm()).ifPresent(existingCitizen::setAfm);
        Optional.ofNullable(citizenDetails.getGender()).ifPresent(existingCitizen::setGender);
        Optional.ofNullable(citizenDetails.getBirthDate()).ifPresent(existingCitizen::setBirthDate);

        return citizenRepository.save(existingCitizen);
    }

    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }

    public Citizen getCitizenById(String idNumber) {
        return citizenRepository.findById(idNumber)
                .orElseThrow(() -> new EntityNotFoundException("Citizen with ID " + idNumber + " not found"));
    }

    @Transactional
    public void deleteCitizen(String idNumber) {
        if (!citizenRepository.existsById(idNumber)) {
            throw new EntityNotFoundException("Citizen with ID " + idNumber + " not found");
        }
        citizenRepository.deleteById(idNumber);
    }
}