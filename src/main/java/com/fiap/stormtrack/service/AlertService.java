package com.fiap.stormtrack.service;

import com.fiap.stormtrack.model.Alert;
import com.fiap.stormtrack.model.Classification;
import com.fiap.stormtrack.model.SensorReading;
import com.fiap.stormtrack.repository.AlertRepository;
import com.fiap.stormtrack.specification.AlertSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertService {


    private final AlertRepository alertRepository;

    public Page<Alert> findAll(int pagina, int item) {
        return alertRepository.findAll(PageRequest.of(pagina, item));
    }


    public Page<Alert> findAllWithFilters(Classification classification,
                                          LocalDateTime startDate,
                                          LocalDateTime endDate,
                                          Pageable pageable) {
        var spec = AlertSpecification.withFilters(classification, startDate, endDate);
        return alertRepository.findAll(spec, pageable);
    }

    public void generatedAlertIfNeeded(SensorReading reading) {
        double temp = reading.getTemperature();
        double humidity = reading.getHumidity();

        double indiceCalor = calcularIndiceCalor(temp, humidity);
        String message = gerarAlerta(indiceCalor);
        Classification classification = determinarClassificacao(indiceCalor);

        if (!message.equals("Seguro: condições normais.")) {
            Alert alert = new Alert(null, reading, message,classification, LocalDateTime.now());
            alertRepository.save(alert);
        }
    }
    public double calcularIndiceCalor(double temperatura, double umidade) {
        return temperatura + 0.33 * umidade - 4.0;
    }

    public String gerarAlerta(double indiceCalor) {
        if (indiceCalor >= 54) {
            return "Emergência: risco extremo de vida!";
        } else if (indiceCalor >= 41) {
            return "Perigo: risco alto de doenças relacionadas ao calor!";
        } else if (indiceCalor >= 33) {
            return "Alerta: risco para saúde (desidratação, exaustão).";
        } else if (indiceCalor >= 27) {
            return "Atenção: desconforto térmico.";
        } else {
            return "Seguro: condições normais.";
        }
    }
    public Classification determinarClassificacao(double indiceCalor) {
        if (indiceCalor >= 54) {
            return Classification.ALERTA_VERMELHO;
        } else if (indiceCalor >= 41) {
            return Classification.CUIDADO;
        } else if (indiceCalor >= 33) {
            return Classification.MODERADO;
        } else {
            return Classification.BOM_TEMPO;
        }
    }
}
