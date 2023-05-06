package com.shopme.be.repository;

import com.shopme.be.persistant.model.Klass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KlassRepository extends JpaRepository<Klass, Long> {
    Optional<Klass> findKlassByName(String name);
}
