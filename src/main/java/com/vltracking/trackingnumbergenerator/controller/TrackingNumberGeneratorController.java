package com.vltracking.trackingnumbergenerator.controller;/*
 *
 * Created By rakeshsrivastav On 25/05/25
 *
 */


import com.vltracking.trackingnumbergenerator.services.TrackingNumberGeneratorService;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@RestController
public class TrackingNumberGeneratorController {

    @Autowired
    private TrackingNumberGeneratorService trackingNumberGeneratorService;

    @GetMapping("/next-tracking-number")
    public ResponseEntity<String> generateTrackingNumber(
            @RequestParam("origin_country_id") String originCountryId,
            @RequestParam("destination_country_id") String destinationCountryId,
            @RequestParam("weight") BigDecimal weight,
            @RequestParam("created_at") String createdAt,
            @RequestParam("customer_id") String customerId,
            @RequestParam("customer_name") String customerName,
            @RequestParam("customer_slug") String customerSlug
    ) {
        if (!originCountryId.matches("^[A-Z]{2}$")) {
            return new ResponseEntity<>("Invalid origin_country_id. Must be two uppercase letters.", HttpStatus.BAD_REQUEST);
        }

        if (!destinationCountryId.matches("^[A-Z]{2}$")) {
            return new ResponseEntity<>("Invalid destination_country_id. Must be two uppercase letters.",HttpStatus.BAD_REQUEST);
        }

        // RFC 3339 datetime check
//        OffsetDateTime parsedTimestamp;
        try {
            OffsetDateTime parsedTimestamp = OffsetDateTime.parse(createdAt);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Invalid timestamp. Must be in RFC 3339 format.",HttpStatus.BAD_REQUEST);
        }

        // Weight with up to 3 decimal places
            if (weight.scale() > 3) {
                return new ResponseEntity<>("Weight must be a decimal with up to 3 decimal places.",HttpStatus.BAD_REQUEST);
            }

        // UUID format check
        try {
            UUID customer_id = UUID.fromString(customerId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid customer_id. Must be a valid UUID.",HttpStatus.BAD_REQUEST);
        }

        // Slug-case (kebab-case) check
        if (!customerSlug.matches("^[a-z0-9]+(-[a-z0-9]+)*$")) {
            return new ResponseEntity<>("Invalid customer_slug. Must be in slug-case (kebab-case).",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(trackingNumberGeneratorService.generateTrackingNumber(),HttpStatus.OK);

    }
}
