package com.fiap.stormtrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.stormtrack.model.Sensors;
import com.fiap.stormtrack.model.User;

public interface SensorsRepository extends JpaRepository<Sensors, Long> {

    List<Sensors> findByUser(User user);

}
