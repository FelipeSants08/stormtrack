package com.fiap.stormtrack.controller;

import com.fiap.stormtrack.model.Alert;
import com.fiap.stormtrack.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertRepository repository;

    @GetMapping
    public List<Alert> index() {
        return repository.findAll();
    }

}
