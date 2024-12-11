package io.anggi.citizen.registry.service.controllers;

import io.anggi.citizen.registry.domain.Citizen;
import io.anggi.citizen.registry.service.repositories.CitizenRepository;
import io.anggi.citizen.registry.service.services.CitizenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/citizens")
@RequiredArgsConstructor
public class CitizenController {

    private final CitizenService citizenService;
    private final CitizenRepository citizenRepository;


    //I tried "consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"} "
    // but the browser defaults to XML and i couldn't find a way to make Json default.

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Citizen> createCitizen(@Valid @RequestBody Citizen citizen) {
        Citizen created = citizenService.createCitizen(citizen);
        return ResponseEntity
                .created(URI.create("/api/citizens/" + created.getIdNumber()))
                .body(created);
    }

    @PutMapping(value = "/{idNumber}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Citizen> updateCitizen(
            @PathVariable("idNumber") String idNumber,
            @Valid @RequestBody Citizen citizen) {
        if (citizen.getIdNumber().length() != 8) {
            throw new IllegalArgumentException("O Α.Τ. πρέπει να έχει ακριβώς 8 χαρακτήρες.");
        }
        Citizen updated = citizenService.updateCitizen(idNumber, citizen);
        return ResponseEntity.ok(updated);
    }

    @GetMapping(value = "/search", produces = {"application/json"})
    public ResponseEntity<List<Citizen>> searchCitizens(
            @RequestParam(required = false) String idNumber,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String afm) {

        if (idNumber != null && idNumber.length() != 8) {
            throw new IllegalArgumentException("Ο αριθμός ταυτότητας (ΑΤ) πρέπει να έχει ακριβώς 8 χαρακτήρες.");
        }

        if (afm != null && !afm.matches("\\d{9}")) {
            throw new IllegalArgumentException("Ο ΑΦΜ πρέπει να έχει ακριβώς 9 ψηφία.");
        }

        List<Citizen> citizens = citizenService.searchCitizens(idNumber, firstName, lastName, afm);
        return ResponseEntity.ok(citizens);
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<Citizen>> getAllCitizens() {
        List<Citizen> citizens = citizenService.getAllCitizens();
        return ResponseEntity.ok(citizens);
    }

    @GetMapping(value = "/{idNumber}", produces = {"application/json"})
    public ResponseEntity<Citizen> getCitizenById(@PathVariable("idNumber") String idNumber) {
        if (idNumber.length() != 8) {
            throw new IllegalArgumentException("O Α.Τ. πρέπει να έχει ακριβώς 8 χαρακτήρες.");
        }
        Citizen citizen = citizenService.getCitizenById(idNumber);
        return ResponseEntity.ok(citizen);
    }

    @DeleteMapping(value = "/{idNumber}", produces = {"application/json"})
    public ResponseEntity<Void> deleteCitizen(@PathVariable("idNumber") String idNumber) {
        if (idNumber.length() != 8) {
            throw new IllegalArgumentException("Το Α.Τ. πρέπει να έχει ακριβώς 8 χαρακτήρες.");
        }
        citizenService.deleteCitizen(idNumber);
        return ResponseEntity.noContent().build();
    }
}

