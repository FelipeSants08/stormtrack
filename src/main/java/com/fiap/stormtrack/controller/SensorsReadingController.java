package com.fiap.stormtrack.controller;

import com.fiap.stormtrack.dto.SensorReadingDTO;
import com.fiap.stormtrack.model.SensorReading;
import com.fiap.stormtrack.model.Sensors;
import com.fiap.stormtrack.repository.SensorReadingRepository;
import com.fiap.stormtrack.repository.SensorsRepository;
import com.fiap.stormtrack.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("sensor-readings")
@RequiredArgsConstructor
public class SensorsReadingController {

    private final SensorsRepository sensorsRepository;
    private final SensorReadingRepository repository;
    private final AlertService alertService;

    @GetMapping
    public List<SensorReading> index() {
        log.info("Buscando leituras");
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody SensorReadingDTO dto) {
        log.info("Lendo sensor: {}", dto);
        Sensors sensor = getSensors(dto.sensorId());
        SensorReading reading = new SensorReading();
        reading.setSensors(sensor);
        reading.setTemperature(dto.temperature());
        reading.setHumidity(dto.humidity());
        reading.setReadingDate(LocalDateTime.now());

        repository.save(reading);

        alertService.generatedAlertIfNeeded(reading);

        return ResponseEntity.status(HttpStatus.CREATED).body("Leitura salva");
    }
    private Sensors getSensors(Long id) {
        return sensorsRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Sensor não encontrado"));
    }

}
