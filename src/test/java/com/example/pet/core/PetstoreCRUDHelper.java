package com.example.pet.core;

import com.example.pet.entities.*;
import io.restassured.http.ContentType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;

public class PetstoreCRUDHelper {

    private final String newPetUrl = "/v2/pet/";
    private final String getPetUrl = "/v2/pet/";
    private final String deletePetUrl = "/v2/pet/";
    private final String deleteOrderUrl = "/v2/store/";
    private final String newOrderUrl = "/v2/store/order/";
    private final String getOrderUrl = "/v2/store/order/";
    private BaseUrlFactory url = ServiceLocator.getFactory(BaseUrlFactory.class);
    private List<DeletableByIdRecord> recordsToCleanUp = new LinkedList<>();

    public String getNewOrderUrl() {
        return newOrderUrl;
    }

    public String getGetOrderUrl() {
        return getOrderUrl;
    }

    public String getNewPetUrl() {
        return newPetUrl;
    }

    public String getGetPetUrl() {
        return getPetUrl;
    }

    public String getDeletePetUrl() {
        return deletePetUrl;
    }

    public String getDeleteOrderUrl() {
        return deleteOrderUrl;
    }

    public String getBaseUrl() {
        return url.getUrl();
    }

    public Pet submitPetAndExtractFromResponse(Pet pet) {
        Pet submittedPet = given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post(url.getUrl() + getNewPetUrl())
                .then()
                .statusCode(200)
                .extract().body().as(Pet.class);

        recordsToCleanUp.add(submittedPet);

        return submittedPet;
    }

    public Order submitOrderAndExtractFromResponse(Order order) {
        Order submittedOrder = given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(getBaseUrl() + getNewOrderUrl())
                .then()
                .statusCode(200)
                .extract().body().as(Order.class);

        recordsToCleanUp.add(submittedOrder);

        return submittedOrder;
    }

    public void cleanUp() {
        recordsToCleanUp.forEach(record -> {
            try {
                delete(getDeleteUrlFor(record));
            } catch (Exception e) {
                //log if necessary
            }
        });
    }

    private String getDeleteUrlFor(DeletableByIdRecord record) {
        if (Pet.class.equals(record.getClass())) {
            return getBaseUrl() + getDeletePetUrl() + record.getId();
        } else if (Order.class.equals(record.getClass())) {
            return getBaseUrl() + getDeleteOrderUrl() + record.getId();
        }

        return null;
    }


    public List<Pet> createValidPetRecords() {
        List<Pet> pets = new LinkedList<>();
        pets.add(new Pet(0, new Category(2, "cat2"), "Pet with photo, category, and tags",
                Arrays.asList("photo 1", "url.local", "photo 2", "url2.local"),
                Arrays.asList(new Tag(1, "Sample Tag 1"), new Tag(2, "Sample Tag 2")),
                PetStatus.AVAILABLE.getStatus()));
        pets.add(new Pet(0, null, "Pet with photo and tags",
                Arrays.asList("photo 1", "url.local", "photo 2", "url2.local"),
                Arrays.asList(new Tag(1, "Sample Tag 1"), new Tag(2, "Sample Tag 2")),
                PetStatus.AVAILABLE.getStatus()));
        pets.add(new Pet(0, null, "Pet with tags",
                null,
                Arrays.asList(new Tag(1, "Sample Tag 1"), new Tag(2, "Sample Tag 2")),
                PetStatus.AVAILABLE.getStatus()));
        pets.add(new Pet(0, null, "Pet name (available status)",
                null,
                null,
                PetStatus.AVAILABLE.getStatus()));
        pets.add(new Pet(0, null, "Pet name (pending status)",
                null,
                null,
                PetStatus.PENDING.getStatus()));
        pets.add(new Pet(0, null, "Pet name (sold status)",
                null,
                null,
                PetStatus.SOLD.getStatus()));

        return pets;
    }

    public List<String> createInvalidPetRecords() {
        List<String> pets = new LinkedList<>();
        pets.add("{\n" +
                "  \"id\": \"abc\",\n" +
                "  \"name\": null,\n" +
                "  \"photoUrls\": null,\n" +
                "  \"status\": null\n" +
                "}");
        return pets;
    }
}
