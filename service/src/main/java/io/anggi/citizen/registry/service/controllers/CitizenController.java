package io.anggi.citizen.registry.service.controllers;

import io.anggi.citizen.registry.domain.Citizen;
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

    @PostMapping
    public ResponseEntity<Citizen> createCitizen(@Valid @RequestBody Citizen citizen) {
        Citizen created = citizenService.createCitizen(citizen);
        return ResponseEntity
                .created(URI.create("/api/citizens/" + created.getIdNumber()))
                .body(created);
    }

    @PutMapping("/{idNumber}")
    public ResponseEntity<Citizen> updateCitizen(
            @PathVariable("idNumber") String idNumber,
            @Valid @RequestBody Citizen citizen) {
        Citizen updated = citizenService.updateCitizen(idNumber, citizen);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<Citizen>> getAllCitizens() {
        List<Citizen> citizens = citizenService.getAllCitizens();
        return ResponseEntity.ok(citizens);
    }

    @GetMapping("/{idNumber}")
    public ResponseEntity<Citizen> getCitizenById(@PathVariable("idNumber") String idNumber) {
        Citizen citizen = citizenService.getCitizenById(idNumber);
        return ResponseEntity.ok(citizen);
    }

    @DeleteMapping("/{idNumber}")
    public ResponseEntity<Void> deleteCitizen(@PathVariable("idNumber") String idNumber) {
        citizenService.deleteCitizen(idNumber);
        return ResponseEntity.noContent().build();
    }
}
