package com.fiap.stormtrack.repository;

import com.fiap.stormtrack.model.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    Page<Alert> findAll(Pageable pageable);

}
