package com.example.pet.core;

public class DefaultBaseUrlFactory implements BaseUrlFactory {
    @Override
    public String getUrl() {
        return "http://petstore.swagger.io";
    }
}
