package com.fiap.stormtrack.controller;

import com.fiap.stormtrack.model.Alert;
import com.fiap.stormtrack.model.Classification;
import com.fiap.stormtrack.repository.AlertRepository;
import com.fiap.stormtrack.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/all")
    public Page<Alert> index(@RequestParam int pagina, int item) {
        return alertService.findAll(pagina, item);
    }

    @GetMapping
    public Page<Alert> index(
            @RequestParam(required = false) Classification classification,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateTime").descending());
        return alertService.findAllWithFilters(classification, startDate, endDate, pageable);
    }

}
