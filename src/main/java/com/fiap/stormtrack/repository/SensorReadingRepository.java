package com.fiap.stormtrack.repository;

import com.fiap.stormtrack.model.SensorReading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

    Page<SensorReading> findAll(Pageable pageable);

}
