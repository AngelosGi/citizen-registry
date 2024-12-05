package io.anggi.citizen.registry.service.repositories;


import io.anggi.citizen.registry.domain.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, String>, JpaSpecificationExecutor<Citizen> {
}