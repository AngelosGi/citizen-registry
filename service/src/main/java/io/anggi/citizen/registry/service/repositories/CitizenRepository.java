package io.anggi.citizen.registry.service.repositories;


import io.anggi.citizen.registry.domain.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, String>, JpaSpecificationExecutor<Citizen> {
    List<Citizen> findByIdNumber(String IdNumber);
    List<Citizen> findByFirstName(String firstName);
    List<Citizen> findByLastName(String lastName);
    List<Citizen> findByFirstNameOrLastName(String firstName, String lastName);
    List<Citizen> findByAfm(String afm);

}