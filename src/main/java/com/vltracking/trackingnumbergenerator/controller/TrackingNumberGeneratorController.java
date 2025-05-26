package com.vltracking.trackingnumbergenerator.controller;/*
 *
 * Created By rakeshsrivastav On 25/05/25
 *
 */


import com.vltracking.trackingnumbergenerator.constants.TrackingNumberGeneratorErrorMessages;
import com.vltracking.trackingnumbergenerator.exceptions.TrackingNumberGenerationExceptionHandler;
import com.vltracking.trackingnumbergenerator.model.ErrorResponse;
import com.vltracking.trackingnumbergenerator.services.TrackingNumberGeneratorService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;


@RestController
public class TrackingNumberGeneratorController {

    @Autowired
    private TrackingNumberGeneratorService trackingNumberGeneratorService;

    @GetMapping("/next-tracking-number")
    public ResponseEntity generateTrackingNumber(
            @RequestParam("origin_country_id") @NonNull String originCountryId,
            @RequestParam("destination_country_id") @NonNull String destinationCountryId,
            @RequestParam("weight") BigDecimal weight,
            @RequestParam("created_at") @NonNull String createdAt,
            @RequestParam("customer_id") @NonNull String customerId,
            @RequestParam("customer_name") String customerName,
            @RequestParam("customer_slug") String customerSlug
    ) {
        if (!originCountryId.matches("^[A-Z]{2}$")) {
            throw new TrackingNumberGenerationExceptionHandler(TrackingNumberGeneratorErrorMessages.INVALID_ORIGIN_COUNTRY_ID);
        }

        if (!destinationCountryId.matches("^[A-Z]{2}$")) {
            throw new TrackingNumberGenerationExceptionHandler(TrackingNumberGeneratorErrorMessages.INVALID_DESTINATION_COUNTRY_ID);
        }

        // RFC 3339 datetime check
        OffsetDateTime parsedTimestamp;
        try {
            parsedTimestamp = OffsetDateTime.parse(createdAt);
        } catch (DateTimeParseException e) {
            throw new TrackingNumberGenerationExceptionHandler(TrackingNumberGeneratorErrorMessages.INVALID_CREATED_AT);
        }

        // Weight with up to 3 decimal places
        if (weight.scale() > 3) {
            throw new TrackingNumberGenerationExceptionHandler(TrackingNumberGeneratorErrorMessages.INVALID_WEIGHT);
        }

        // UUID format check
        try {
            UUID customer_id = UUID.fromString(customerId);
        } catch (IllegalArgumentException e) {
            throw new TrackingNumberGenerationExceptionHandler(TrackingNumberGeneratorErrorMessages.INVALID_CUSTOMER_ID);
        }

        // Slug-case (kebab-case) check
        if (!customerSlug.matches("^[a-z0-9]+(-[a-z0-9]+)*$")) {
            throw new TrackingNumberGenerationExceptionHandler(TrackingNumberGeneratorErrorMessages.INVALID_CUSTOMER_SLUG);
        }

        return new ResponseEntity<>(trackingNumberGeneratorService.generateTrackingNumber(originCountryId, destinationCountryId, customerId, parsedTimestamp), HttpStatus.OK);

    }

    @ExceptionHandler(value = TrackingNumberGenerationExceptionHandler.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCustomerAlreadyExistsException(TrackingNumberGenerationExceptionHandler ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
