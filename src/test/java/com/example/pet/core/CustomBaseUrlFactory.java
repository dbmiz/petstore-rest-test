package com.example.pet.core;

public class CustomBaseUrlFactory implements BaseUrlFactory {

    private String url;

    public CustomBaseUrlFactory(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
