package com.vltracking.trackingnumbergenerator.exceptions.impl;/*
 *
 * Created By rakeshsrivastav On 26/05/25
 *
 */

import com.vltracking.trackingnumbergenerator.exceptions.TokenGenerationExceptions;

public class TokenGenerationExceptionsImpl extends RuntimeException implements TokenGenerationExceptions {


    public TokenGenerationExceptionsImpl(String message) {
        super(message);
    }
}
