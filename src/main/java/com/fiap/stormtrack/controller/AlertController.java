package com.fiap.stormtrack.controller;

import com.fiap.stormtrack.model.Alert;
import com.fiap.stormtrack.repository.AlertRepository;
import com.fiap.stormtrack.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping
    public Page<Alert> index(@RequestParam int pagina, int item) {
        return alertService.findAll(pagina, item);
    }

}
