package com.fiap.stormtrack.controller;

import com.fiap.stormtrack.dto.SensorReadingDTO;
import com.fiap.stormtrack.model.SensorReading;
import com.fiap.stormtrack.model.Sensors;
import com.fiap.stormtrack.repository.SensorReadingRepository;
import com.fiap.stormtrack.repository.SensorsRepository;
import com.fiap.stormtrack.service.AlertService;
import com.fiap.stormtrack.service.SensorReadingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "SensorsReading", description = "Leitura de sensores")
@Slf4j
@RestController
@RequestMapping("sensor-readings")
@RequiredArgsConstructor
public class SensorReadingController {

    private final SensorsRepository sensorsRepository;
    private final SensorReadingRepository repository;
    private final AlertService alertService;
    private final SensorReadingService sensorReadingService;

    @Operation(description = "Listar leituras por pagina", summary = "Lista de leituras")
    @GetMapping
    public ResponseEntity<Page<SensorReading>> index(@RequestParam int pagina,
                                                     @RequestParam int item) {
        log.info("Buscando leituras por página");
        return ResponseEntity.ok(sensorReadingService.findAll(pagina, item));
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na validação")})
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
        Sensors sensor = sensorsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sensor não encontrado"));

        if (!Boolean.TRUE.equals(sensor.getActive())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sensor está inativo");
        }

        return sensor;
    }

}
