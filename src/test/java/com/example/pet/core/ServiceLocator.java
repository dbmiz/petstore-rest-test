package com.example.pet.core;

public class ServiceLocator {

    private static BaseUrlFactory baseUrlFactory = new DefaultBaseUrlFactory();

    public static <T> T getFactory(Class<T> type) {
        if (BaseUrlFactory.class.equals(type)) {
            return (T) baseUrlFactory;
        }

        throw new IllegalArgumentException("Unknown service");
    }

    static void setBaseUrlFactory(BaseUrlFactory baseUrlFactory) {
        ServiceLocator.baseUrlFactory = baseUrlFactory;
    }
}
