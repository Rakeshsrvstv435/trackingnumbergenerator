package com.vltracking.trackingnumbergenerator.services.impl;/*
 *
 * Created By rakeshsrivastav On 25/05/25
 *
 */

import com.vltracking.trackingnumbergenerator.model.TrackingInfo;
import com.vltracking.trackingnumbergenerator.services.TrackingNumberGeneratorService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class TrackingNumberGeneratorServiceImpl implements TrackingNumberGeneratorService {

    @Override
    public TrackingInfo generateTrackingNumber(String originCountryId, String destinationCountryId, String customerId, OffsetDateTime createdAt) {

//        String timestamp = createdAt.toString(); // e.g., 2023-10-15T10:00:00Z

        // Hash UUID + timestamp to get a fixed-length alphanumeric string
        String hashCode = DigestUtils.sha1Hex(customerId + createdAt).toUpperCase();


        // Final tracking number: COUNTRY + HASH → length = 2 + 14 = 16
        String trackingNumber = originCountryId + hashCode.substring(0, 12) + destinationCountryId;


        return TrackingInfo.builder().tracking_number(trackingNumber).created_at(LocalDateTime.now().toString()).build();
    }
}
