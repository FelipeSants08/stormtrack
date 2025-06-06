package com.fiap.stormtrack.specification;

import com.fiap.stormtrack.model.Alert;
import com.fiap.stormtrack.model.Classification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AlertSpecification {

    public static Specification<Alert> withFilters(
            Classification classification,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (classification != null) {
                predicates.add(cb.equal(root.get("classification"), classification));
            }

            if (startDate != null && endDate != null) {
                predicates.add(cb.between(root.get("dateTime"), startDate, endDate));
            } else if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateTime"), startDate));
            } else if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateTime"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
