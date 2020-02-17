package com.example.pet.entities;

public enum OrderStatus {
    PLACED("placed"), APPROVED("approved"), DELIVERED("delivered") ;

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
