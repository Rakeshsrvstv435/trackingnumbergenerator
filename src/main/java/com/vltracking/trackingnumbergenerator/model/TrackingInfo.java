package com.vltracking.trackingnumbergenerator.model;/*
 *
 * Created By rakeshsrivastav On 26/05/25
 *
 */


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TrackingInfo {

    private String tracking_number;
    private String created_at;
}
