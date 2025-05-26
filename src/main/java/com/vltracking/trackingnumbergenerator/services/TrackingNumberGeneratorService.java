package com.vltracking.trackingnumbergenerator.services;/*
 *
 * Created By rakeshsrivastav On 25/05/25
 *
 */

import com.vltracking.trackingnumbergenerator.model.TrackingInfo;

import java.time.OffsetDateTime;

public interface TrackingNumberGeneratorService {

    TrackingInfo generateTrackingNumber(String originCountryId, String destinationCountryId, String customerId, OffsetDateTime createdAt);
}
