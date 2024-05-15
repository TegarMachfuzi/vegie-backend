package com.vegiecrud.vegie.repository;

import com.vegiecrud.vegie.model.Vegie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VegieRepository extends JpaRepository<Vegie, String> {
}
