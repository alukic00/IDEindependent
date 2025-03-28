package com.teletrader.entity;

public enum Status {
    PENDING,    // Order je kreiran ali nije u order booku
    ACTIVE,     // Order je u order booku (za matchovanje)
    PARTIALLY_FILLED, // Delimično izvršen
    FILLED,     // Potpuno izvršen
    CANCELLED,  // Korisnički otkazan
    REJECTED
}
