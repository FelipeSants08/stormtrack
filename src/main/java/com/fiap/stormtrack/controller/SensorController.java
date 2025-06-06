package com.fiap.stormtrack.controller;


import com.fiap.stormtrack.model.Sensors;
import com.fiap.stormtrack.model.User;
import com.fiap.stormtrack.repository.SensorsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Sensores", description = "Operações de sensores")
@RestController
@RequestMapping("/sensors")
@Slf4j
public class SensorController {

    @Autowired
    private SensorsRepository repository;

    @Operation(summary = "Lista todos os sensores", description = "Retorna uma lista de sensores cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public List<Sensors> index(@AuthenticationPrincipal User user) {
        log.info("Buscando todos os Sensores");
        return repository.findByUser(user);
    }
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na validação")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sensors create(@RequestBody @Valid Sensors sensors, @AuthenticationPrincipal User user) {
        log.info("Cadastrando sensor");
        sensors.setActive(true);
        sensors.setUser(user);
        return repository.save(sensors);
    }

    @GetMapping("{id}")
    public Sensors get(@PathVariable Long id) {
        log.info("buscando sensor " + id);
        return getSensors(id);
    }

    @PutMapping("{id}")
    public Sensors update(@PathVariable Long id,@RequestBody Sensors sensors) {
        log.info("Atualizando sensor " + id + " " + sensors);
        sensors.setId(id);
        return repository.save(sensors);
    }



    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id){
        log.info("Deletando sensor");
        repository.delete(getSensors(id));
    }

    @PatchMapping("{id}/activate")
    public Sensors activateSensors(@PathVariable Long id) {
        log.info("Ativando sensor");
        Sensors sensor = getSensors(id);
        sensor.setActive(true);
        return repository.save(sensor);
    }
    @PatchMapping("{id}/deactivate")
    public Sensors deactivateSensors(@PathVariable Long id) {
        log.info("Desativando sensor");
        Sensors sensor = getSensors(id);
        sensor.setActive(false);
        return repository.save(sensor);
    }

    private Sensors getSensors(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Sensor não encontrado"));
    }
}
