package com.fiap.stormtrack.service;

import com.fiap.stormtrack.model.SensorReading;
import com.fiap.stormtrack.repository.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;



@Service
public class SensorReadingService {

    @Autowired
    SensorReadingRepository repository;

    public Page<SensorReading> findAll(int pagina, int item){
        return repository.findAll(PageRequest.of(pagina, item));
    }

}
