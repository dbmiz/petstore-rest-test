package com.example.pet.entities;

public enum PetStatus {
    AVAILABLE("available"), PENDING("pending"), SOLD("sold");

    private String status;

    private PetStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
