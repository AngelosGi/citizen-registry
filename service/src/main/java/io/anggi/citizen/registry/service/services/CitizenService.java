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
            throw new IllegalArgumentException("O πολίτης με Α.Τ. " + citizen.getIdNumber() + " υπάρχει είδη.");
        }
        return citizenRepository.save(citizen);
    }

    @Transactional
    public Citizen updateCitizen(String idNumber, @Valid Citizen citizenDetails) {
        Citizen existingCitizen = citizenRepository.findById(idNumber)
                .orElseThrow(() -> new EntityNotFoundException("O πολίτης με Α.Τ. " + idNumber + " δεν βρέθηκε."));

        Optional.ofNullable(citizenDetails.getAddress()).ifPresent(existingCitizen::setAddress);
        Optional.ofNullable(citizenDetails.getAfm()).ifPresent(existingCitizen::setAfm);

        return citizenRepository.save(existingCitizen);
    }

    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }

    public Citizen getCitizenById(String idNumber) {
        return citizenRepository.findById(idNumber)
                .orElseThrow(() -> new EntityNotFoundException("O πολίτης με Α.Τ. " + idNumber + " δεν βρέθηκε."));
    }

    public List<Citizen> searchCitizens(String idNumber, String firstName, String lastName, String afm) {
        // Logic for searching based on input parameters
        if (idNumber != null && !idNumber.isBlank()) {
            return citizenRepository.findByIdNumber(idNumber);
        } else if (firstName != null && lastName != null) {
            return citizenRepository.findByFirstNameOrLastName(firstName, lastName);
        } else if (firstName != null) {
            return citizenRepository.findByFirstName(firstName);
        } else if (lastName != null) {
            return citizenRepository.findByLastName(lastName);
        } else if (afm != null) {
            return citizenRepository.findByAfm(afm);
        } else {
            return citizenRepository.findAll();
        }
    }

    @Transactional
    public void deleteCitizen(String idNumber) {
        if (!citizenRepository.existsById(idNumber)) {
            throw new EntityNotFoundException("O πολίτης με Α.Τ. " + idNumber + " δεν βρέθηκε.");
        }
        citizenRepository.deleteById(idNumber);
    }
}