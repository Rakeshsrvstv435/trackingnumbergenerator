package com.vltracking.trackingnumbergenerator.constants;/*
 *
 * Created By rakeshsrivastav On 26/05/25
 *
 */

public class TrackingNumberGeneratorErrorMessages {
    public static final String INVALID_CREATED_AT = "Invalid created_at. Must be in RFC 3339 format.";
    public static final String INVALID_ORIGIN_COUNTRY_ID = "Invalid origin_country_id. Must be two uppercase letters.";
    public static final String INVALID_DESTINATION_COUNTRY_ID = "Invalid destination_country_id. Must be two uppercase letters.";
    public static final String INVALID_WEIGHT = "Weight must be a decimal with up to 3 decimal places.";
    public static final String INVALID_CUSTOMER_ID = "Invalid customer_id. Must be a valid UUID.";
    public static final String INVALID_CUSTOMER_SLUG= "Invalid customer_slug. Must be in slug-case (kebab-case).";

}
